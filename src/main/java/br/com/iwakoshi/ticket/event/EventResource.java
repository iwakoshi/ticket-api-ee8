package br.com.iwakoshi.ticket.event;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.iwakoshi.ticket.config.json.Views;
import br.com.iwakoshi.ticket.config.json.Views.ComingSoon;
import br.com.iwakoshi.ticket.config.rest.CachControl;
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

	@Inject
	private EventService eventService;

	@Inject
	@CachControl(maxAge = 20)
	private CacheControl cacheControl;

	/**
	 * @param Event id
	 * @return a specific Event
	 */
	@GET
	@Path("{id:\\d+}")
	@JsonView(Views.Event.class)
	public Response findById(@PathParam("id") Long id) {
		log.log(Level.FINE, () -> "fetching event by id: " + id);
		Event event = eventService.find(id);
		return Response.ok(eventService.find(id)).cacheControl(cacheControl)
				.tag(Integer.toString(event.getId().hashCode())).build();
	}

	/**
	 * @param page
	 * @param keyword
	 * @return Coming Soon Movies
	 * @deprecated The queryParam was changed
	 * @throws URISyntaxException
	 */
	@GET
	@Path("coming-soon")
	@PermitAll
	@JsonView(ComingSoon.class)
	@Deprecated(since = "2.0", forRemoval = true)
	public Response comingSoon(@BeanParam PageParam page) throws URISyntaxException {
		log.log(Level.FINE, () -> "fetching all events");
		return Response
				.seeOther(new URI("events/v2/coming-soon?offset=" + page.getOffset() + "&limit=" + page.getLimit()))
				.build();
	}
}
