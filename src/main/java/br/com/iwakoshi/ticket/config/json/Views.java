package br.com.iwakoshi.ticket.config.json;

import lombok.Value;

/**
 * Declares json views.
 * 
 * Defines which fields will be returned on a controller.
 * 
 * @author Fabio Iwakoshi
 *
 */
@Value
public final class Views {

	private Views() {
	}

	public static final class ComingSoon { // NOSONAR
	}
	
	public static final class Event { // NOSONAR
	}
}
