package com.soapboxrace.core.jpa;

import com.soapboxrace.core.jpa.convert.BanTypeConverter;
import com.soapboxrace.core.jpa.convert.LocalDateTimeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BAN")
@NamedQueries({ //
        @NamedQuery(name = "BanEntity.findAll", query = "SELECT obj FROM BanEntity obj")
})
public class BanEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    
    @Column
    @Convert(converter = BanTypeConverter.class)
    private BanType type;
    
    @Column
    private String data;
    
    @Column
    private String reason;
    
    @Column
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime endsAt;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public UserEntity getUserEntity()
    {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity)
    {
        this.userEntity = userEntity;
    }

    public BanType getType()
    {
        return type;
    }

    public void setType(BanType type)
    {
        this.type = type;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public LocalDateTime getEndsAt()
    {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt)
    {
        this.endsAt = endsAt;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public boolean stillApplies() 
    {
        return this.endsAt == null || LocalDateTime.now().isBefore(this.endsAt);    
    }
    
    public enum BanType
    {
        USER_BAN,
        IP_BAN,
        HWID_BAN,
        EMAIL_BAN
    }
}
