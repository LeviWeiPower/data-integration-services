package com.aimspeed.common.file.excel.export;

/**
 * 测试类
 * @author AimSpeed
 */
@ExcelExport
public class ExcelExportTest {
	
	private String name;
	
	@ExcelExportField(headlineName="爱好",row=2)
	private String hobby;
	
	private String nikeHobby;

	@ExcelExportField(headlineName="昵称",row=1)
	private String nikeName;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getNikeHobby() {
		return nikeHobby;
	}

	public void setNikeHobby(String nikeHobby) {
		this.nikeHobby = nikeHobby;
	}
	
	
	
}
