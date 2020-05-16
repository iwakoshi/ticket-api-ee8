package br.com.iwakoshi.ticket.config.execption;

import java.util.Set;

import javax.ws.rs.core.Response.Status;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Error Model
 * 
 * @author Fabio Iwakoshi
 *
 */
@Value
@Builder
public final class Error {

	@NonNull
	@Builder.Default
	private final Integer status = Status.SERVICE_UNAVAILABLE.getStatusCode();

	@NonNull
	private final String message;

	private final Set<String> errors;
}
