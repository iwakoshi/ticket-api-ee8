package br.com.iwakoshi.ticket.event;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.iwakoshi.ticket.config.jpa.JpaHints;
import br.com.iwakoshi.ticket.config.rest.PageParam;

/**
 * 
 * @author Fabio Iwakoshi
 *
 */
@RequestScoped
class EventRepository {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Returns a specific searched {@code Event}
	 * 
	 * @param id
	 * @return specific {@code Event}
	 */
	public Optional<Event> find(Long id) {
		return Optional.ofNullable(entityManager.find(Event.class, id));
	}

	/**
	 * Returns a list of Events
	 * 
	 * @param param
	 * @param keyword
	 * @return a list of {@code Event}
	 */
	public Stream<Event> comingSoon(PageParam page, String search) {
		return entityManager.createNamedQuery("Event.comingSoon", Event.class).setParameter("q", "%" + search + "%")
				.setParameter("today", LocalDate.now()).setFirstResult(page.getOffset()).setMaxResults(page.getLimit())
				.setHint(JpaHints.HINT_FETCH_SIZE, page.getLimit()).setHint(JpaHints.HINT_CACHEABLE, Boolean.TRUE)
				.getResultStream();
	}

	/**
	 * Returns the sum of all items on the table
	 * 
	 * @param keyword
	 * @return sum of items on the table
	 */
	public Long countComingSoon(String keyword) {
		return entityManager.createNamedQuery("Event.countComingSoon", Long.class)
				.setParameter("q", "%" + keyword + "%").setParameter("today", LocalDate.now())
				.setHint(JpaHints.HINT_CACHEABLE, Boolean.TRUE).getSingleResult();
	}

	/**
	 * Saves a item on the table
	 * 
	 * @param event
	 */
	public void save(Event event) {
		entityManager.persist(event);
	}
}
