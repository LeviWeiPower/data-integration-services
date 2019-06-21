package com.aimspeed.thirdparty.intf.tencent.vo.ai.reply;

/**
 * 回复分析结果
 * @author AimSpeed
 */
public class ReplyAnalyzeResult {

	
	/** 
	 * @author AimSpeed
	 * @date 2018年8月16日 
	 */
	private static final long serialVersionUID = 2578868631019100943L;

	/**
	 * 返回码； 0表示成功，非0表示出错
	 */
	private Integer ret;

	/**
	 * 返回信息；ret非0时表示出错时错误原因
	 */
	private String msg;
	
	/**
	 * 回复结果
	 */
	private String answer;
	
	/**
	 * 倾向值
	 */
	private String sessionId;

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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "ReplyAnalyzeResult [ret=" + ret + ", msg=" + msg + ", answer=" + answer + ", sessionId=" + sessionId
				+ "]";
	}
	
	

}
