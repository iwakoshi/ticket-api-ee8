package br.com.iwakoshi.ticket.config.execption;

import javax.ejb.EJBException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.com.iwakoshi.ticket.util.Util;

/**
 * @author Fabio Iwakoshi
 *
 */
// @Log
@Provider
public class EjbExceptionMapper implements ExceptionMapper<EJBException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(EJBException exception) {
		// log.log(Level.SEVERE, exception, () -> "> ")

		Error error = Error.builder()
				.message(Util.getMessage(headers, "error.service_unavailable")).build();
		return Response.status(Status.SERVICE_UNAVAILABLE).entity(error).type(MediaType.APPLICATION_JSON).build();
	}
}
