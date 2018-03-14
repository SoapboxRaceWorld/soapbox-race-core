package com.soapboxrace.core.bo.util;

import com.soapboxrace.jaxb.http.ArrayOfRewardPart;
import com.soapboxrace.jaxb.http.EnumRewardCategory;
import com.soapboxrace.jaxb.http.EnumRewardType;
import com.soapboxrace.jaxb.http.RewardPart;

public class RewardVO {

	int rep = 0;
	int cash = 0;
	ArrayOfRewardPart arrayOfRewardPart = new ArrayOfRewardPart();
	private boolean enableEconomy;
	private boolean enableReputation;

	public RewardVO(boolean enableEconomy, boolean enableReputation) {
		this.enableEconomy = enableEconomy;
		this.enableReputation = enableReputation;
	}

	public void add(int rep, int cash, EnumRewardCategory enumRewardCategory, EnumRewardType enumRewardType) {
		if (!enableReputation) {
			rep = 0;
		}
		if (!enableEconomy) {
			cash = 0;
		}
		RewardPart rewardPart = new RewardPart();
		rewardPart.setRepPart(rep);
		rewardPart.setTokenPart(cash);
		rewardPart.setRewardCategory(enumRewardCategory);
		rewardPart.setRewardType(enumRewardType);
		this.rep += rep;
		this.cash += cash;
		arrayOfRewardPart.getRewardPart().add(rewardPart);
	}

	public int getRep() {
		return rep;
	}

	public int getCash() {
		return cash;
	}

	public ArrayOfRewardPart getArrayOfRewardPart() {
		return arrayOfRewardPart;
	}

}
