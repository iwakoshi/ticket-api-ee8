package br.com.iwakoshi.ticket.event;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.iwakoshi.ticket.config.jpa.JpaHints;
import br.com.iwakoshi.ticket.config.json.Views;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

/**
 * The persistent class for the movie database table.
 * 
 * @author Fabio Iwakoshi
 */
@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@NonFinal
@FieldDefaults(makeFinal = false)
@Entity
@Cacheable
@Table(name = "event")
@NamedQuery(name = "Event.comingSoon", query = "SELECT e FROM Event e WHERE e.releaseDate > :today AND e.originalTitle like :q ORDER BY e.releaseDate DESC", hints = @QueryHint(name = JpaHints.HINT_CACHEABLE, value = "true"))
@NamedQuery(name = "Event.countComingSoon", query = "SELECT COUNT(e.id) FROM Event e WHERE e.releaseDate > :today AND e.originalTitle like :q", hints = @QueryHint(name = JpaHints.HINT_CACHEABLE, value = "true"))
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum Category {
		MUSICAL, PLAY, MOVIE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView({ Views.Event.class, Views.ComingSoon.class })
	@Column(unique = true, nullable = false)
	private Long id;

	@NotBlank
	@NonNull
	@JsonView({ Views.Event.class, Views.ComingSoon.class })
	@Column(name = "original_title", nullable = false, length = 300)
	private String originalTitle;

	@JsonView({ Views.Event.class, Views.ComingSoon.class })
	@Column(name = "release_date")
	private LocalDate releaseDate;

	@NonNull
	@Enumerated(EnumType.STRING)
	@JsonView({ Views.Event.class, Views.ComingSoon.class })
	@Column(nullable = false)
	private Category category;

	@NonNull
	@JsonView({ Views.Event.class })
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@NonNull
	@Column(name = "created_by", nullable = false)
	private String createdBy;

	@JsonView({ Views.Event.class })
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	public Optional<LocalDate> getReleaseDate() {
        return Optional.ofNullable(releaseDate);
    }

	public Optional<LocalDateTime> getUpdatedAt() {
        return Optional.ofNullable(updatedAt);
    }

	public Optional<String> getUpdatedBy() {
        return Optional.ofNullable(updatedBy);
    }
	
	
}
