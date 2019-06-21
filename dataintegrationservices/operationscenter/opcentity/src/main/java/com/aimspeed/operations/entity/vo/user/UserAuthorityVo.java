package com.aimspeed.operations.entity.vo.user;

import java.io.Serializable;

/**
 * Created by liu on 2017/12/9.
 */
public class UserAuthorityVo implements Serializable{
    private static final long serialVersionUID = 7213548354645214870L;

    private Integer id;

    /**
     * 名称
     */
    private String name;//

	/**
	 * 页面展示的时候的id
	 */
	private String pageId;
	
    /**
     * 父ID
     */
    private Integer pId;//父id，一级分类为0
    
    /**
     * uri
     */
    private String uri;

    /**
     * 完整的URI
     */
    private String fullUri;

    public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getFullUri() {
		return fullUri;
	}

	public void setFullUri(String fullUri) {
		this.fullUri = fullUri;
	}



    
}
