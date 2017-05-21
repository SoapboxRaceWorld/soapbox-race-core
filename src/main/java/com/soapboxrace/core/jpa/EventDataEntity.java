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
@Table(name = "EVENT_DATA")
@NamedQueries({ //
	@NamedQuery(name = "EventDataEntity.findByPersona", query = "SELECT obj FROM EventDataEntity obj WHERE obj.personaId = :personaId"), //
	@NamedQuery(name = "EventDataEntity.getRacers", query = "SELECT obj FROM EventDataEntity obj WHERE obj.id = :id"), //
	@NamedQuery(name = "EventDataEntity.findByPersonaAndType", query = "SELECT obj FROM EventDataEntity obj WHERE obj.personaId = :personaId AND obj.eventModeId = :eventModeId"), //
	@NamedQuery(name = "EventDataEntity.findByPersonaAndEventSessionId", query = "SELECT obj FROM EventDataEntity obj WHERE obj.personaId = :personaId AND obj.eventSessionId = :eventSessionId") //
})
public class EventDataEntity {

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "EVENTID", referencedColumnName = "ID")
	private EventEntity event;
	
	@Column(name = "PERSONAID")
	private Long personaId;
	@Column(name = "EVENTMODEID")
	private int eventModeId;
	@Column(name = "EVENTSESSIONID")
	private Long eventSessionId;
	
	// ArbitrationPacket variables
	@Column(name = "ALTERNATEEVENTDURATIONBINMS")
	private long alternateEventDurationInMilliseconds;
	@Column(name = "CarId")
	private long carId;
	@Column(name = "EVENTDURATIONINMS")
	private long eventDurationInMilliseconds;
	@Column(name = "FINISHREASON")
	private int finishReason;
	@Column(name = "HACKSDETECTED")
	private long hacksDetected;
	@Column(name = "RANK")
	private int rank;
	
	// OtherArbitrationPacket Global variables
	@Column(name = "LONGESTJUMPDURATIONINMS")
    protected long longestJumpDurationInMilliseconds;
	@Column(name = "SUMOFJUMPSDURATIONINMS")
    protected long sumOfJumpsDurationInMilliseconds;
	@Column(name = "TOPSPEED")
    protected float topSpeed;
	
	// RouteArbitrationPacket variables
	@Column(name = "BESTLAPDURATIONINMS")
    protected long bestLapDurationInMilliseconds;
	@Column(name = "FRACTIONCOMPLETED")
    protected float fractionCompleted;
	@Column(name = "NUMBEROFCOLLISIONS")
    protected int numberOfCollisions;
	@Column(name = "PERFECTSTART")
    protected int perfectStart;
	
	
	// PursuitArbitrationPacket and TeamEscapeArbitrationPacket Global variable
	@Column(name = "CopsDeployed")
	protected int copsDeployed;
	@Column(name = "CopsDisabled")
	protected int copsDisabled;
	@Column(name = "CopsRammed")
	protected int copsRammed;
	@Column(name = "CostToState")
	protected int costToState;
	@Column(name = "Infractions")
	protected int infractions;
	@Column(name = "RoadBlocksDodged")
	protected int roadBlocksDodged;
	@Column(name = "SpikeStripsDodged")
	protected int spikeStripsDodged;
	
	// PursuitArbitrationPacket variable
	@Column(name = "Heat")
	protected float heat;
	
	// TeamEscapeArbitrationPacket variables
	@Column(name = "BustedCount")
    protected int bustedCount;
	@Column(name = "DistanceToFinish")
    protected float distanceToFinish;

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

}
