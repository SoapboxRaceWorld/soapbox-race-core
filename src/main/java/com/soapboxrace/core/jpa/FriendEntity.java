package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "FRIEND")
@NamedQueries({ //
        @NamedQuery(name = "FriendEntity.findByUser", query = "SELECT obj FROM FriendEntity obj WHERE obj.user.id = :id"), //
        @NamedQuery(name = "FriendEntity.findBySenderAndRecipient", query = "SELECT obj FROM FriendEntity obj WHERE " +
                "obj.user.id = :recipientUserId AND obj.fromUser.id = :senderId"), //
})
public class FriendEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "fromUserId")
    private UserEntity fromUser;

    @Column
    private Long fromPersonaId;

    @Column
    private int status; // 0=pending,1=accepted

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getFromPersonaId() {
        return fromPersonaId;
    }

    public void setFromPersonaId(Long personaId) {
        this.fromPersonaId = personaId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public UserEntity getFromUser() {
        return fromUser;
    }

    public void setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
    }
}