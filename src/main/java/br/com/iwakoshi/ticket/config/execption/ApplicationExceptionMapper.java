package br.com.iwakoshi.ticket.config.execption;

import java.util.logging.Level;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.iwakoshi.ticket.util.Util;
import lombok.extern.java.Log;

/**
 * Catch all exceptions
 * 
 * @author Fabio Iwakoshi
 *
 */
@Log
@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<Throwable> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(Throwable exception) {
		log.log(Level.SEVERE, exception, () -> "> ");
		return Response.status(Status.SERVICE_UNAVAILABLE).entity(getEntity()).type(MediaType.APPLICATION_JSON).build();
	}

	/**
	 * @return Error Entity
	 */
	private Error getEntity() {
		return Error.builder().message(Util.getMessage(headers, "error.service_unavailable")).build();
	}
}
