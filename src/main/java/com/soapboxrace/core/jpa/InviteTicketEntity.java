package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "INVITE_TICKET")
// InviteTicketEntity.findByTicket
@NamedQueries({ //
		@NamedQuery(name = "InviteTicketEntity.findAll", query = "SELECT obj FROM InviteTicketEntity obj"),
		@NamedQuery(name = "InviteTicketEntity.findByTicket", query = "SELECT obj FROM InviteTicketEntity obj WHERE obj.ticket = :ticket"), //
		@NamedQuery(name = "InviteTicketEntity.findByDiscordName", query = "SELECT obj FROM InviteTicketEntity obj WHERE obj.discordName = :discordName") })
public class InviteTicketEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "TICKET", length = 255)
	private String ticket;

	@Column(name = "DISCORD_NAME", length = 255)
	private String discordName;

	@ManyToOne
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private UserEntity user;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getDiscordName() {
		return discordName;
	}

	public void setDiscordName(String discordName) {
		this.discordName = discordName;
	}

}
