/*
 * This file is part of the Soapbox Race World core source code.
 * If you use any of this code for third-party purposes, please provide attribution.
 * Copyright (c) 2020.
 */

package com.soapboxrace.core.jpa;

import javax.persistence.*;

@Entity
@Table(name = "EVENT_DATA")
@NamedQueries({ //
        @NamedQuery(name = "EventDataEntity.findByPersona", query = "SELECT obj FROM EventDataEntity obj WHERE obj" +
                ".personaId = :personaId"), //
        @NamedQuery(name = "EventDataEntity.getRacers", query = "SELECT obj FROM EventDataEntity obj WHERE obj" +
                ".eventSessionId = :eventSessionId"), //
        @NamedQuery(name = "EventDataEntity.findByPersonaAndType", query = "SELECT obj FROM EventDataEntity obj WHERE" +
                " obj.personaId = :personaId AND obj.eventModeId = :eventModeId"), //
        @NamedQuery(name = "EventDataEntity.findByPersonaAndEventSessionId", query = "SELECT obj FROM EventDataEntity" +
                " obj WHERE obj.personaId = :personaId AND obj.eventSessionId = :eventSessionId") //
})
public class EventDataEntity {

    // OtherArbitrationPacket Global variables
    protected long longestJumpDurationInMilliseconds;
    protected long sumOfJumpsDurationInMilliseconds;
    protected float topSpeed;
    // RouteArbitrationPacket variables
    protected long bestLapDurationInMilliseconds;
    protected float fractionCompleted;
    protected int numberOfCollisions;
    protected int perfectStart;
    // PursuitArbitrationPacket and TeamEscapeArbitrationPacket Global variable
    protected int copsDeployed;
    protected int copsDisabled;
    protected int copsRammed;
    protected int costToState;
    protected int infractions;
    protected int roadBlocksDodged;
    protected int spikeStripsDodged;
    // PursuitArbitrationPacket variable
    protected float heat;
    // TeamEscapeArbitrationPacket variables
    protected int bustedCount;
    protected float distanceToFinish;
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "EVENTID", referencedColumnName = "ID", foreignKey = @ForeignKey(name = "FK_EVENTDATA_EVENT_EVENTID"))
    private EventEntity event;
    private Long personaId;
    private int eventModeId;
    private Long eventSessionId;
    // ArbitrationPacket variables
    private long alternateEventDurationInMilliseconds;
    private long carId;
    private long eventDurationInMilliseconds;
    private int finishReason;
    private long hacksDetected;
    @Column(name = "`rank`")
    private int rank;
    @Column(columnDefinition = "BIT default 0")
    private boolean isLegit;
    private long serverTimeInMilliseconds;
    private long serverTimeStarted;
    private long serverTimeEnded;
    private Integer carClassHash;
    private Integer carRating;

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

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long value) {
        this.personaId = value;
    }

    public int getEventModeId() {
        return eventModeId;
    }

    public void setEventModeId(int value) {
        this.eventModeId = value;
    }

    public Long getEventSessionId() {
        return eventSessionId;
    }

    public void setEventSessionId(Long value) {
        this.eventSessionId = value;
    }

    // ArbitrationPacket functions
    public long getAlternateEventDurationInMilliseconds() {
        return alternateEventDurationInMilliseconds;
    }

    public void setAlternateEventDurationInMilliseconds(long value) {
        this.alternateEventDurationInMilliseconds = value;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long value) {
        this.carId = value;
    }

    public long getEventDurationInMilliseconds() {
        return eventDurationInMilliseconds;
    }

    public void setEventDurationInMilliseconds(long value) {
        this.eventDurationInMilliseconds = value;
    }

    public int getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(int value) {
        this.finishReason = value;
    }

    public long getHacksDetected() {
        return hacksDetected;
    }

    public void setHacksDetected(long value) {
        this.hacksDetected = value;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int value) {
        this.rank = value;
    }

    // OtherArbitrationPacket Global functions
    public long getLongestJumpDurationInMilliseconds() {
        return longestJumpDurationInMilliseconds;
    }

    public void setLongestJumpDurationInMilliseconds(long value) {
        this.longestJumpDurationInMilliseconds = value;
    }

    public long getSumOfJumpsDurationInMilliseconds() {
        return sumOfJumpsDurationInMilliseconds;
    }

    public void setSumOfJumpsDurationInMilliseconds(long value) {
        this.sumOfJumpsDurationInMilliseconds = value;
    }

    public float getTopSpeed() {
        return topSpeed;
    }

    public void setTopSpeed(float value) {
        this.topSpeed = value;
    }

    // RouteArbitrationPacket functions
    public long getBestLapDurationInMilliseconds() {
        return bestLapDurationInMilliseconds;
    }

    public void setBestLapDurationInMilliseconds(long value) {
        this.bestLapDurationInMilliseconds = value;
    }

    public float getFractionCompleted() {
        return fractionCompleted;
    }

    public void setFractionCompleted(float value) {
        this.fractionCompleted = value;
    }

    public int getNumberOfCollisions() {
        return numberOfCollisions;
    }

    public void setNumberOfCollisions(int value) {
        this.numberOfCollisions = value;
    }

    public int getPerfectStart() {
        return perfectStart;
    }

    public void setPerfectStart(int value) {
        this.perfectStart = value;
    }

    // PursuitArbitrationPacket and TeamEscapeArbitrationPacket Global functions
    public int getCopsDeployed() {
        return copsDeployed;
    }

    public void setCopsDeployed(int value) {
        this.copsDeployed = value;
    }

    public int getCopsDisabled() {
        return copsDisabled;
    }

    public void setCopsDisabled(int value) {
        this.copsDisabled = value;
    }

    public int getCopsRammed() {
        return copsRammed;
    }

    public void setCopsRammed(int value) {
        this.copsRammed = value;
    }

    public int getCostToState() {
        return costToState;
    }

    public void setCostToState(int value) {
        this.costToState = value;
    }

    public int getInfractions() {
        return infractions;
    }

    public void setInfractions(int value) {
        this.infractions = value;
    }

    public int getRoadBlocksDodged() {
        return roadBlocksDodged;
    }

    public void setRoadBlocksDodged(int value) {
        this.roadBlocksDodged = value;
    }

    public int getSpikeStripsDodged() {
        return spikeStripsDodged;
    }

    public void setSpikeStripsDodged(int value) {
        this.spikeStripsDodged = value;
    }

    // PursuitArbitrationPacket function
    public float getHeat() {
        return heat;
    }

    public void setHeat(float value) {
        this.heat = value;
    }

    // TeamEscapeArbitrationPacket functions
    public int getBustedCount() {
        return bustedCount;
    }

    public void setBustedCount(int value) {
        this.bustedCount = value;
    }

    public float getDistanceToFinish() {
        return distanceToFinish;
    }

    public void setDistanceToFinish(float value) {
        this.distanceToFinish = value;
    }

    public boolean isLegit() {
        return isLegit;
    }

    public void setLegit(boolean legit) {
        isLegit = legit;
    }

    public long getServerTimeInMilliseconds() {
        return serverTimeInMilliseconds;
    }

    public void setServerTimeInMilliseconds(long serverTimeInMilliseconds) {
        this.serverTimeInMilliseconds = serverTimeInMilliseconds;
    }

    public long getServerTimeStarted() {
        return serverTimeStarted;
    }

    public void setServerTimeStarted(long serverTimeStarted) {
        this.serverTimeStarted = serverTimeStarted;
    }

    public long getServerTimeEnded() {
        return serverTimeEnded;
    }

    public void setServerTimeEnded(long serverTimeEnded) {
        this.serverTimeEnded = serverTimeEnded;
    }

    public Integer getCarClassHash() {
        return carClassHash;
    }

    public void setCarClassHash(Integer carClassHash) {
        this.carClassHash = carClassHash;
    }

    public Integer getCarRating() {
        return carRating;
    }

    public void setCarRating(Integer carRating) {
        this.carRating = carRating;
    }
}
