package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_ROOM")
@NamedQueries({ //
        @NamedQuery(name = "ChatRoomEntity.findAll", query = "SELECT obj FROM ChatRoomEntity obj") //
})
public class ChatRoomEntity {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String longName;
    private String shortName;
    private Integer amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
