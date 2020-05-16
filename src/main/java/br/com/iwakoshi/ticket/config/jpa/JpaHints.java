package br.com.iwakoshi.ticket.config.jpa;

import lombok.Value;

/**
 * Defines the supported JPA query hints
 *
 * @author Fabio Iwakoshi
 */
@Value
public final class JpaHints {

	private JpaHints() {
	}

	/**
	 * The hint key for specifying whether the query results should be cached for
	 * the next (cached) execution of the "same query".
	 */
	public static final String HINT_CACHEABLE = "org.hibernate.cacheable";

	/**
	 * The hint key for specifying a JDBC fetch size, used when executing the
	 * resulting SQL.
	 */
	public static final String HINT_FETCH_SIZE = "org.hibernate.fetchSize";
	
	/**
	 * Hint to enable/disable the pass-distinct-through mechanism.
	 * A value of {@code true} enables pass-distinct-through, whereas a value of {@code false} disables it.
	 * When the pass-distinct-through is disabled, the HQL and JPQL distinct clause is no longer passed to the SQL statement.
	 *
	 * @since 5.2
	 */
	public static final String HINT_PASS_DISTINCT_THROUGH = "hibernate.query.passDistinctThrough";
}
