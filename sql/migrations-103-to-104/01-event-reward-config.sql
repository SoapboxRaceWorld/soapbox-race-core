CREATE TABLE EVENT_REWARD
(
    ID                         VARCHAR(255) NOT NULL,
    baseRepReward              INT          NOT NULL DEFAULT 0,
    levelRepRewardMultiplier   FLOAT        NOT NULL DEFAULT 0,
    finalRepRewardMultiplier   FLOAT        NOT NULL DEFAULT 0,
    perfectStartRepMultiplier  FLOAT        NOT NULL DEFAULT 0,
    topSpeedRepMultiplier      FLOAT        NOT NULL DEFAULT 0,
    rank1RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank2RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank3RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank4RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank5RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank6RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank7RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    rank8RepMultiplier         FLOAT        NOT NULL DEFAULT 0,
    baseCashReward             INT          NOT NULL DEFAULT 0,
    levelCashRewardMultiplier  FLOAT        NOT NULL DEFAULT 0,
    finalCashRewardMultiplier  FLOAT        NOT NULL DEFAULT 0,
    perfectStartCashMultiplier FLOAT        NOT NULL DEFAULT 0,
    topSpeedCashMultiplier     FLOAT        NOT NULL DEFAULT 0,
    rank1CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank2CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank3CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank4CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank5CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank6CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank7CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    rank8CashMultiplier        FLOAT        NOT NULL DEFAULT 0,
    minTopSpeedTrigger         FLOAT        NOT NULL DEFAULT 0,
    rewardTable_rank1_id       BIGINT       NULL,
    rewardTable_rank2_id       BIGINT       NULL,
    rewardTable_rank3_id       BIGINT       NULL,
    rewardTable_rank4_id       BIGINT       NULL,
    rewardTable_rank5_id       BIGINT       NULL,
    rewardTable_rank6_id       BIGINT       NULL,
    rewardTable_rank7_id       BIGINT       NULL,
    rewardTable_rank8_id       BIGINT       NULL,

    PRIMARY KEY (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK1TABLE_ID FOREIGN KEY (rewardTable_rank1_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK2TABLE_ID FOREIGN KEY (rewardTable_rank2_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK3TABLE_ID FOREIGN KEY (rewardTable_rank3_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK4TABLE_ID FOREIGN KEY (rewardTable_rank4_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK5TABLE_ID FOREIGN KEY (rewardTable_rank5_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK6TABLE_ID FOREIGN KEY (rewardTable_rank6_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK7TABLE_ID FOREIGN KEY (rewardTable_rank7_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK8TABLE_ID FOREIGN KEY (rewardTable_rank8_id) REFERENCES REWARD_TABLE (ID)
);

INSERT INTO EVENT_REWARD (ID, baseRepReward, levelRepRewardMultiplier, finalRepRewardMultiplier,
                          perfectStartRepMultiplier, topSpeedRepMultiplier, rank1RepMultiplier, rank2RepMultiplier,
                          rank3RepMultiplier, rank4RepMultiplier, rank5RepMultiplier, rank6RepMultiplier,
                          rank7RepMultiplier, rank8RepMultiplier, baseCashReward, levelCashRewardMultiplier,
                          finalCashRewardMultiplier, perfectStartCashMultiplier, topSpeedCashMultiplier,
                          rank1CashMultiplier, rank2CashMultiplier, rank3CashMultiplier, rank4CashMultiplier,
                          rank5CashMultiplier, rank6CashMultiplier, rank7CashMultiplier, rank8CashMultiplier,
                          minTopSpeedTrigger, rewardTable_rank1_id, rewardTable_rank2_id, rewardTable_rank3_id,
                          rewardTable_rank4_id, rewardTable_rank5_id, rewardTable_rank6_id, rewardTable_rank7_id,
                          rewardTable_rank8_id)
SELECT CONCAT(E.ID, '_generic'),
       E.baseRepReward,
       E.levelRepRewardMultiplier,
       E.finalRepRewardMultiplier,
       E.perfectStartRepMultiplier,
       E.topSpeedRepMultiplier,
       E.rank1RepMultiplier,
       E.rank2RepMultiplier,
       E.rank3RepMultiplier,
       E.rank4RepMultiplier,
       E.rank5RepMultiplier,
       E.rank6RepMultiplier,
       E.rank7RepMultiplier,
       E.rank8RepMultiplier,
       E.baseCashReward,
       E.levelCashRewardMultiplier,
       E.finalCashRewardMultiplier,
       E.perfectStartCashMultiplier,
       E.topSpeedCashMultiplier,
       E.rank1CashMultiplier,
       E.rank2CashMultiplier,
       E.rank3CashMultiplier,
       E.rank4CashMultiplier,
       E.rank5CashMultiplier,
       E.rank6CashMultiplier,
       E.rank7CashMultiplier,
       E.rank8CashMultiplier,
       E.minTopSpeedTrigger,
       E.rewardTable_rank1_id,
       E.rewardTable_rank2_id,
       E.rewardTable_rank3_id,
       E.rewardTable_rank4_id,
       E.rewardTable_rank5_id,
       E.rewardTable_rank6_id,
       E.rewardTable_rank7_id,
       E.rewardTable_rank8_id
FROM EVENT E;

ALTER TABLE EVENT
    DROP COLUMN baseRepReward;
ALTER TABLE EVENT
    DROP COLUMN levelRepRewardMultiplier;
ALTER TABLE EVENT
    DROP COLUMN finalRepRewardMultiplier;
ALTER TABLE EVENT
    DROP COLUMN perfectStartRepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN topSpeedRepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank1RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank2RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank3RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank4RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank5RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank6RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank7RepMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank8RepMultiplier;

ALTER TABLE EVENT
    DROP COLUMN baseCashReward;
ALTER TABLE EVENT
    DROP COLUMN levelCashRewardMultiplier;
ALTER TABLE EVENT
    DROP COLUMN finalCashRewardMultiplier;
ALTER TABLE EVENT
    DROP COLUMN perfectStartCashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN topSpeedCashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank1CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank2CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank3CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank4CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank5CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank6CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank7CashMultiplier;
ALTER TABLE EVENT
    DROP COLUMN rank8CashMultiplier;

ALTER TABLE EVENT
    DROP COLUMN minTopSpeedTrigger;

ALTER TABLE EVENT
    DROP FOREIGN KEY FK2xj5h4dvsmiy3fpyayhmgsm7y;
ALTER TABLE EVENT
    DROP FOREIGN KEY FK5r8fjqwem02m1b4oyc6yy1esq;
ALTER TABLE EVENT
    DROP FOREIGN KEY FK8g17bk7hkmabmnf88srvslxwm;
ALTER TABLE EVENT
    DROP FOREIGN KEY FKalw5jfl728ahfpvdwhegdty6w;
ALTER TABLE EVENT
    DROP FOREIGN KEY FKdwjw797e5pirj7rnvcr6d71pi;
ALTER TABLE EVENT
    DROP FOREIGN KEY FKfh6oit4kajywxhmxtid3yfukk;
ALTER TABLE EVENT
    DROP FOREIGN KEY FKfpntheixvd1mgifia39l2tf4b;
ALTER TABLE EVENT
    DROP FOREIGN KEY FKhky4tfvfpo91vixa09jkofjnc;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank1_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank2_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank3_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank4_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank5_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank6_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank7_id;
ALTER TABLE EVENT
    DROP COLUMN rewardTable_rank8_id;
ALTER TABLE EVENT_REWARD
    CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE `EVENT`
    CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE EVENT
    ADD COLUMN singleplayer_reward_config_id VARCHAR(255) NOT NULL;
ALTER TABLE EVENT
    ADD COLUMN multiplayer_reward_config_id VARCHAR(255) NOT NULL;
ALTER TABLE EVENT
    ADD COLUMN private_reward_config_id VARCHAR(255) NOT NULL;
UPDATE EVENT E
SET singleplayer_reward_config_id=CONCAT(E.ID, '_generic'),
    multiplayer_reward_config_id=CONCAT(E.ID, '_generic'),
    private_reward_config_id=CONCAT(E.ID, '_generic')
WHERE 1=1;
ALTER TABLE EVENT
    ADD CONSTRAINT FK_EVENT_SINGLEPLAYER_REWARD_CONFIG_ID FOREIGN KEY (singleplayer_reward_config_id) REFERENCES EVENT_REWARD (ID);
ALTER TABLE EVENT
    ADD CONSTRAINT FK_EVENT_MULTIPLAYER_REWARD_CONFIG_ID FOREIGN KEY (multiplayer_reward_config_id) REFERENCES EVENT_REWARD (ID);
ALTER TABLE EVENT
    ADD CONSTRAINT FK_EVENT_PRIVATE_REWARD_CONFIG_ID FOREIGN KEY (private_reward_config_id) REFERENCES EVENT_REWARD (ID);