package br.com.iwakoshi.ticket.config.json;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Provides a configured json.
 * 
 * @author Fabio Iwakoshi
 *
 */
@Provider
public final class JsonProvider implements ContextResolver<ObjectMapper> {

	private final ObjectMapper mapper;

	public JsonProvider() {
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule()); 	// Enables the use of the new Java Time API with Jackson
		mapper.registerModule(new Jdk8Module());		// Enables the use of the Optional with Jackson
		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);	// Only fields declared with View will be returned
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // Returns date fields as a String instead of a number
	}
	
	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

}
