package com.speed.klutz.vo;

/**
 * 
 * @author AimSpeed
 */
public class ClassAnnotationInfoVo {
	
	/**
	 * 创建人
	 */
	private String author;
	
	/**
	 * 是否加上时间
	 */
	private boolean isDate;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isDate() {
		return isDate;
	}

	public void setDate(String isDate) {
		if("yes".equalsIgnoreCase(isDate)) 
			this.isDate = true;
		else 
			this.isDate = false;
	}
	
	
	
}
