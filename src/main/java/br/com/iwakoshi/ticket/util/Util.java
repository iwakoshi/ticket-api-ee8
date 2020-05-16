package br.com.iwakoshi.ticket.util;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import br.com.iwakoshi.ticket.config.rest.PageParam;

/**
 * Utilities
 * 
 * @author Fabio Iwakoshi
 *
 */
public class Util {

	private Util() {
	}

	/**
	 * Return message i18n
	 * 
	 * @param headers
	 * @param key
	 * @return message
	 */
	public static String getMessage(HttpHeaders headers, String key) {
		return ResourceBundle
				.getBundle("Messages", headers.getAcceptableLanguages().isEmpty() ? Locale.getDefault()
						: headers.getAcceptableLanguages().get(0))
				.getString(key);
	}

	/**
	 * Insert Header Content-Range on Response.
	 * 
	 * @param service
	 * @param list
	 * @param offset
	 * @param limit
	 * @return Response
	 */
	public static Response paginator(long count, List<?> list, PageParam page, CacheControl cacheControl) {
		return Response.status(list.size() < page.getLimit() ? Response.Status.OK : Response.Status.PARTIAL_CONTENT)
				.entity(list).header("Content-Range", formatContentRange(count, list.size(), page))
				.header("X-Total-Count", count).cacheControl(cacheControl).build();
	}

	/**
	 * @param count
	 * @param list
	 * @param page
	 * @return String Content Range
	 */
	private static String formatContentRange(long count, int listSize, PageParam page) {
		return String.format("items %d-%d/%d", (page.getOffset() * page.getLimit()) + 1,
				page.getOffset() * page.getLimit() + listSize, count);
	}
}
