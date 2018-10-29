package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import model.ResponseMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

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
	
	private void setHttpStatus(Throwable ex, ResponseMessage responseMessage) {
		if(ex instanceof WebApplicationException ) {
			responseMessage.setStatusCode(((WebApplicationException)ex).getResponse().getStatus());
		} else {
			responseMessage.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()); //defaults to internal server error 500
		}
	}

}