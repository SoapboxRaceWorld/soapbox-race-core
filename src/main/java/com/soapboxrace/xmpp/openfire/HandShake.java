package com.soapboxrace.xmpp.openfire;

import com.soapboxrace.core.bo.ParameterBO;
import com.soapboxrace.xmpp.openfire.shard.ShardedClientHandshake;
import com.soapboxrace.xmpp.openfire.shard.ShardedMasterHandshake;
import com.soapboxrace.xmpp.openfire.standard.StandardHandshake;

public class HandShake {

	private ParameterBO parameterBO = new ParameterBO();
	
	private OpenFireTalk openFireTalk;

	public HandShake() {
		if (!parameterBO.isShardingEnabled()) {
			this.openFireTalk = new StandardHandshake().getOpenFireTalk();
		} else {
			if (parameterBO.isShardingEnabled()) {
				if (!parameterBO.isShardingMaster()) {
					this.openFireTalk = new ShardedClientHandshake().getOpenFireTalk();
				} else {
					this.openFireTalk = new ShardedMasterHandshake().getOpenFireTalk();
				}
			}
		}
	}

	public OpenFireTalk getXmppTalk() {
		return openFireTalk;
	}

}
