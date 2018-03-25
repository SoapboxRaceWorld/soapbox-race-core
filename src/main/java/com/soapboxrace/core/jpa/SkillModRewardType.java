package com.soapboxrace.core.jpa;

public enum SkillModRewardType {
	EXPLORER, SOCIALITE, BOUNTY_HUNTER;

	@Override
	public String toString() {
		return super.toString().replace("_", " ");
	}
}
