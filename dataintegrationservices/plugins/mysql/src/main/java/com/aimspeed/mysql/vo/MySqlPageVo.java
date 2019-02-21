package com.aimspeed.mysql.vo;

import java.util.List;
import java.util.Map;

/** 
 * 分页数据传输类
 * @author AimSpeed
 */
public class MySqlPageVo<T> {//分页传导的可能是Vo，所以不一定必须继承BaseMySqlBean

	/** 首页 **/
	private Integer fristPage; 
	
	/** 上一页 **/
	private Integer prePage;
	
	/** 下一页 **/
	private Integer nextPage;
	
	/** 总页数 = 尾页 **/
	private Integer totalPage; 
	
	/** 总数据条数 **/
	private Integer totalCount;
	
	/** 当前页 **/
	private Integer curPage;
	
	/** 每页条数 **/
	private Integer pageSize; 
	
	/** 当前页累计条数 **/
	private Integer curPageCountSize; 
	
	/** 筛选条件 - 字段名，值　**/
	private Map<String,Object> filtrate; 
	
	/** 筛选条件 - 模糊搜索　**/
	private Map<String,Object> likeFiltrate;
	
	/** 排序字段（数据库字段名） - 默认为最新时间排序  - 默认为最新时间排序　**/
	private String orderField; 
	
	/** 排序规则  - 默认为最新时间排序  **/
	private String orderingRule;
	
	/** 每页数据　**/
	private List<T> pageData;
	
	public MySqlPageVo() {
		super();
	}

	public Integer getFristPage() {
		return null == fristPage? 1 : fristPage;
	}

	public void setFristPage(Integer fristPage) {
		this.fristPage = fristPage;
	}
	
	//上一页：当前页是否为首页，如果非首页则当前页 -1 否则不变
	public Integer getPrePage() {
		return this.getCurPage().equals(this.getFristPage())
				? 
				this.getFristPage() : this.getCurPage() - 1;
	}

	public Map<String,Object> getLikeFiltrate() {
		return likeFiltrate;
	}

	public void setLikeFiltrate(Map<String,Object> likeFiltrate) {
		this.likeFiltrate = likeFiltrate;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}
	
	//下一页：当前页是否为尾页，如果非尾页则当前页 +1，否则不变
	public Integer getNextPage() {
		return this.getCurPage() == this.getTotalPage() 
				?
				this.getCurPage() : this.getCurPage() + 1;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	//总页数；总条目数%每页的条数==0 是则 总条目数/每页的条数，否则总条目数/每页的条数+1
	public int getTotalPage() {
		return this.getTotalCount() % this.getPageSize() == 0
				   ?this.getTotalCount() / this.getPageSize()
				   :this.getTotalCount() / this.getPageSize() + 1;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		return null == totalCount ? 0 : totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurPage() {
		return curPage = null == curPage ? 1 : curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	//分页时，从第几条开始，当前页数 -1 * 每页的数据条数
	public Integer getCurPageCountSize() {
		return (this.getCurPage() - 1 ) * this.getPageSize();
	}

	public void setCurPageCountSize(Integer curPageCountSize) {
		this.curPageCountSize = curPageCountSize;
	}
	
	public Map<String,Object> getFiltrate() {
		return filtrate;
	}

	public void setFiltrate(Map<String,Object> filtrate) {
		this.filtrate = filtrate;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderingRule() {
		return orderingRule;
	}

	public void setOrderingRule(String orderingRule) {
		this.orderingRule = orderingRule;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
	
	
	
	
	
	
}
