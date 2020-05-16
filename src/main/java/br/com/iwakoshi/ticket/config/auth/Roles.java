package br.com.iwakoshi.ticket.config.auth;

import lombok.Value;

/**
 * Declares the roles of the application
 * 
 * @author Fabio Iwakoshi
 *
 */
@Value
public final class Roles {

	private Roles() {
	}

	public static final String ADMIN = "admin";

	public static final String USER = "everyone";
}
