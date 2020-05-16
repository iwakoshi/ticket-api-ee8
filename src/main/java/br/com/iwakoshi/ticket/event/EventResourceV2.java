package br.com.iwakoshi.ticket.event;

import java.util.logging.Level;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.iwakoshi.ticket.config.json.Views;
import br.com.iwakoshi.ticket.config.json.Views.ComingSoon;
import br.com.iwakoshi.ticket.config.rest.CachControl;
import br.com.iwakoshi.ticket.config.rest.PageParam;
import br.com.iwakoshi.ticket.util.Util;
import lombok.extern.java.Log;

/**
 * @author Fabio Iwakoshi
 *
 */
@Log
@Path("events/v2")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResourceV2 {

	@Inject
	private EventService eventService;

	@Inject
	@CachControl(maxAge = 20)
	private CacheControl cacheControl;

	@GET
	@PermitAll
	@Path("{id:\\d+}")
	@JsonView(Views.Event.class)
	public Response findById(@PathParam("id") Long id) {
		log.log(Level.FINE, () -> "fetching event by id: " + id);
		Event event = eventService.find(id);
		return Response.ok(eventService.find(id)).cacheControl(cacheControl)
				.tag(Integer.toString(event.getId().hashCode())).build();
	}

	/**
	 * @param start
	 * @param max
	 * @return Coming Soon Movies
	 */
	@GET
	@PermitAll
	@Path("coming-soon")
	@JsonView(ComingSoon.class)
	public Response comingSoonV2(@BeanParam PageParam page, @QueryParam("search") @DefaultValue("") String search) {
		log.log(Level.FINE, () -> "fetching all events, keyword: " + search);
		return Util.paginator(eventService.countComingSoon(search), eventService.comingSoon(page, search), page,
				cacheControl);
	}
}
