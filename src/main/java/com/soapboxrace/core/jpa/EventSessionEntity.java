package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT_SESSION")
public class EventSessionEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "EVENTID", referencedColumnName = "ID")
	private EventEntity event;
	
	@Column(name = "STARTED")
	private Long started;
	
	@Column(name = "ENDED")
	private Long ended;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EventEntity getEvent() {
		return event;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}

	public Long getEnded()
	{
		return ended;
	}

	public void setEnded(Long ended)
	{
		this.ended = ended;
	}

	public Long getStarted()
	{
		return started;
	}

	public void setStarted(Long started)
	{
		this.started = started;
	}
}
