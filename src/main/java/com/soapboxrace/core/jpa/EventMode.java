package com.soapboxrace.core.jpa;

public enum EventMode {

	SPRINT(9), CIRCUIT(4), DRAG(19), PURSUIT_SP(12), PURSUIT_MP(24), MEETINGPLACE(22);
	private final int eventModeId;

	public int getEventModeId() {
		return eventModeId;
	}

	private EventMode(int eventModeId) {
		this.eventModeId = eventModeId;
	}

	public static EventMode fromId(int id) {
		for (EventMode type : EventMode.values()) {
			if (type.getEventModeId() == id) {
				return type;
			}
		}
		return null;
	}
}
