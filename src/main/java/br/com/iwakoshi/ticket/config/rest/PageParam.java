package br.com.iwakoshi.ticket.config.rest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import lombok.Data;

/**
 * Default parameters for pagination.
 * 
 * @author Fabio Iwakoshi
 *
 */
@Data
public class PageParam {

	@QueryParam("offset")
	@DefaultValue("0")
	@PositiveOrZero(message = "Offset {javax.validation.constraints.PositiveOrZero.message}")
	private int offset;

	@QueryParam("limit")
	@DefaultValue("30")
	@Max(value = 100, message = "Limit {javax.validation.constraints.Max.message}")
	@Positive(message = "Limit {javax.validation.constraints.Positive.message}")
	private int limit;
}
