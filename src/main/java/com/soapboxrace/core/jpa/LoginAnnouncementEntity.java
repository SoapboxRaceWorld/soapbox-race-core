package com.soapboxrace.core.jpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "LOGIN_ANNOUCEMENT")
@NamedQueries({ //
		@NamedQuery(name = "LoginAnnouncementEntity.findAll", query = "SELECT obj FROM LoginAnnouncementEntity obj") //
})
public class LoginAnnouncementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String imageUrl;
	private String type;
	private String target;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}