package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
@NamedQueries({ //
		@NamedQuery(name = "EventEntity.findAll", query = "SELECT obj FROM EventEntity obj"), //
		@NamedQuery(name = "EventEntity.findByLevel", query = "SELECT obj FROM EventEntity obj WHERE :level >= obj.minLevel") //
})
public class EventEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int eventModeId;
	private int minLevel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventModeId() {
		return eventModeId;
	}

	public void setEventModeId(int eventModeId) {
		this.eventModeId = eventModeId;
	}
	
	public int getMinLevel() {
		return minLevel;
	}
	
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

}
