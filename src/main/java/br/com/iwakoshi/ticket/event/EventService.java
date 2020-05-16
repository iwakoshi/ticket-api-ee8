package br.com.iwakoshi.ticket.event;

import static java.util.Optional.ofNullable;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.NotFoundException;

import br.com.iwakoshi.ticket.config.rest.PageParam;
import lombok.extern.java.Log;

/**
 * Manage {@code Event}
 * 
 * @author Fabio Iwakoshi
 *
 */
@Log
@Stateless
public class EventService {

	@Inject
	private EventRepository eventRepository;

	/**
	 * Returns a specific searched {@code Event}
	 * 
	 * @param id
	 * @return a specific {@code Event}
	 */
	public Event find(long id) {
		log.log(Level.FINE, () -> "fetching event: " + id);
		return eventRepository.find(id).orElseThrow(() -> new NotFoundException("Event"));
	}

	/**
	 * Returns a list of Events
	 * 
	 * @param param
	 * @param keyword
	 * @return a list of {@code Event}
	 */
	public List<Event> comingSoon(@Valid PageParam param, String search) {
		log.log(Level.FINE, () -> "Fetching all events, keyword: " + search);
		return ofNullable(eventRepository.comingSoon(param, search).collect(Collectors.toUnmodifiableList()))
				.filter(a -> !a.isEmpty()).orElseThrow(() -> new NotFoundException("Events"));
	}

	/**
	 * Returns the sum of all items on the table
	 * 
	 * @param keyword
	 * @return sum of items on the table
	 */
	public long countComingSoon(String search) {
		log.fine(() -> "Counting events");
		return eventRepository.countComingSoon(search);
	}

	/**
	 * Saves a item on the table
	 * 
	 * @param event
	 */
	public void save(@Valid Event event) {
		log.fine(() -> "Saving events");
		eventRepository.save(event);
	}
}
