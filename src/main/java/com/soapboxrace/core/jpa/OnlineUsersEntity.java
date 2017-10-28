package com.soapboxrace.core.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "ONLINE_USERS")
@NamedQueries({ //
		@NamedQuery(name = "OnlineUsersEntity.findByTime", //
				query = "SELECT obj FROM OnlineUsersEntity obj WHERE obj.timeRecord > :time ORDER BY obj.timeRecord") //

})
public class OnlineUsersEntity {

	@Id
	@Column(name = "ID", nullable = false)
	private int timeRecord;

	private int numberOfUsers = 0;

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public int getTimeRecord() {
		return timeRecord;
	}

	public void setTimeRecord(int timeRecord) {
		this.timeRecord = timeRecord;
	}
}
