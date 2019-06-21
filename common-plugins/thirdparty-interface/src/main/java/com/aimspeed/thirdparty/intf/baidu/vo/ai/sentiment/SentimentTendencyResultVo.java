package com.aimspeed.thirdparty.intf.baidu.vo.ai.sentiment;

/**
 * 百度情感倾向分析结果Vo
 * @author AimSpeed
 */
public class SentimentTendencyResultVo {
	
	/** 
	 * @author AimSpeed
	 * @date 2018年8月16日 
	 */
	private static final long serialVersionUID = -4950513107047973988L;

	/**
	 * 调用号
	 */
	private Long LogId;
	
	/**
	 * 分析的文本
	 */
	private String text;
	
	/**
	 * 情感分析分类的结果：0：负、1：中、2：正
	 */
	private Integer sentiment;
	
	/**
	 * 分类的置信度
	 */
	private double confidence;
	
	/**
	 * 正面结果倾向概率
	 */
	private double positive;
	
	/**
	 * 负面倾向结果概率
	 */
	private double negative;

	/**
	 * 原JSON
	 */
	private String rawJson;
	
	/**
	 * 倾向结果
	 */
	private double tendencyResult;
	
	public SentimentTendencyResultVo() {
		super();
	}

	public SentimentTendencyResultVo(long logId, String text, Integer sentiment, double confidence,
			double positive, double negative, String rawJson, double tendencyResult) {
		super();
		LogId = logId;
		this.text = text;
		this.sentiment = sentiment;
		this.confidence = confidence;
		this.positive = positive;
		this.negative = negative;
		this.rawJson = rawJson;
		this.tendencyResult = tendencyResult;
	}

	public double getTendencyResult() {
		return tendencyResult;
	}

	public void setTendencyResult(double tendencyResult) {
		this.tendencyResult = tendencyResult;
	}

	public String getRawJson() {
		return rawJson;
	}

	public void setRawJson(String rawJson) {
		this.rawJson = rawJson;
	}

	public Long getLogId() {
		return LogId;
	}

	public void setLogId(long logId) {
		LogId = logId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSentiment() {
		return sentiment;
	}

	public void setSentiment(Integer sentiment) {
		this.sentiment = sentiment;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public double getPositive() {
		return positive;
	}

	public void setPositive(double positive) {
		this.positive = positive;
	}

	public double getNegative() {
		return negative;
	}

	public void setNegative(double negative) {
		this.negative = negative;
	}

	@Override
	public String toString() {
		return "SentimentTendencyResultVo [LogId=" + LogId + ", text=" + text + ", sentiment=" + sentiment
				+ ", confidence=" + confidence + ", positive=" + positive + ", negative=" + negative + ", rawJson="
				+ rawJson + ", tendencyResult=" + tendencyResult + "]";
	}

	
		
	
}
