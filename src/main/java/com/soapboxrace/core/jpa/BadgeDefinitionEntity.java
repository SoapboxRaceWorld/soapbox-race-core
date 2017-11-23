package com.soapboxrace.core.jpa;

import com.soapboxrace.jaxb.http.BadgeDefinitionPacket;

import javax.persistence.*;

@Entity
@Table(name = "BADGEDEFINITION")
public class BadgeDefinitionEntity {
	private static final long serialVersionUID = 5314835854384144787L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "background")
	private String background;
	
	@Column(name = "border")
	private String border;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "icon")
	private String icon;
	
	@Column(name = "name")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBackground()
	{
		return background;
	}

	public void setBackground(String background)
	{
		this.background = background;
	}

	public String getBorder()
	{
		return border;
	}

	public void setBorder(String border)
	{
		this.border = border;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public BadgeDefinitionPacket toPacket() {
        BadgeDefinitionPacket packet = new BadgeDefinitionPacket();
		packet.setBackground(background);
		packet.setBadgeDefinitionId(id.intValue());
		packet.setBorder(border);
		packet.setDescription(description);
		packet.setIcon(icon);
		packet.setName(name);
		
		return packet;
	}
}