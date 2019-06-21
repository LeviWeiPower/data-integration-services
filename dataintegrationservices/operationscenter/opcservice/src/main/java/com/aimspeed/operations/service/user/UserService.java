package com.aimspeed.operations.service.user;

import com.aimspeed.common.vo.ResultVo;
import com.aimspeed.mysql.BaseMySqlService;
import com.aimspeed.operations.entity.bean.mysql.user.UserMySqlBean;



/**
 * 
 * 用户表
 * 
 */
public interface UserService extends BaseMySqlService<UserMySqlBean> {

	/**
	 * 添加数据
	 * @param record
	 * @param logAccount
	 * @return Integer  
	 */
	ResultVo saveSelective(UserMySqlBean record,String logAccount);

	/**
	 * 更新数据
	 * @param record
	 * @param logAccount
	 * @return Integer  
	 */
	ResultVo updateUserAccount(UserMySqlBean record,String logAccount);

	/**
	 * 本地系统登录
	 * @param sessionId
	 * @param account
	 * @param password
	 * @return ResultVo  
	 */
	ResultVo login(String sessionId,String account,String password);

	/**
	 * 根据ID进行删除
	 * @author AimSpeed
	 * @param record
	 * @param ids
	 * @return ResultVo  
	 */
	ResultVo deleteUserByIds(UserMySqlBean record, String ids);




}

