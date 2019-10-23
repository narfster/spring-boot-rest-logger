package com.narfster;

public class LogModel {

	private String htmlInfo;
	private String msgInfo;

	public LogModel(String htmlInfo, String msgInfo) {
		this.htmlInfo = htmlInfo;
		this.msgInfo = msgInfo;
	}

	public String getHtmlInfo() {
		return htmlInfo;
	}

	public void setHtmlInfo(String htmlInfo) {
		this.htmlInfo = htmlInfo;
	}

	public String getMsgInfo() {
		return msgInfo;
	}

	public void setMsgInfo(String msgInfo) {
		this.msgInfo = msgInfo;
	}
}
