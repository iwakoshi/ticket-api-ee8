package br.com.iwakoshi.ticket.config.auth;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

/**
 * This JAX-RS dynamic feature will install filters for JAX-RS resources that
 * check roles or deny all access.
 * 
 * @author Fabio Iwakoshi
 */
@Provider
public class RolesAllowedDynamicFeature implements DynamicFeature {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		final Method resourceMethod = resourceInfo.getResourceMethod();

		// DenyAll on the method take precedence over RolesAllowed and PermitAll
		if (resourceMethod.isAnnotationPresent(DenyAll.class)) {
			context.register(new RolesAllowedRequestFilter());
			return;
		}

		// PermitAll takes precedence over RolesAllowed on the class
		if (resourceMethod.isAnnotationPresent(PermitAll.class)) {
			// Do nothing.
			return;
		}

		// RolesAllowed on the method takes precedence over PermitAll
		RolesAllowed ra = resourceMethod.getAnnotation(RolesAllowed.class);
		if (ra != null) {
			context.register(new RolesAllowedRequestFilter(ra.value()));
			return;
		}

		// DenyAll can't be attached to classes

		// RolesAllowed on the class takes precedence over PermitAll
		ra = resourceInfo.getResourceClass().getAnnotation(RolesAllowed.class);
		if (ra != null) {
			context.register(new RolesAllowedRequestFilter(ra.value()));
		}
	}

	@Priority(Priorities.AUTHORIZATION) // authorization filter - should go after any authentication filters
	private static class RolesAllowedRequestFilter implements ContainerRequestFilter {

		private final boolean denyAll;
		private final String[] rolesAllowed;

		public RolesAllowedRequestFilter() {
			this.denyAll = true;
			this.rolesAllowed = null;
		}

		RolesAllowedRequestFilter(String[] rolesAllowed) {
			this.denyAll = false;
			this.rolesAllowed = (rolesAllowed != null) ? rolesAllowed : new String[] {};
		}

		@Override
		public void filter(ContainerRequestContext requestContext) throws IOException {
			if (!denyAll) {
				if (rolesAllowed.length > 0 && !isAuthenticated(requestContext)) {
					throw new ForbiddenException();
				}

				boolean hasRole = Arrays.stream(rolesAllowed)
						.anyMatch(role -> requestContext.getSecurityContext().isUserInRole(role));

				if (!hasRole) {
					throw new ForbiddenException("Caller not in requested role");
				}
			}
			throw new ForbiddenException();
		}

		private boolean isAuthenticated(ContainerRequestContext requestContext) {
			return requestContext.getSecurityContext().getUserPrincipal() != null;
		}
	}
}
