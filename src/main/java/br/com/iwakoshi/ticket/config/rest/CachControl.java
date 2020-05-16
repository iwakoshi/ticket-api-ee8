/**
 * 
 */
package br.com.iwakoshi.ticket.config.rest;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Fabio Iwakoshi
 *
 */
@Retention(RUNTIME)
@Target({ FIELD, PARAMETER })
public @interface CachControl {

	public boolean isPrivate() default true;

	public boolean noCache() default false;

	public boolean noStore() default false;

	public boolean noTransform() default true;

	public boolean mustRevalidate() default true;

	public boolean proxyRevalidate() default false;

	/**
	 * Seconds
	 */
	public int maxAge() default 0;

	/**
	 * Seconds
	 */
	public int sMaxAge() default 0;
}
