package com.aimspeed.thirdparty.intf.tencent.vo.ai.sentiment;

/**
 * 情感倾向分析结果
 * @author AimSpeed
 */
public class SentimentTendencyResult {

	/**
	 * @date 2018年8月16日 上午12:47:46
	 */ 
	private static final long serialVersionUID = 4896768522122222808L;
	
	/**
	 * 返回码； 0表示成功，非0表示出错
	 */
	private Integer ret;

	/**
	 * 返回信息；ret非0时表示出错时错误原因
	 */
	private String msg;

	/**
	 * 情感编码
	 * 情感编码	情感描述
	 *   -1	                 负面
	 *   0	                 中性
	 *   1	                 正面
	 */
	private Integer polar;
	
	/**
	 * 置信度
	 */
	private Double confd;
	
	/**
	 * API请求中的分析文本
	 */
	private String text;
	
	/**
	 * 倾向值
	 */
	private Double tendency;

	public Integer getRet() {
		return ret;
	}

	public void setRet(Integer ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getPolar() {
		return polar;
	}

	public void setPolar(Integer polar) {
		this.polar = polar;
	}

	public Double getConfd() {
		return confd;
	}

	public void setConfd(Double confd) {
		this.confd = confd;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getTendency() {
		return tendency;
	}

	public void setTendency(Double tendency) {
		this.tendency = tendency;
	}

	@Override
	public String toString() {
		return "SentimentTendencyResult [ret=" + ret + ", msg=" + msg + ", polar=" + polar + ", confd=" + confd
				+ ", text=" + text + ", tendency=" + tendency + "]";
	}
	
	
	
	
}
