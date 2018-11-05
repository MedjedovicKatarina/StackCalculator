package com.mededovic.stackcalculator.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mededovic.stackcalculator.model.ResponseMessage;

/**
 * 
 * This class is responsible for the catching of exceptions. It catches all of the exceptions
 * and then filters out the WebApplicationExceptions. For example, it catches the wrong
 * type of an API call, or catches the exception which occurs when a wrong API invocation is made.
 * 
 * @author Katarina Mededovic
 * 
 */
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	/**
	 * 
	 * Catches the Throwable and converts it to a response which is then returned.
	 * 
	 * @param  ex       The Throwable which is caught.
	 * @return Response The Response containing the ResponseMessage entity.
	 * 
	 */
	@Override
	public Response toResponse(Throwable ex) {
		ResponseMessage responseMessage = new ResponseMessage();
		setHttpStatus(ex, responseMessage);
		responseMessage.setResponseMessage(ex.getMessage());
		return Response.status(responseMessage.getStatusCode())
				.entity(responseMessage)
				.type(MediaType.APPLICATION_JSON)
				.build();
	}
	
	/**
	 * 
	 * Checks whether the Throwable is of WebApplicationException type and if so, sets the
	 * ResponseMessage status code to that of the Throwable's. Otherwise sets the code to be 
	 * an INTERNAL_SERVER_ERROR - code 500.
	 * 
	 * @param ex               Throwable which was caught and is to be compared to the 
	 * WebApplicationException.
	 * @param responseMessage  The ResponseMessage used to communicate the response to the user.
	 * 
	 */
	private void setHttpStatus(Throwable ex, ResponseMessage responseMessage) {
		if(ex instanceof WebApplicationException ) {
			responseMessage.setStatusCode(((WebApplicationException)ex).getResponse().getStatus());
		} else {
			responseMessage.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
		}
	}

}