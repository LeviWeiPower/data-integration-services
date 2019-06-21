package com.aimspeed.operations.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimspeed.common.datatype.DataTypeChangeUtils;
import com.aimspeed.common.datatype.StringUtils;
import com.aimspeed.mysql.BaseMySqlServiceImpl;
import com.aimspeed.operations.common.constants.CommonConstant;
import com.aimspeed.operations.entity.bean.mysql.user.UserAuthorityMySqlBean;
import com.aimspeed.operations.entity.bean.mysql.user.UserRoleMySqlBean;
import com.aimspeed.operations.entity.vo.user.UserAuthorityVo;
import com.aimspeed.operations.repository.mysql.user.UserAuthorityMySqlBeanMapper;
import com.aimspeed.redis.RedisMapperImpl;



/**
 * 
 * 用户权限表
 * 
 */
@Service
public class UserAuthorityServiceImpl extends BaseMySqlServiceImpl<UserAuthorityMySqlBean> implements UserAuthorityService {

	@Autowired
	private UserAuthorityMySqlBeanMapper authorityBeanMapper;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private RedisMapperImpl redisMapper;
	

	/*
	 * 删除指定节点及其子节点
	 * @param id
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#removeAuthorityInfo(java.lang.Integer)
	 */
	@Override
	public Integer removeAuthorityInfo(Integer id) {
		//查找出所有未删除的数据
		List<UserAuthorityMySqlBean> UserAuthorityList = this.selectSelectiveOfIsDeleteN(new UserAuthorityMySqlBean());
		
		//父节点容器
		List<Integer> parentIds= new ArrayList<Integer>();
		parentIds.add(id);
		
		//子节点容器
		List<Integer> childrenId= new ArrayList<Integer>();
		
		//循环获取到所有子节点的ID
		while(hasChild(parentIds,UserAuthorityList)){
			List<Integer> childrenNode= getChildNodeIds(parentIds,UserAuthorityList);
			parentIds = childrenNode;
			childrenId.addAll(childrenNode);
		}
		
		//删除该id对于的权限及其子节点权限
		childrenId.add(id);
		return authorityBeanMapper.deleteSelectiveOfIds(childrenId);
	}

	/*
	 * 查询指定节点的所有子节点
	 * @param parentIds
	 * @param UserAuthorityList
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#getChildNode(java.util.ArrayList, java.util.List)
	 */
	@Override
	public List<Integer> getChildNodeIds(List<Integer> parentIds,List<UserAuthorityMySqlBean> UserAuthorityList) {
		
		
		/*for (int i = 0; i < parentIds.size(); i++) {
			for (int j = 0; j < UserAuthorityList.size(); j++) {
				if(parentIds.get(i)==UserAuthorityList.get(j).getPid()){
					childrenIds.add(UserAuthorityList.get(j).getId());
				}
			}
		}*/
		
		//查找所有节点
		List<UserAuthorityMySqlBean> resultBeans = getChildNode(parentIds, UserAuthorityList);
		
		//ID数据
		List<Integer> childrenIds = new ArrayList<>();
		
		for (UserAuthorityMySqlBean userAuthorityMySqlBean : resultBeans) {
			childrenIds.add(userAuthorityMySqlBean.getId());
		}
		
		return childrenIds;
	}

	/*
	 * 查询指定节点的所有子节点
	 * @param parentIds
	 * @param UserAuthorityList
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#getChildNode(java.util.ArrayList, java.util.List)
	 */
	@Override
	public List<UserAuthorityMySqlBean> getChildNode(List<Integer> parentIds,List<UserAuthorityMySqlBean> UserAuthorityList) {
		List<UserAuthorityMySqlBean> childrens = new ArrayList<>();
		
		for (int i = 0; i < parentIds.size(); i++) {
			for (int j = 0; j < UserAuthorityList.size(); j++) {
				if(parentIds.get(i)==UserAuthorityList.get(j).getPid()){
					childrens.add(UserAuthorityList.get(j));
				}
			}
		}
		return childrens;
	}

