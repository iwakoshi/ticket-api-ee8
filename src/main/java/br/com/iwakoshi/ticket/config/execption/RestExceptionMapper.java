package br.com.iwakoshi.ticket.config.execption;

import java.util.logging.Level;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.iwakoshi.ticket.util.Util;
import lombok.extern.java.Log;

/**
 * @author Fabio Iwakoshi
 *
 */
@Log
@Provider
public class RestExceptionMapper implements ExceptionMapper<WebApplicationException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(WebApplicationException exception) {
		log.log(Level.SEVERE, exception, () -> "> ");
		return Response.status(exception.getResponse().getStatus()).entity(getEntity(exception))
				.type(MediaType.APPLICATION_JSON).build();
	}

	public Error getEntity(WebApplicationException exception) {
		return Error.builder().status(exception.getResponse().getStatus())
				.message(Util.getMessage(headers, getMessage(exception))).build();
	}

	/**
	 * @param exception
	 * @return Error entity
	 */
	private String getMessage(WebApplicationException exception) {
		switch (exception.getResponse().getStatusInfo().toEnum()) {
		case UNAUTHORIZED:
			return "error.unauthorized";
		case FORBIDDEN:
			return "error.forbidden";
		case NOT_FOUND:
			return "error.not_found";
		case METHOD_NOT_ALLOWED:
			return "error.method_not_allowed";
		default:
			return "error.service_unavailable";
		}
	}
}
