package br.com.iwakoshi.ticket.event;

import static br.com.iwakoshi.ticket.config.auth.Roles.ADMIN;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.iwakoshi.ticket.config.json.Views;
import br.com.iwakoshi.ticket.config.json.Views.ComingSoon;
import br.com.iwakoshi.ticket.config.rest.PageParam;
import lombok.extern.java.Log;

/**
 * @author Fabio Iwakoshi
 *
 */
@Log
@Path("events")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

	/**
	 * @param Event id
	 * @return a specific Event
	 * @deprecated a new version is available
	 * @throws URISyntaxException
	 */
	@GET
	@Path("{id:\\d+}")
	@JsonView(Views.Event.class)
	@Deprecated(since = "2.0", forRemoval = true)
	public Response findById(@PathParam("id") Long id) throws URISyntaxException {
		log.log(Level.FINE, () -> "unavailable fetching event by id: " + id);
		return Response.seeOther(new URI("events/v2/" + id)).build();
	}

	/**
	 * @param page
	 * @param keyword
	 * @return coming Soon Movies
	 * @deprecated the queryParam was changed
	 * @throws URISyntaxException
	 */
	@GET
	@Path("coming-soon")
	@RolesAllowed({ ADMIN })
	@JsonView(ComingSoon.class)
	@Deprecated(since = "2.0", forRemoval = true)
	public Response comingSoon(@BeanParam PageParam page) throws URISyntaxException {
		log.log(Level.FINE, () -> "unavailable fetching all events");
		return Response
				.seeOther(new URI("events/v2/coming-soon?offset=" + page.getOffset() + "&limit=" + page.getLimit()))
				.build();
	}
}