	/*
	 * 判断该权限节点下面是否还有子节点
	 * @param parentIds
	 * @param userAuthorityList
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#hasChild(java.util.ArrayList, java.util.List)
	 */
	@Override
	public boolean hasChild(List<Integer> parentIds, List<UserAuthorityMySqlBean> userAuthorityList) {
		for (int i = 0; i < parentIds.size(); i++) {
			Integer parentId=parentIds.get(i);
			for (int j = 0; j < userAuthorityList.size(); j++) {
				if(parentId==userAuthorityList.get(j).getPid()){return true;}
			}
		}
		return false;
	}

	/*
	 * 根据ID获取到完整的URL
	 * @param userAuthorityBean
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#getFullUrlById(com.aimspeed.operation.entity.bean.mysql.user.UserAuthorityMySqlBean)
	 */
	@Override
	public String getFullUrlById(Integer id) {
		//根据ID查找出数据
		UserAuthorityMySqlBean userAuthorityBean = this.selectOfId(id);
		
		//查找出所有的材价数据
		List<UserAuthorityMySqlBean> UserAuthorityList = this.selectSelectiveOfIsDeleteN(new UserAuthorityMySqlBean());
		
		//拼凑的字符串，先把当前节点的URL放进去
		StringBuffer urlBuffer = new StringBuffer(userAuthorityBean.getUri());
		
		//获取到当前节点的父节点
		Integer pId = userAuthorityBean.getPid();
		
		for (int i = 0; i < UserAuthorityList.size();) {
			
			//判断当前节点是否是父节点，是父节点就继续，不是父节点就接着下一个
			if(pId != UserAuthorityList.get(i).getId()){
				i++;
				continue;
			}
			
			//是父节点就拼接这个父节点的URL
			urlBuffer.insert(0, UserAuthorityList.get(i).getUri());
			
			//在获取到这个节点的父节点
			pId = UserAuthorityList.get(i).getPid();
			
			//如果是顶级节点就跳过，不是就继续
			if(0 == pId) {
				break;
			}
			
			i=0;
		}
		return urlBuffer.toString();
	}

	/*
	 * 保存用户权限信息
	 * @param userAuthority
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#saveUserAuthority(com.aimspeed.operation.entity.bean.mysql.user.UserAuthorityMySqlBean)
	 */
	@Override
	public Integer saveUserAuthority(UserAuthorityMySqlBean userAuthority) {
		//如果是父节点，那么则设置级别为1
		if(0 == userAuthority.getPid()){
			userAuthority.setLevel(1);
		
		}else{
			//获取到父节点
			int pId = userAuthority.getPid();
			UserAuthorityMySqlBean pUserAuthority = this.selectOfId(pId);
			int pLevel=pUserAuthority.getLevel();
			userAuthority.setLevel(pLevel + 1);
		}
		
		//添加节点
		Integer len = this.insertSelective(userAuthority);
		
		//设置URL
		String fullUrl = this.getFullUrlById(userAuthority.getId());
		fullUrl = StringUtils.lineToHump(fullUrl.substring(1).replace("/", "_"));
		userAuthority.setPageId(fullUrl + "_" + userAuthority.getId() + "_" + userAuthority.getLevel());
		this.updateSelectiveOfId(userAuthority);
		
		return len;
	}

