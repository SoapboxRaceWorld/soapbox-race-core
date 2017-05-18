package com.soapboxrace.core.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "LOBBY_ENTRANT")
@NamedQueries({ //
		@NamedQuery(name = "LobbyEntrantEntity.deleteByPersona", query = "DELETE FROM LobbyEntrantEntity obj WHERE obj.persona = :persona") //
})
public class LobbyEntrantEntity implements Comparable<LobbyEntrantEntity> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@XmlTransient
	@JoinColumn(name = "PERSONAID", referencedColumnName = "ID")
	private PersonaEntity persona;

	@ManyToOne
	@XmlTransient
	@JoinColumn(name = "LOBBYID", referencedColumnName = "ID")
	private LobbyEntity lobby;

	private int gridIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonaEntity getPersona() {
		return persona;
	}

	public void setPersona(PersonaEntity persona) {
		this.persona = persona;
	}

	public LobbyEntity getLobby() {
		return lobby;
	}

	public void setLobby(LobbyEntity lobby) {
		this.lobby = lobby;
	}

	@Override
	public int compareTo(LobbyEntrantEntity lobbyEntrantEntity) {
		return this.getPersona().getPersonaId().compareTo(lobbyEntrantEntity.getPersona().getPersonaId());
	}

	public int getGridIndex() {
		return gridIndex;
	}

	public void setGridIndex(int gridIndex) {
		this.gridIndex = gridIndex;
	}

}
