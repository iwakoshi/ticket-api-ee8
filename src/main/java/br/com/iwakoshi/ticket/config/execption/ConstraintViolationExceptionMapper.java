package br.com.iwakoshi.ticket.config.execption;

import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
 * @author Fabio Iwakoshi
 *
 */
@Log
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(ConstraintViolationException exception) {
		log.log(Level.SEVERE, exception, () -> "> ");
		return Response.status(Status.BAD_REQUEST).entity(getEntity(exception)).type(MediaType.APPLICATION_JSON)
				.build();
	}

	/**
	 * @param exception
	 * @return Error Entity
	 */
	private Error getEntity(ConstraintViolationException exception) {
		return Error.builder().status(Status.BAD_REQUEST.getStatusCode())
				.message(Util.getMessage(headers, "error.bad_request")).errors(exception.getConstraintViolations()
						.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet()))
				.build();
	}
}
