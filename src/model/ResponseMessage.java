package model;

/**
 * 
 * This class defines the response of a API call. It contains relevant information about the outcome 
 * of that call.
 * 
 * @author Katarina Mededovic
 * 
 */
public class ResponseMessage {
	
	private String responseMessage;
	private int statusCode;
	
	/**
	 * 
	 * The default constructor for the class ResponseMessage.
	 * 
	 */
	public ResponseMessage() {}

	/**
	 * 
	 * The constructor for the ResponseMessage class. Used for creating the response
	 * which contains the responseMessage and the statusCode. 
	 * 
	 * @param responseMessage A string which represents a readable message for the user.
	 * @param statusCode      The status code of the API request.
	 * 
	 */
	public ResponseMessage(String responseMessage, int statusCode) {
		super();
		this.responseMessage = responseMessage;
		this.statusCode = statusCode;
	}

	/**
	 * 
	 * Returns the response message of the ResponseMessage in question.
	 * 
	 * @return Response message of the ResponseMessage in question.
	 * 
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * 
	 * Sets the response message of the ResponseMessage in question.
	 * 
	 * @param responseMessage Response message which is to be set.
	 * 
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * 
	 * Returns the status code of the ResponseMessage in question.
	 * 
	 * @return Status code of the ResponseMessage in question.
	 * 
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * 
	 * Sets the status code of the ResponseMessage in question.
	 * 
	 * @param statusCode Status code which is to be set.
	 * 
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
}
