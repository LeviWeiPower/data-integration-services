package com.aimspeed.operations.service.user;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimspeed.common.constants.RedisTimeOutConstants;
import com.aimspeed.common.cryptology.irreversible.MD5Arithmetic;
import com.aimspeed.common.cryptology.irreversible.SHAArithmetic;
import com.aimspeed.common.datatype.DataTypeChangeUtils;
import com.aimspeed.common.enums.HttpResponseCurdEnum;
import com.aimspeed.common.enums.HttpResponseEnum;
import com.aimspeed.common.enums.IsDeleteEnum;
import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.mysql.BaseMySqlServiceImpl;
import com.aimspeed.operations.common.constants.CommonConstant;
import com.aimspeed.operations.entity.bean.mysql.user.UserMySqlBean;
import com.aimspeed.redis.RedisMapperImpl;
import com.alibaba.fastjson.JSON;



/**
 * 
 * 用户表
 * 
 */
@Service
public class UserServiceImpl extends BaseMySqlServiceImpl<UserMySqlBean> implements UserService {
	
	@Autowired
	private RedisMapperImpl redisMapper;
	
	/*@Autowired
	private UserAccountApiService userAccountApiService;*/
	
	@Autowired
	private UserAuthorityService userAuthorityService;


	/*
	 * 添加数据
	 * @param record
	 * @param logAccount
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserMySqlBeanService#saveSelective(com.aimspeed.operation.entity.bean.user.UserMySqlBean, java.lang.String)
	 */
	@Override
	public ResultVo saveSelective(UserMySqlBean record, String logAccount) {
		
		String password = record.getPassword();
		
		try {
			password = new SHAArithmetic().encryptSHA384(password);
			password = new MD5Arithmetic().encrypt16bit(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		record.setPassword(password);
		
		//设置必要数据
		record.setIsDelete(IsDeleteEnum.N.getValue());
		record.setCreator(logAccount);
		record.setUpdator(logAccount);
		
		int len = this.insertSelective(record);
		
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.SAVE_SUCCESS.getValue(),len);
	}

	/*
	 * 更新数据
	 * @param record
	 * @param logAccount
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserMySqlBeanService#updateUserAccount(com.aimspeed.operation.entity.bean.user.UserMySqlBean, java.lang.String)
	 */
	@Override
	public ResultVo updateUserAccount(UserMySqlBean record, String logAccount) {
		
		UserMySqlBean userBean = this.selectOfId(record.getId());
		
		String password = record.getPassword();
		
		//验证密码是否一样
		if(!userBean.getPassword().equals(password)) {
			//密码重新加密
			try {
				password = new SHAArithmetic().encryptSHA384(password);
				password = new MD5Arithmetic().encrypt16bit(password);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			record.setPassword(password);
		}
		
		//设置必要数据
		record.setIsDelete(IsDeleteEnum.N.getValue());
		record.setCreator(logAccount);
		record.setUpdator(logAccount);
		
		int len = this.updateSelectiveOfId(record);
		
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseCurdEnum.SAVE_SUCCESS.getValue());
	}

	/*
	 * 本地系统登录
	 * @param systemName
	 * @param sessionId
	 * @param account
	 * @param password
	 * @return
	 * @overridden @see com.aimspeed.sso.service.user.UserAccountBeanService#loginOfLocalSystem(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ResultVo login(String sessionId,String account,String password) {
		
		//账号验证
		UserMySqlBean selectUserMySqlBean = new UserMySqlBean();
		selectUserMySqlBean.setAccount(account);
		selectUserMySqlBean.setIsDelete(IsDeleteEnum.N.getValue());
		selectUserMySqlBean = this.selectOnlyOfSelective(selectUserMySqlBean);
		
		if(null == selectUserMySqlBean) {
			return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),"账号不存在!");
		}
		
		//密码加密
		try {
			password = new SHAArithmetic().encryptSHA384(password);
			password = new MD5Arithmetic().encrypt16bit(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		//判断是否一致
		if(password.equals(selectUserMySqlBean.getPassword())) {
			//缓存用户信息
			redisMapper.set(sessionId + CommonConstant.REDIS_LOGIN_USER_INFO, selectUserMySqlBean,RedisTimeOutConstants.HALF_HOUR);
			//缓存用户权限信息
			redisMapper.set(sessionId + CommonConstant.REDIS_LOGIN_USER_ROLE_INFO, JSON.toJSONString(userAuthorityService.getAuthorityByRoleId(selectUserMySqlBean.getRoleId())),RedisTimeOutConstants.HALF_HOUR);
			
			return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),HttpResponseEnum.SUCCESS.getValue());
		}
		return new ResultVo(HttpResponseEnum.SUCCESS.getCode(),"密码错误!");
		
	}

	/*
	 * 根据ID进行删除
	 * @param record
	 * @param ids
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserService#deleteUserByIds(com.aimspeed.operation.entity.bean.mysql.user.UserMySqlBean, java.lang.String)
	 */
	@Override
	public ResultVo deleteUserByIds(UserMySqlBean record, String ids) {
		
		//查找出相应的数据
		String [] idStrings = ids.split(",");
		Integer [] integers = DataTypeChangeUtils.strArrToIntArr(idStrings);
		List<Integer> idsArr = Arrays.asList(integers);
		List<UserMySqlBean> userMySqlBeans = this.selectOfIds(idsArr);
		
		if(null != userMySqlBeans && userMySqlBeans.size() > 0) {
			//拼接数据
			String accounts = "";
			for (int i = 0; i < userMySqlBeans.size(); i++) {
				UserMySqlBean userMySqlBean = userMySqlBeans.get(i);
				
				//非最后一个
				if(userMySqlBeans.size() - 1 != i) {
					accounts += userMySqlBean.getAccount() + ",";
				}else {//最后一个
					accounts += userMySqlBean.getAccount();
				}
			}
			
			//本地操作
			if(null != idStrings && idStrings.length > 0) {
				this.batchUpdateOfIds(record, idsArr);
			}else {
				this.updateSelectiveOfId(record);
			}
		}
		
		return new ResultVo(HttpResponseEnum.SYSTEM_ERROR.getCode(),"未查找到数据，请重新确认!");
	}

}

