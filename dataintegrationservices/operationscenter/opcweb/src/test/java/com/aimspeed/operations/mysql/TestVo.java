package com.aimspeed.operations.mysql;

import com.aimspeed.operations.entity.bean.mysql.user.UserAuthorityMySqlBean;
import com.aimspeed.operations.entity.bean.mysql.user.UserMySqlBean;

/**
 * 
 * @author AimSpeed
 */
public class TestVo {
	
	private UserMySqlBean userBean;
	
	private UserAuthorityMySqlBean userAuthorityMySqlBean;

	public UserMySqlBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserMySqlBean userBean) {
		this.userBean = userBean;
	}

	public UserAuthorityMySqlBean getUserAuthorityMySqlBean() {
		return userAuthorityMySqlBean;
	}

	public void setUserAuthorityMySqlBean(UserAuthorityMySqlBean userAuthorityMySqlBean) {
		this.userAuthorityMySqlBean = userAuthorityMySqlBean;
	}
	
	
	
}
