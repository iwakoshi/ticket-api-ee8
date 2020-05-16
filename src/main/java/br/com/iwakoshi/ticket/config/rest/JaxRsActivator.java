package br.com.iwakoshi.ticket.config.rest;

import static br.com.iwakoshi.ticket.config.auth.Roles.ADMIN;
import static br.com.iwakoshi.ticket.config.auth.Roles.USER;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is
 * the Java EE 6 "no XML" approach to activating JAX-RS.
 * 
 * @DeclareRoles are the roles allowed for the project.
 * 
 * <p>
 * Resources are served relative to the servlet path specified in the
 * {@link ApplicationPath} annotation.
 * </p>
 * 
 * @author Fabio Iwakoshi
 */
@DeclareRoles({ ADMIN, USER })
@ApplicationPath("/api")
public class JaxRsActivator extends Application {
	/* class body intentionally left blank */
}
