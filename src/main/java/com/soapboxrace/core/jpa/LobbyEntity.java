package com.soapboxrace.core.jpa;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "LOBBY")
@NamedQueries({ //
		@NamedQuery(name = "LobbyEntity.findAll", query = "SELECT obj FROM LobbyEntity obj"), //
		@NamedQuery(name = "LobbyEntity.findAllOpen", //
				query = "SELECT obj FROM LobbyEntity obj WHERE obj.startedTime between :dateTime1 and :dateTime2 "), //
		@NamedQuery(name = "LobbyEntity.findAllOpenByCarClass", //
				query = "SELECT obj FROM LobbyEntity obj " //
						+ "WHERE obj.startedTime between :dateTime1 and :dateTime2 " //
						+ "and (obj.event.carClassHash = 607077938 or obj.event.carClassHash = :carClassHash )"),
		@NamedQuery(name = "LobbyEntity.findByEventStarted", query = "SELECT obj FROM LobbyEntity obj WHERE obj.event = :event AND obj.startedTime between :dateTime1 AND :dateTime2 AND obj.isPrivate = false"), //
		@NamedQuery(name = "LobbyEntity.findByEventAndPersona", query = "SELECT obj FROM LobbyEntity obj WHERE obj.event = :event AND obj.startedTime between :dateTime1 AND :dateTime2 AND obj.isPrivate = true AND obj.personaId = :personaId") //
})
public class LobbyEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "EVENTID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_LOBBY_EVENT"))
	private EventEntity event;

	@OneToMany(mappedBy = "lobby", targetEntity = LobbyEntrantEntity.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private List<LobbyEntrantEntity> entrants = new ArrayList<>();

	private LocalDateTime startedTime;

	private Boolean isPrivate;

	private Long personaId;

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

	public List<LobbyEntrantEntity> getEntrants() {
		return entrants;
	}

	public void setEntrants(List<LobbyEntrantEntity> entrants) {
		this.entrants = entrants;
	}

	public LocalDateTime getStartedTime()
	{
		return startedTime;
	}

	public void setStartedTime(LocalDateTime startedTime)
	{
		this.startedTime = startedTime;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Long getPersonaId() {
		return personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	public boolean add(LobbyEntrantEntity e) {
		if (entrants == null) {
			entrants = new ArrayList<>();
		}
		return entrants.add(e);
	}

	public int getLobbyCountdownInMilliseconds(int baseTime) {
		if (startedTime != null) {
//			Long time = System.currentTimeMillis() - lobbyDateTimeStart.getTime();
//			time = baseTime - time;
//			return time.intValue();
//			return System.currentTimeMillis() - startedTime.toEpochSecond()
			return (int) (baseTime - (System.currentTimeMillis() - (startedTime.toEpochSecond(OffsetDateTime.now().getOffset()) * 1000)));
		}
		
		return baseTime;
	}
}