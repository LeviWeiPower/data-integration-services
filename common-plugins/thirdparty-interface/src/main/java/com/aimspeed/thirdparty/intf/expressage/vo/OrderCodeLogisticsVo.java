package com.aimspeed.thirdparty.intf.expressage.vo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 订单物流信息
 * @author AimSpeed
 */
public class OrderCodeLogisticsVo {
	

	/*
     * {  "LogisticCode" : "9769183628782",  
     *    "ShipperCode" : "EMS",  
     *    "Traces" : [ 
     *                    {    "AcceptStation" : "南通叠石桥姜灶揽投站已收件（揽投员姓名：黄欢,联系电话:13814745878）",    
     *                    	   "AcceptTime" : "2018-09-25 16:48:00"  
     *                    }, 
     *                    {    "AcceptStation" : "已离开南通转运中心，发往无锡长三角邮件处理中心",    
     *                         "AcceptTime" : "2018-09-25 20:25:35"  
     *                    }, 
     *                    {    "AcceptStation" : "到达无锡长三角邮件处理中心处理中心（经转）",    
     *                         "AcceptTime" : "2018-09-26 02:28:49"  
     *                    }, 
     *                    {    "AcceptStation" : "离开无锡市 发往中山市（经转）",    
     *                         "AcceptTime" : "2018-09-26 08:39:59"  
     *                    }, 
     *                    {    "AcceptStation" : "到达中山三角处理中心处理中心（经转）",    
     *                         "AcceptTime" : "2018-09-27 14:54:00"  
     *                    }, 
     *                    {    "AcceptStation" : "投递并签收，签收人：他人收 申通菜鸟",    
     *                         "AcceptTime" : "2018-09-28 11:11:34"  
     *                    } 
     *              ],  
     *     "State" : "3",  
     *     "EBusinessID" : "1392118",  
     *     "Success" : true}
     */
	
	/**
	 * 物流单号
	 */
	private String logisticCode;
	
	/**
	 * 物流公司编码
	 */
	private String shipperCode;
	
	/**
	 * 物流详情
	 */
	private List<LinkedHashMap<String, String>> traces;
	
	/**
	 * 物流状态：2-在途中,3-签收,4-问题件
	 */
	private String state;
	
	/**
	 * 业务ID：忽略
	 */
	private String eBusinessID;

	public String getLogisticCode() {
		return logisticCode;
	}

	public void setLogisticCode(String logisticCode) {
		this.logisticCode = logisticCode;
	}

	public String getShipperCode() {
		return shipperCode;
	}

	public void setShipperCode(String shipperCode) {
		this.shipperCode = shipperCode;
	}

	public List<LinkedHashMap<String, String>> getTraces() {
		return traces;
	}

	public void setTraces(List<LinkedHashMap<String, String>> traces) {
		this.traces = traces;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String geteBusinessID() {
		return eBusinessID;
	}

	public void seteBusinessID(String eBusinessID) {
		this.eBusinessID = eBusinessID;
	}
	
	
	
}
