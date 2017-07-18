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
		@NamedQuery(name = "EventEntity.findByLevel", query = "SELECT obj FROM EventEntity obj WHERE :level >= obj.minLevel AND isEnabled = true") //
})
public class EventEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int eventModeId;
	private int minLevel;
	private int maxLevel;
	private int minCarClassRating;
	private int maxCarClassRating;
	private boolean isEnabled;
	private boolean isLocked;
	private String name;

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
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	public int getMinCarClassRating() {
		return minCarClassRating;
	}
	
	public void setMinCarClassRating(int minCarClassRating) {
		this.minCarClassRating = minCarClassRating;
	}
	
	public int getMaxCarClassRating() {
		return maxCarClassRating;
	}
	
	public void setMaxCarClassRating(int maxCarClassRating) {
		this.maxCarClassRating = maxCarClassRating;
	}
	
	public boolean getIsEnabled() {
		return isEnabled;
	}
	
	public void setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean getIsLocked() {
		return isLocked;
	}
	
	public void setIsLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
