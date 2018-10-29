package model;

public class ResponseMessage {
	
	private String responseMessage;
	private int statusCode;
	
	public ResponseMessage() {
	
	}

	public ResponseMessage(String responseMessage, int statusCode) {
		super();
		this.responseMessage = responseMessage;
		this.statusCode = statusCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
