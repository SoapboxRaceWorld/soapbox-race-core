/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2021.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "K_CREW_MEMBER")
@NamedQueries({
    @NamedQuery(name = "KCrewMemberEntity.findCrewMembershipByPersonaId", query = "SELECT obj FROM KCrewMemberEntity obj WHERE obj.persona.personaId = :personaid")
})
public class KCrewMemberEntity {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crewMemberId;

    @OneToOne(targetEntity = PersonaEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "personaID", referencedColumnName = "ID")
    private PersonaEntity persona;

    @Column(name = "date_join")
    private LocalDateTime dateJoin;

    @OneToOne(targetEntity = KCrewEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "crewId", referencedColumnName = "ID")
    private KCrewEntity crew;

    private Long points;
    private int canManage;

    public KCrewEntity getCrew() {
        return crew;
    }

    public void getCrew(KCrewEntity crew) { 
        this.crew = crew; 
    }
}