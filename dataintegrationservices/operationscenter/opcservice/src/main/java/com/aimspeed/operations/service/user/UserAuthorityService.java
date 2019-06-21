package com.aimspeed.operations.service.user;

import java.util.List;

import com.aimspeed.mysql.BaseMySqlService;
import com.aimspeed.operations.entity.bean.mysql.user.UserAuthorityMySqlBean;
import com.aimspeed.operations.entity.vo.user.UserAuthorityVo;




/**
 * 
 * 用户权限表
 * 
 */
public interface UserAuthorityService extends BaseMySqlService<UserAuthorityMySqlBean> {

	/**
	 * 删除指定节点及其子节点
	 * @author AimSpeed
	 * @param id
	 * @return Integer  
	 */
	Integer removeAuthorityInfo(Integer id);
	
	/**
	 * 查询指定节点的所有子节点
	 * @author AimSpeed
	 * @param parentIds 子节点，如果是顶级节点就只有一个
	 * @param UserAuthorityList
	 * @return ArrayList<Integer>  
	 */
	List<Integer> getChildNodeIds(List<Integer> parentIds,List<UserAuthorityMySqlBean> UserAuthorityList);

	/**
	 * 查询指定节点的所有子节点
	 * @author AimSpeed
	 * @param parentIds
	 * @param UserAuthorityList
	 * @return ArrayList<Integer>  
	 */
	List<UserAuthorityMySqlBean> getChildNode(List<Integer> parentIds,List<UserAuthorityMySqlBean> UserAuthorityList);

	/**
	 * 判断该权限节点下面是否还有子节点
	 * @author AimSpeed
	 * @param parentIds 子节点，如果是顶级节点就只有一个
	 * @param userAuthorityList
	 * @return boolean 
	 */
	boolean hasChild(List<Integer> parentIds, List<UserAuthorityMySqlBean> userAuthorityList);
	
	/**
	 * 根据ID获取到完整的URL
	 * @author AimSpeed
	 * @param userAuthority
	 * @return String  
	 */
	String getFullUrlById(Integer id);
	
	/**
	 * 保存用户权限信息
	 * @author AimSpeed
	 * @param userAuthority
	 * @return Integer  
	 */
	Integer saveUserAuthority(UserAuthorityMySqlBean userAuthority);
	
	/**
	 * 更新用户权限
	 * @author AimSpeed
	 * @param userAuthority
	 * @return Integer  
	 */
	Integer updateUserAuthority(UserAuthorityMySqlBean userAuthority);
	
	/**
	 * 根据角色获取到权限信息
	 * @author AimSpeed
	 * @param roleId
	 * @return List<AuthorityInfoVo> 
	 */
	List<UserAuthorityVo> getAuthorityByRoleId(Integer roleId);
	
	/**
	 * 获取到当前登录用户的可访问权限信息
	 * @author AimSpeed
	 * @param sessionId
	 * @return List<UserAuthorityVo> 
	 */
	List<UserAuthorityVo> getCurLoginUserAuthority(String sessionId);
	
	
	
}

