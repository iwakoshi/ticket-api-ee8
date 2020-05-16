package br.com.iwakoshi.ticket.bootstrap;

import static java.util.Arrays.asList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import br.com.iwakoshi.ticket.event.Event;
import br.com.iwakoshi.ticket.event.EventService;
import br.com.iwakoshi.ticket.event.Event.Category;
import lombok.extern.java.Log;

/**
 * Runs at startup
 * 
 * @author Fabio Iwakoshi
 *
 */
@Log
@Startup
@Singleton
public class Bootstrap {

	@Inject
	private EventService eventService;

	@PostConstruct
	public void init() {
		log.info("initializing...");

		Stream.of(Map.entry(Category.MUSICAL, asList("The Lion King", "The Phantom of the Opera")),
				Map.entry(Category.PLAY, asList("Aladdin", "To Kill a Mockingbird")),
				Map.entry(Category.MOVIE, asList("Inception", "Matrix")))
				.map(m -> m.getValue().stream().map(n -> createEvent(m.getKey(), n))).flatMap(Function.identity())
				.forEach(eventService::save);
	}

	private Event createEvent(Category category, String originalTitle) {
		return Event.builder().originalTitle(originalTitle).category(category).releaseDate(LocalDate.now().plusDays(5))
				.createdAt(LocalDateTime.now()).createdBy("iwakoshi").build();
	}
}
