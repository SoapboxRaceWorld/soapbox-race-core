package com.soapboxrace.core.jpa;

public enum SkillModRewardType {
	EXPLORER, SOCIALITE, BOUNTY_HUNTER;

	@Override
	public String toString() {
		System.out.println(super.toString());
		return super.toString().replace("_", " ");
	}
}
