# v1.0.3 -> v1.0.4
# as of july 6 2020

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

CREATE TABLE EVENT_REWARD
(
    ID                         VARCHAR(255) NOT NULL,
    event_id                   INT          NOT NULL,
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
    rewardTable_rank1_id       BIGINT       NOT NULL,
    rewardTable_rank2_id       BIGINT       NOT NULL,
    rewardTable_rank3_id       BIGINT       NOT NULL,
    rewardTable_rank4_id       BIGINT       NOT NULL,
    rewardTable_rank5_id       BIGINT       NOT NULL,
    rewardTable_rank6_id       BIGINT       NOT NULL,
    rewardTable_rank7_id       BIGINT       NOT NULL,
    rewardTable_rank8_id       BIGINT       NOT NULL,

    PRIMARY KEY (ID),
    CONSTRAINT FK_EVENT_REWARD_EVENTID FOREIGN KEY (event_id) REFERENCES EVENT (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK1TABLE_ID FOREIGN KEY (rewardTable_rank1_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK2TABLE_ID FOREIGN KEY (rewardTable_rank2_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK3TABLE_ID FOREIGN KEY (rewardTable_rank3_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK4TABLE_ID FOREIGN KEY (rewardTable_rank4_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK5TABLE_ID FOREIGN KEY (rewardTable_rank5_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK6TABLE_ID FOREIGN KEY (rewardTable_rank6_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK7TABLE_ID FOREIGN KEY (rewardTable_rank7_id) REFERENCES REWARD_TABLE (ID),
    CONSTRAINT FK_EVENT_REWARD_RANK8TABLE_ID FOREIGN KEY (rewardTable_rank8_id) REFERENCES REWARD_TABLE (ID)
);