	/*
	 * 更新用户权限
	 * @param userAuthority
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#updateUserAuthority(com.aimspeed.operation.entity.bean.mysql.user.UserAuthorityMySqlBean)
	 */
	@Override
	public Integer updateUserAuthority(UserAuthorityMySqlBean userAuthority) {
		
		//查找出原有的。
		UserAuthorityMySqlBean userAuthorityMySqlBeanResult = selectOfId(userAuthority.getId());
		
		//重新设置id
		userAuthority.setPageId(StringUtils.lineToHump(this.getFullUrlById(userAuthority.getId()).replace(userAuthorityMySqlBeanResult.getUri().replace("/", ""), userAuthority.getUri().replace("/", "")).substring(1).replace("/", "_")) + "_" + userAuthority.getId() + "_" + userAuthorityMySqlBeanResult.getLevel());
		userAuthority.setLevel(userAuthorityMySqlBeanResult.getLevel());
		Integer len = this.updateSelectiveOfId(userAuthority);
		
		//父节点容器
		List<Integer> parentIds= new ArrayList<Integer>();
		parentIds.add(userAuthority.getId());
		
		//获取到所有子节点容器
		List<UserAuthorityMySqlBean>  userAuthorityMySqlBeans = getChildNode(parentIds, this.selectSelectiveOfIsDeleteN(new UserAuthorityMySqlBean()));
		for (UserAuthorityMySqlBean userAuthorityMySqlBean : userAuthorityMySqlBeans) {
			userAuthorityMySqlBean.setPageId(StringUtils.lineToHump(this.getFullUrlById(userAuthorityMySqlBean.getId()).substring(1).replace("/", "_")) + "_" + userAuthorityMySqlBean.getId() + "_" + userAuthorityMySqlBean.getLevel());
			this.updateSelectiveOfId(userAuthorityMySqlBean);
		}
		
		return len;
	}

	/*
	 * 根据角色获取到权限信息
	 * @author AimSpeed
	 * @param roleId
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#getAuthorityByRoleId(java.lang.Integer)
	 */
	@Override
	public List<UserAuthorityVo> getAuthorityByRoleId(Integer roleId) {
		//获取到所有的权限Id
		UserRoleMySqlBean userRoleMySqlBean = userRoleService.selectOfId(roleId);
		String idsString = userRoleMySqlBean.getAuthorityIds();
		
		if(null != idsString && !"".equals(idsString)) {
			
			//查询所有权限数据
			String [] idStringArrs = idsString.split(",");
			Integer [] integers = DataTypeChangeUtils.strArrToIntArr(idStringArrs);
			List<UserAuthorityMySqlBean> userAuthorityMySqlBeans = this.selectOfIds(Arrays.asList(integers));
			
			//获取到每一个权限的完整url，并且拼接
			List<UserAuthorityVo> userAuthorityVos = new ArrayList<>();
			for (UserAuthorityMySqlBean userAuthorityMySqlBean : userAuthorityMySqlBeans) {
				//拼装数据
				UserAuthorityVo userAuthorityVo = new UserAuthorityVo(); 
				userAuthorityVo.setId(userAuthorityMySqlBean.getId());
				userAuthorityVo.setName(userAuthorityMySqlBean.getName());
				userAuthorityVo.setPageId(userAuthorityMySqlBean.getPageId());
				userAuthorityVo.setpId(userAuthorityMySqlBean.getPid());
				userAuthorityVo.setUri(userAuthorityMySqlBean.getUri());
				userAuthorityVo.setFullUri(getFullUrlById(userAuthorityMySqlBean.getId()));
				userAuthorityVos.add(userAuthorityVo);
			}
			return userAuthorityVos;
		}
		return null;
	}

	/*
	 * 获取到当前登录用户的可访问权限信息
	 * @author AimSpeed
	 * @param sessionId
	 * @return
	 * @overridden @see com.aimspeed.operations.service.user.UserAuthorityService#getCurLoginUserAuthority(java.lang.String)
	 */
	@Override
	public List<UserAuthorityVo> getCurLoginUserAuthority(String sessionId) {
		//获取到当前登录用户
		List<UserAuthorityVo> userAuthorityVos = new ArrayList<>();
		userAuthorityVos = redisMapper.get(sessionId + CommonConstant.REDIS_LOGIN_USER_ROLE_INFO,userAuthorityVos.getClass());
		return userAuthorityVos;
	}	
	
	
	
	

}

