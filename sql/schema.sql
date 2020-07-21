create table if not exists ACHIEVEMENT_REWARD
(
    ID                          bigint auto_increment
        primary key,
    internal_reward_description varchar(255) null,
    reward_description          varchar(255) null,
    rewardScript                text         not null
)
    charset = latin1;

create index ACHIEVEMENT_REWARD_internal_reward_description_index
    on ACHIEVEMENT_REWARD (internal_reward_description);

create table if not exists BADGE_DEFINITION
(
    ID          bigint auto_increment
        primary key,
    background  varchar(255) null,
    border      varchar(255) null,
    description varchar(255) null,
    icon        varchar(255) null,
    name        varchar(255) null
)
    charset = latin1;

create table if not exists ACHIEVEMENT
(
    ID                        bigint auto_increment
        primary key,
    auto_update               bit                                                                null,
    category                  varchar(255)                                                       null,
    name                      varchar(255)                                                       null,
    progress_text             varchar(255)                                                       null,
    should_overwrite_progress bit default b'0'                                                   null,
    stat_conversion           enum ('None', 'FromMetersToDistance', 'FromMillisecondsToMinutes') null,
    update_trigger            text                                                               null,
    update_value              text                                                               null,
    visible                   bit                                                                null,
    badge_definition_id       bigint                                                             not null,
    constraint UK_so6kbq2i8oy15k8f9m7vuylu5
        unique (badge_definition_id),
    constraint FK6xo8y7evmxkq6rbqgqlngxnxh
        foreign key (badge_definition_id) references BADGE_DEFINITION (ID)
)
    charset = latin1;

create index ACHIEVEMENT_category_index
    on ACHIEVEMENT (category);

create table if not exists ACHIEVEMENT_RANK
(
    ID                  bigint auto_increment
        primary key,
    points              int          null,
    `rank`              int          null,
    rarity              float        null,
    reward_description  varchar(255) null,
    reward_type         varchar(255) null,
    reward_visual_style varchar(255) null,
    threshold_value     int          null,
    achievement_id      bigint       not null,
    constraint FK276d2ojf04ey397aixbri1j42
        foreign key (achievement_id) references ACHIEVEMENT (ID)
)
    charset = latin1;

create table if not exists BASKETDEFINITION
(
    productId     varchar(255) not null
        primary key,
    ownedCarTrans longtext     null,
    constraint BASKETDEFINITION_productId_uindex
        unique (productId)
)
    charset = latin1;

alter table BASKETDEFINITION
    add primary key (productId);

create table if not exists CARD_PACK
(
    ID             bigint auto_increment
        primary key,
    entitlementTag varchar(255) null
)
    charset = latin1;

create table if not exists CARD_PACK_ITEM
(
    ID                bigint auto_increment
        primary key,
    script            text   not null,
    cardPackEntity_ID bigint not null,
    constraint FK3wo6p0la6hflhtss9iqj5t5h6
        foreign key (cardPackEntity_ID) references CARD_PACK (ID)
)
    charset = latin1;

create table if not exists CAR_CLASSES
(
    store_name varchar(255) collate utf8mb4_unicode_ci not null,
    full_name  varchar(255)                            not null,
    manufactor varchar(255)                            not null,
    model      varchar(255)                            not null,
    ts_stock   int                                     null,
    ts_var1    int                                     null,
    ts_var2    int                                     null,
    ts_var3    int                                     null,
    ac_stock   int                                     null,
    ac_var1    int                                     null,
    ac_var2    int                                     null,
    ac_var3    int                                     null,
    ha_stock   int                                     null,
    ha_var1    int                                     null,
    ha_var2    int                                     null,
    ha_var3    int                                     null,
    hash       int                                     null,
    product_id varchar(255) collate utf8_unicode_ci    null,
    constraint store_name_index
        unique (store_name)
)
    charset = utf8;

create index hash_index
    on CAR_CLASSES (hash);

create index store_name_key
    on CAR_CLASSES (store_name);

alter table CAR_CLASSES
    add primary key (store_name);

create table if not exists CATEGORY
(
    idcategory           bigint auto_increment
        primary key,
    catalogVersion       varchar(255) null,
    categories           varchar(255) null,
    displayName          varchar(255) null,
    filterType           int          null,
    icon                 varchar(255) null,
    id                   bigint       null,
    longDescription      varchar(255) null,
    name                 varchar(255) null,
    priority             smallint     null,
    shortDescription     varchar(255) null,
    showInNavigationPane bit          null,
    showPromoPage        bit          null,
    webIcon              varchar(255) null
)
    charset = latin1;

create table if not exists CHAT_ANNOUNCEMENT
(
    id                   int auto_increment
        primary key,
    announcementInterval int          null,
    announcementMessage  varchar(255) null,
    channelMask          varchar(255) null
)
    charset = latin1;

create table if not exists CHAT_ROOM
(
    ID        bigint auto_increment
        primary key,
    amount    int                                  null,
    longName  varchar(255) collate utf8_unicode_ci null,
    shortName varchar(255) collate utf8_unicode_ci null
)
    charset = utf8;

create table if not exists GIFT_CODE
(
    ID       bigint auto_increment
        primary key,
    code     varchar(255) null,
    endsAt   datetime     null,
    startsAt datetime     null
)
    charset = latin1;

create table if not exists HARDWARE_INFO
(
    ID           bigint auto_increment
        primary key,
    banned       bit          not null,
    hardwareHash varchar(255) null,
    hardwareInfo longtext     null,
    userId       bigint       null
)
    charset = latin1;

create index HARDWARE_INFO_hardwareHash_index
    on HARDWARE_INFO (hardwareHash);

create table if not exists LEVEL_REP
(
    level    bigint auto_increment
        primary key,
    expPoint bigint null
)
    charset = utf8;

create table if not exists LOGIN_ANNOUCEMENT
(
    id       int auto_increment
        primary key,
    imageUrl varchar(255) collate utf8_unicode_ci null,
    target   varchar(255) collate utf8_unicode_ci null,
    type     varchar(255) collate utf8_unicode_ci null
)
    charset = utf8;

create table if not exists ONLINE_USERS
(
    ID                 int    not null,
    numberOfOnline     bigint not null,
    numberOfRegistered bigint not null,
    constraint ONLINE_USERS_id_index
        unique (ID desc)
)
    charset = utf8;

alter table ONLINE_USERS
    add primary key (ID);

create table if not exists PARAMETER
(
    name  varchar(255) not null,
    value varchar(255) null,
    constraint PARAMETER_name_index
        unique (name)
)
    charset = latin1;

alter table PARAMETER
    add primary key (name);

create table if not exists PRODUCT
(
    id              bigint auto_increment
        primary key,
    accel           int          null,
    brand           varchar(255) null,
    categoryId      varchar(255) null,
    categoryName    varchar(255) null,
    currency        varchar(255) not null,
    description     varchar(255) null,
    dropWeight      double       null,
    durationMinute  int          not null,
    enabled         bit          not null,
    entitlementTag  varchar(255) null,
    handling        int          null,
    hash            int          null,
    icon            varchar(255) not null,
    isDropable      bit          not null,
    level           int          not null,
    longDescription varchar(255) null,
    minLevel        int          not null,
    premium         bit          not null,
    price           float        not null,
    priority        int          not null,
    productId       varchar(255) not null,
    productTitle    varchar(255) null,
    productType     varchar(255) not null,
    rarity          int          null,
    resalePrice     float        not null,
    secondaryIcon   varchar(255) null,
    skillValue      float        null,
    subType         varchar(255) null,
    topSpeed        int          null,
    useCount        int          not null,
    visualStyle     varchar(255) null,
    webIcon         varchar(255) null,
    webLocation     varchar(255) null,
    parentProductId bigint       null,
    bundleItems     text         null,
    constraint UK_10bw1u87a77ibq6hdbivxp1kp
        unique (productId),
    constraint FK8obn8l9i769slt8pjub191l52
        foreign key (parentProductId) references PRODUCT (id)
)
    charset = latin1;

create table if not exists AMPLIFIERS
(
    id             bigint auto_increment
        primary key,
    ampType        varchar(255) null,
    cashMultiplier float        null,
    repMultiplier  float        null,
    product_id     varchar(255) not null,
    constraint UK_dpq9a1cdlxuukwbqor9ua1j2a
        unique (product_id),
    constraint FK8dw7pn0b32ngrloirp6m9xx5x
        foreign key (product_id) references PRODUCT (productId)
)
    charset = latin1;

create index PRODUCT_availability_index
    on PRODUCT (categoryName, productType, enabled, minLevel, premium);

create index PRODUCT_entitlementTag_index
    on PRODUCT (entitlementTag);

create index PRODUCT_hash_index
    on PRODUCT (hash);

create index parent_prod_id_index
    on PRODUCT (parentProductId);

create index prod_id_index
    on PRODUCT (productId);

create table if not exists RECOVERY_PASSWORD
(
    id             bigint auto_increment
        primary key,
    expirationDate datetime                             null,
    isClose        bit                                  null,
    randomKey      varchar(255) collate utf8_unicode_ci null,
    userId         bigint                               null
)
    charset = utf8;

create table if not exists REPORT
(
    id              bigint auto_increment
        primary key,
    abuserPersonaId bigint       null,
    chatMinutes     int          null,
    customCarID     int          null,
    description     varchar(255) null,
    hacksdetected   bigint       null,
    personaId       bigint       null,
    petitionType    int          null
)
    collate = utf8mb4_unicode_ci;

create table if not exists REWARD_TABLE
(
    ID   bigint auto_increment
        primary key,
    name varchar(255) null,
    constraint REWARD_TABLE_name_index
        unique (name)
)
    charset = latin1;

create table if not exists EVENT
(
    ID                         int auto_increment
        primary key,
    baseCashReward             int                                  not null,
    baseRepReward              int                                  not null,
    eventModeId                int                                  not null,
    finalCashRewardMultiplier  float                                not null,
    finalRepRewardMultiplier   float                                not null,
    isEnabled                  bit    default b'1'                  null,
    isLocked                   bit    default b'0'                  null,
    rewardsTimeLimit           bigint default 0                     not null,
    levelCashRewardMultiplier  float                                not null,
    levelRepRewardMultiplier   float                                not null,
    maxCarClassRating          int                                  not null,
    maxLevel                   int                                  not null,
    maxPlayers                 int                                  not null,
    minCarClassRating          int                                  not null,
    minLevel                   int                                  not null,
    minTopSpeedTrigger         float                                not null,
    name                       varchar(255) collate utf8_unicode_ci null,
    perfectStartCashMultiplier float                                not null,
    perfectStartRepMultiplier  float                                not null,
    rank1CashMultiplier        float                                not null,
    rank1RepMultiplier         float                                not null,
    rank2CashMultiplier        float                                not null,
    rank2RepMultiplier         float                                not null,
    rank3CashMultiplier        float                                not null,
    rank3RepMultiplier         float                                not null,
    rank4CashMultiplier        float                                not null,
    rank4RepMultiplier         float                                not null,
    rank5CashMultiplier        float                                not null,
    rank5RepMultiplier         float                                not null,
    rank6CashMultiplier        float                                not null,
    rank6RepMultiplier         float                                not null,
    rank7CashMultiplier        float                                not null,
    rank7RepMultiplier         float                                not null,
    rank8CashMultiplier        float                                not null,
    rank8RepMultiplier         float                                not null,
    topSpeedCashMultiplier     float                                not null,
    topSpeedRepMultiplier      float                                not null,
    carClassHash               int                                  not null,
    trackLength                float                                not null,
    rewardTable_rank1_id       bigint                               null,
    rewardTable_rank2_id       bigint                               null,
    rewardTable_rank3_id       bigint                               null,
    rewardTable_rank4_id       bigint                               null,
    rewardTable_rank5_id       bigint                               null,
    rewardTable_rank6_id       bigint                               null,
    rewardTable_rank7_id       bigint                               null,
    rewardTable_rank8_id       bigint                               null,
    isRotationEnabled          bit    default b'0'                  null,
    dnfTimerTime               int    default 60000                 null,
    lobbyCountdownTime         int    default 60000                 null,
    legitTime                  bigint default 0                     null,
    isDnfEnabled               bit    default b'1'                  null,
    isRaceAgainEnabled         bit    default b'1'                  null,
    constraint FK2xj5h4dvsmiy3fpyayhmgsm7y
        foreign key (rewardTable_rank6_id) references REWARD_TABLE (ID),
    constraint FK5r8fjqwem02m1b4oyc6yy1esq
        foreign key (rewardTable_rank3_id) references REWARD_TABLE (ID),
    constraint FK8g17bk7hkmabmnf88srvslxwm
        foreign key (rewardTable_rank4_id) references REWARD_TABLE (ID),
    constraint FKalw5jfl728ahfpvdwhegdty6w
        foreign key (rewardTable_rank5_id) references REWARD_TABLE (ID),
    constraint FKdwjw797e5pirj7rnvcr6d71pi
        foreign key (rewardTable_rank2_id) references REWARD_TABLE (ID),
    constraint FKfh6oit4kajywxhmxtid3yfukk
        foreign key (rewardTable_rank8_id) references REWARD_TABLE (ID),
    constraint FKfpntheixvd1mgifia39l2tf4b
        foreign key (rewardTable_rank7_id) references REWARD_TABLE (ID),
    constraint FKhky4tfvfpo91vixa09jkofjnc
        foreign key (rewardTable_rank1_id) references REWARD_TABLE (ID)
)
    charset = utf8;

create index EVENT_availability_index
    on EVENT (isEnabled asc, minLevel asc, maxLevel desc);

create index test_index
    on EVENT (ID, name);

create table if not exists LOBBY
(
    ID                 bigint auto_increment
        primary key,
    isPrivate          bit      null,
    lobbyDateTimeStart datetime null,
    personaId          bigint   null,
    EVENTID            int      null,
    startedTime        datetime null,
    constraint FKig5wp7wc5nuwille2w0hahp77
        foreign key (EVENTID) references EVENT (ID),
    constraint FK_LOBBY_EVENT
        foreign key (EVENTID) references EVENT (ID)
)
    charset = utf8;

create table if not exists EVENT_SESSION
(
    ID          bigint auto_increment
        primary key,
    EVENTID     int    null,
    ENDED       bigint null,
    STARTED     bigint null,
    LOBBYID     bigint null,
    NEXTLOBBYID bigint null,
    constraint FK_EVENTSESSION_LOBBY
        foreign key (LOBBYID) references LOBBY (ID),
    constraint FK_EVENTSESSION_NEXTLOBBY
        foreign key (NEXTLOBBYID) references LOBBY (ID),
    constraint FKj7j77j10kjso12h57nw1vdb1
        foreign key (EVENTID) references EVENT (ID),
    constraint FK_EVENTSESSION_EVENT
        foreign key (EVENTID) references EVENT (ID)
)
    charset = utf8;

create index LOBBY_startedTime_index
    on LOBBY (startedTime);

create table if not exists REWARD_TABLE_ITEM
(
    ID                   bigint auto_increment
        primary key,
    dropWeight           double null,
    script               text   not null,
    rewardTableEntity_ID bigint not null,
    constraint FK4qxgxc2phadj0ig58lsepsx9y
        foreign key (rewardTableEntity_ID) references REWARD_TABLE (ID)
)
    charset = latin1;

create table if not exists SERVER_INFO
(
    messageSrv                       varchar(1000) collate utf8_unicode_ci not null,
    country                          varchar(255) collate utf8_unicode_ci  null,
    adminList                        varchar(255) collate utf8_unicode_ci  null,
    bannerUrl                        varchar(255) collate utf8_unicode_ci  null,
    discordUrl                       varchar(255) collate utf8_unicode_ci  null,
    facebookUrl                      varchar(255) collate utf8_unicode_ci  null,
    homePageUrl                      varchar(255) collate utf8_unicode_ci  null,
    numberOfRegistered               int                                   null,
    ownerList                        varchar(255) collate utf8_unicode_ci  null,
    serverName                       varchar(255) collate utf8_unicode_ci  not null
        primary key,
    timezone                         int                                   null,
    activatedHolidaySceneryGroups    varchar(255) default ''               not null,
    disactivatedHolidaySceneryGroups varchar(255) default ''               not null,
    allowedCountries                 varchar(255)                          null,
    secondsToShutDown                int          default 7200             null
)
    charset = utf8;

create table if not exists TREASURE_HUNT_CONFIG
(
    ID              bigint auto_increment
        primary key,
    base_cash       float  null,
    base_rep        float  null,
    cash_multiplier float  null,
    rep_multiplier  float  null,
    streak          int    null,
    reward_table_id bigint null,
    constraint FK5tyssnup3qalmjfmr4q27jpxf
        foreign key (reward_table_id) references REWARD_TABLE (ID)
)
    charset = latin1;

create table if not exists USER
(
    ID                   bigint auto_increment
        primary key,
    EMAIL                varchar(255) collate utf8_unicode_ci null,
    PASSWORD             varchar(50) collate utf8_unicode_ci  null,
    premium              bit default b'0'                     not null,
    isAdmin              bit                                  null,
    HWID                 varchar(255)                         null,
    IP_ADDRESS           varchar(255)                         null,
    created              datetime                             null,
    lastLogin            datetime                             null,
    gameHardwareHash     varchar(255)                         null,
    isLocked             bit                                  null,
    selectedPersonaIndex int default 0                        null,
    constraint USER_email_index
        unique (EMAIL)
)
    charset = utf8;

create table if not exists INVITE_TICKET
(
    ID           bigint auto_increment
        primary key,
    DISCORD_NAME varchar(255) collate utf8_unicode_ci null,
    TICKET       varchar(255) collate utf8_unicode_ci null,
    USERID       bigint                               null,
    constraint FK3plac3qijtk0ciw43aoro0ogn
        foreign key (USERID) references USER (ID),
    constraint FK_INVITETICKET_USER
        foreign key (USERID) references USER (ID)
)
    charset = utf8;

create index INVITE_TICKET_TICKET_index
    on INVITE_TICKET (TICKET);

create table if not exists PERSONA
(
    ID                bigint auto_increment
        primary key,
    boost             double                               not null,
    cash              double                               not null,
    curCarIndex       int                                  not null,
    iconIndex         int                                  not null,
    level             int                                  not null,
    motto             varchar(255) collate utf8_unicode_ci null,
    name              varchar(255) collate utf8_unicode_ci null,
    percentToLevel    float                                not null,
    rating            double                               not null,
    rep               double                               not null,
    repAtCurrentLevel int                                  not null,
    score             int                                  not null,
    USERID            bigint                               null,
    created           datetime                             null,
    badges            varchar(2048)                        null,
    first_login       datetime                             null,
    last_login        datetime                             null,
    constraint PERSONA_name_index
        unique (name),
    constraint FKon9k3f1y35051t3y7x6ogd6k7
        foreign key (USERID) references USER (ID),
    constraint FK_PERSONA_USER
        foreign key (USERID) references USER (ID)
)
    charset = utf8;

create table if not exists BAN
(
    id           bigint auto_increment
        primary key,
    data         varchar(255) null,
    endsAt       datetime     null,
    reason       varchar(255) null,
    type         varchar(255) null,
    user_id      bigint       null,
    started      datetime     null,
    willEnd      bit          null,
    banned_by_id bigint       null,
    constraint FK_BANNED_BY
        foreign key (banned_by_id) references PERSONA (ID),
    constraint FK_BAN_USER
        foreign key (user_id) references USER (ID),
    constraint FKlwawdqh4uid0isnsc9uomdyfx
        foreign key (user_id) references USER (ID)
)
    collate = utf8mb4_unicode_ci;

create index BAN_endsAt_index
    on BAN (endsAt);

create index BAN_existence_index
    on BAN (user_id, willEnd, endsAt);

create table if not exists CARSLOT
(
    id            bigint auto_increment
        primary key,
    ownedCarTrans longtext collate utf8_unicode_ci null,
    PersonaId     bigint                           null,
    constraint FKc8km27r2ln6pxi1wfr5r8qjrd
        foreign key (PersonaId) references PERSONA (ID),
    constraint FK_CARSLOT_PERSONA
        foreign key (PersonaId) references PERSONA (ID)
)
    charset = utf8;

create table if not exists EVENT_DATA
(
    ID                                   bigint auto_increment
        primary key,
    alternateEventDurationInMilliseconds bigint           not null,
    bestLapDurationInMilliseconds        bigint           not null,
    bustedCount                          int              not null,
    carId                                bigint           not null,
    copsDeployed                         int              not null,
    copsDisabled                         int              not null,
    copsRammed                           int              not null,
    costToState                          int              not null,
    distanceToFinish                     float            not null,
    eventDurationInMilliseconds          bigint           not null,
    eventModeId                          int              not null,
    eventSessionId                       bigint           null,
    finishReason                         int              not null,
    fractionCompleted                    float            not null,
    hacksDetected                        bigint           not null,
    heat                                 float            not null,
    infractions                          int              not null,
    longestJumpDurationInMilliseconds    bigint           not null,
    numberOfCollisions                   int              not null,
    perfectStart                         int              not null,
    personaId                            bigint           null,
    `rank`                               int              not null,
    roadBlocksDodged                     int              not null,
    spikeStripsDodged                    int              not null,
    sumOfJumpsDurationInMilliseconds     bigint           not null,
    topSpeed                             float            not null,
    EVENTID                              int              null,
    isLegit                              bit default b'0' null,
    serverTimeInMilliseconds             bigint           null,
    serverTimeStarted                    bigint           null,
    serverTimeEnded                      bigint           null,
    carClassHash                         int              null,
    carRating                            int              null,
    constraint esi_personaId
        unique (eventSessionId, personaId),
    constraint EVENT_DATA_EVENT_SESSION_ID_fk
        foreign key (eventSessionId) references EVENT_SESSION (ID)
            on delete cascade,
    constraint EVENT_DATA_PERSONA_ID_fk
        foreign key (personaId) references PERSONA (ID)
            on delete cascade,
    constraint FKjqbikfyvivd8pf6ke5ke5rv1k
        foreign key (EVENTID) references EVENT (ID),
    constraint FK_EVENTDATA_EVENT
        foreign key (EVENTID) references EVENT (ID)
)
    charset = utf8;

create index EVENT_DATA_persona_id_index
    on EVENT_DATA (personaId);

create index car_id_index
    on EVENT_DATA (carId);

create index finishreason_index
    on EVENT_DATA (finishReason);

create table if not exists EVENT_POWERUP
(
    id             bigint auto_increment
        primary key,
    personaId      bigint not null,
    eventSessionId bigint not null,
    powerupHash    int    not null,
    constraint event_session_fk
        foreign key (eventSessionId) references EVENT_SESSION (ID)
            on delete cascade,
    constraint persona_fk
        foreign key (personaId) references PERSONA (ID)
            on delete cascade
);

create index hash_index
    on EVENT_POWERUP (powerupHash);

create index persona_index
    on EVENT_POWERUP (personaId);

create index session_index
    on EVENT_POWERUP (eventSessionId);

create index session_persona_index
    on EVENT_POWERUP (eventSessionId, personaId);

create table if not exists INVENTORY
(
    id                            bigint auto_increment
        primary key,
    performancePartsCapacity      int    null,
    performancePartsUsedSlotCount int    null,
    skillModPartsCapacity         int    null,
    skillModPartsUsedSlotCount    int    null,
    visualPartsCapacity           int    null,
    visualPartsUsedSlotCount      int    null,
    personaId                     bigint null,
    constraint FK_INVENTORY_PERSONA
        foreign key (personaId) references PERSONA (ID)
)
    charset = latin1;

create table if not exists INVENTORY_ITEM
(
    id                 bigint auto_increment
        primary key,
    expirationDate     datetime     null,
    remainingUseCount  int          null,
    resellPrice        int          null,
    status             varchar(255) not null,
    inventoryEntity_id bigint       not null,
    productId          varchar(255) not null,
    constraint FK1ii2plfjt9hme45210mfpr15e
        foreign key (productId) references PRODUCT (productId),
    constraint FKpt9o6wxhd3m4ufvl7dgifl9l3
        foreign key (inventoryEntity_id) references INVENTORY (id)
)
    charset = latin1;

create index INVENTORY_ITEM_expirationDate_index
    on INVENTORY_ITEM (expirationDate);

create table if not exists LOBBY_ENTRANT
(
    id        bigint auto_increment
        primary key,
    gridIndex int    not null,
    LOBBYID   bigint null,
    PERSONAID bigint null,
    constraint FKn2yjevc0kdgpj7juvn0udr2mn
        foreign key (LOBBYID) references LOBBY (ID),
    constraint FK_LOBBYENTRANT_LOBBY
        foreign key (LOBBYID) references LOBBY (ID),
    constraint FKqy9gynb01x729yacdubgmjier
        foreign key (PERSONAID) references PERSONA (ID),
    constraint FK_LOBBYENTRANT_PERSONA
        foreign key (PERSONAID) references PERSONA (ID)
)
    charset = utf8;

create table if not exists NEWS_ARTICLE
(
    id                    bigint auto_increment
        primary key,
    filters               varchar(255)  null,
    iconType              int           null,
    longHALId             varchar(255)  null,
    parameters            varchar(1000) null,
    shortHALId            varchar(255)  null,
    sticky                int           null,
    timestamp             bigint        null,
    type                  varchar(255)  null,
    persona_id            bigint        null,
    referenced_persona_id bigint        null,
    constraint FK41ahos5r10lc5v0o1ttd3tqt3
        foreign key (referenced_persona_id) references PERSONA (ID),
    constraint FKrgi3drtymu1h23a4jjgd092yg
        foreign key (persona_id) references PERSONA (ID)
)
    charset = latin1;

create table if not exists OWNEDCAR
(
    id             bigint auto_increment
        primary key,
    durability     int                                  not null,
    expirationDate datetime                             null,
    heat           float                                not null,
    ownershipType  varchar(255) collate utf8_unicode_ci null,
    carSlotId      bigint                               null,
    constraint FK_OWNEDCAR_CARSLOT
        foreign key (carSlotId) references CARSLOT (id),
    constraint FK9i0ql538ftgc26i0eri8keeqp
        foreign key (carSlotId) references CARSLOT (id)
)
    charset = utf8;

create table if not exists CUSTOMCAR
(
    id                 bigint auto_increment
        primary key,
    baseCar            int                                     not null,
    carClassHash       int                                     not null,
    isPreset           bit                                     not null,
    level              int                                     not null,
    name               varchar(255) collate utf8mb4_unicode_ci null,
    physicsProfileHash int                                     not null,
    rating             int                                     not null,
    resalePrice        float                                   not null,
    rideHeightDrop     float                                   not null,
    skillModSlotCount  int                                     not null,
    version            int                                     not null,
    ownedCarId         bigint                                  null,
    constraint FK_CUSTOMCAR_OWNEDCAR
        foreign key (ownedCarId) references OWNEDCAR (id),
    constraint FKjgdh4g5me6ljh5srjkn2t0i71
        foreign key (ownedCarId) references OWNEDCAR (id)
)
    charset = utf8;

create index bci_index
    on CUSTOMCAR (baseCar);

create index customcar_name_index
    on CUSTOMCAR (name);

create index pph_index
    on CUSTOMCAR (physicsProfileHash);

create index OWNEDCAR_expirationDate_index
    on OWNEDCAR (expirationDate);

create index OWNEDCAR_ownershipType_index
    on OWNEDCAR (ownershipType);

create table if not exists PAINT
(
    id          bigint auto_increment
        primary key,
    paintGroup  int    null,
    hue         int    not null,
    sat         int    not null,
    slot        int    not null,
    paintVar    int    null,
    customCarId bigint null,
    constraint FK_PAINT_CUSTOMCAR
        foreign key (customCarId) references CUSTOMCAR (id),
    constraint FKpxxc02fcm311w9odwx6j6wu6u
        foreign key (customCarId) references CUSTOMCAR (id)
)
    charset = utf8;

create table if not exists PERFORMANCEPART
(
    id                        bigint auto_increment
        primary key,
    performancePartAttribHash int    not null,
    customCarId               bigint null,
    constraint FK_PERFPART_CUSTOMCAR
        foreign key (customCarId) references CUSTOMCAR (id),
    constraint FKbehavbux872md9t4uedp5xw68
        foreign key (customCarId) references CUSTOMCAR (id)
)
    charset = utf8;

create table if not exists PERSONA_ACHIEVEMENT
(
    ID             bigint auto_increment
        primary key,
    can_progress   bit    null,
    current_value  bigint null,
    achievement_id bigint not null,
    persona_id     bigint not null,
    constraint FKi0362rxl6y75pcw9v1n7hmecu
        foreign key (achievement_id) references ACHIEVEMENT (ID),
    constraint FKmci5vxxoedblsncmosjfbtfxk
        foreign key (persona_id) references PERSONA (ID)
)
    charset = latin1;

create index persona_ach_index
    on PERSONA_ACHIEVEMENT (persona_id, achievement_id);

create table if not exists PERSONA_ACHIEVEMENT_RANK
(
    ID                     bigint auto_increment
        primary key,
    achieved_on            datetime                                                    null,
    state                  enum ('Locked', 'InProgress', 'Completed', 'RewardWaiting') null,
    achievement_rank_id    bigint                                                      not null,
    persona_achievement_id bigint                                                      not null,
    constraint FKbbg8f49c6fgkbwwhe25i2ely0
        foreign key (persona_achievement_id) references PERSONA_ACHIEVEMENT (ID),
    constraint FKc1w7mcx2mdb9bg30d0ed5efd9
        foreign key (achievement_rank_id) references ACHIEVEMENT_RANK (ID)
)
    charset = latin1;

create table if not exists PERSONA_BADGE
(
    ID                  bigint auto_increment
        primary key,
    slot                int    null,
    badge_definition_id bigint not null,
    persona_id          bigint not null,
    constraint FK4c2e5u8ymnlh4xc8hiem6uota
        foreign key (badge_definition_id) references BADGE_DEFINITION (ID),
    constraint FKah3mnx6lcx9mg3ant8n1uorii
        foreign key (persona_id) references PERSONA (ID)
)
    charset = latin1;

create table if not exists PROMO_CODE
(
    id        bigint auto_increment
        primary key,
    isUsed    bit          null,
    promoCode varchar(255) null,
    USERID    bigint       null,
    constraint FKf3q30eb9w1j5o89cumpi72gry
        foreign key (USERID) references USER (ID),
    constraint FK_PROMOCODE_USER
        foreign key (USERID) references USER (ID)
)
    charset = utf8;

create table if not exists SKILLMODPART
(
    id                     bigint auto_increment
        primary key,
    isFixed                bit    not null,
    skillModPartAttribHash int    not null,
    customCarId            bigint null,
    constraint FK_SKILLPART_CUSTOMCAR
        foreign key (customCarId) references CUSTOMCAR (id),
    constraint FK8hnhp2he4tg3rxtpyad7payt1
        foreign key (customCarId) references CUSTOMCAR (id)
)
    charset = utf8;

create table if not exists SOCIAL_RELATIONSHIP
(
    ID              int auto_increment
        primary key,
    remotePersonaId bigint null,
    status          bigint null,
    fromUserId      bigint null,
    userId          bigint null,
    constraint FKb9v7qy291m237rtenur72v61l
        foreign key (userId) references USER (ID),
    constraint FKih34hpy2rt97e269pop7ehcwm
        foreign key (fromUserId) references USER (ID)
)
    charset = latin1;

create table if not exists TOKEN_SESSION
(
    ID                varchar(255) collate utf8_unicode_ci not null,
    activeLobbyId     bigint                               null,
    activePersonaId   bigint                               null,
    expirationDate    datetime                             null,
    premium           bit default b'0'                     not null,
    relayCryptoTicket varchar(255) collate utf8_unicode_ci null,
    userId            bigint                               null,
    clientHostIp      varchar(255)                         null,
    webToken          varchar(255)                         null,
    eventSessionId    bigint                               null,
    constraint TOKEN_SESSION_ID_uindex
        unique (ID),
    constraint UK_9ranmagnxgrp70u76q860goeb
        unique (userId),
    constraint FKomwojh6l6a26jsu4jiqpjnuvn
        foreign key (userId) references USER (ID),
    constraint TOKEN_SESSION_ibfk_1
        foreign key (eventSessionId) references EVENT_SESSION (ID)
            on delete set null,
    constraint TOKEN_SESSION_userID__fk
        foreign key (userId) references USER (ID)
)
    charset = utf8;

create index TOKEN_SESSION_activePersonaId_index
    on TOKEN_SESSION (activePersonaId);

create index event_session_fk
    on TOKEN_SESSION (eventSessionId);

alter table TOKEN_SESSION
    add primary key (ID);

create table if not exists TREASURE_HUNT
(
    personaId      bigint not null
        primary key,
    coinsCollected int    null,
    isStreakBroken bit    null,
    numCoins       int    null,
    seed           int    null,
    streak         int    null,
    thDate         date   null,
    isCompleted    bit    not null,
    constraint TREASURE_HUNT_personaId_PERSONA_fk
        foreign key (personaId) references PERSONA (ID)
)
    charset = latin1;

create table if not exists VINYL
(
    id          bigint auto_increment
        primary key,
    hash        int    not null,
    hue1        int    not null,
    hue2        int    not null,
    hue3        int    not null,
    hue4        int    not null,
    layer       int    not null,
    mir         bit    not null,
    rot         int    not null,
    sat1        int    not null,
    sat2        int    not null,
    sat3        int    not null,
    sat4        int    not null,
    scalex      int    not null,
    scaley      int    not null,
    shear       int    not null,
    tranx       int    not null,
    trany       int    not null,
    var1        int    not null,
    var2        int    not null,
    var3        int    not null,
    var4        int    not null,
    customCarId bigint null,
    constraint FK_VINYL_CUSTOMCAR
        foreign key (customCarId) references CUSTOMCAR (id),
    constraint FK83pxksxc4fx2efkwnpqet3f3l
        foreign key (customCarId) references CUSTOMCAR (id)
)
    charset = utf8;

create table if not exists VINYLPRODUCT
(
    id               bigint auto_increment
        primary key,
    bundleItems      varchar(255) null,
    categoryId       varchar(255) null,
    categoryName     varchar(255) null,
    currency         varchar(255) null,
    description      varchar(255) null,
    durationMinute   int          not null,
    enabled          bit          not null,
    entitlementTag   varchar(255) null,
    hash             int          null,
    icon             varchar(255) null,
    level            int          not null,
    longDescription  varchar(255) null,
    minLevel         int          not null,
    premium          bit          not null,
    price            float        not null,
    priority         int          not null,
    productId        varchar(255) null,
    productTitle     varchar(255) null,
    productType      varchar(255) null,
    secondaryIcon    varchar(255) null,
    useCount         int          not null,
    visualStyle      varchar(255) null,
    webIcon          varchar(255) null,
    webLocation      varchar(255) null,
    parentCategoryId bigint       null,
    constraint FK_VINYLPRODUCT_CATEGORY
        foreign key (parentCategoryId) references CATEGORY (idcategory)
)
    charset = latin1;

create table if not exists VIRTUALITEM
(
    itemName         varchar(255) not null
        primary key,
    brand            varchar(255) null,
    hash             int          null,
    icon             varchar(255) null,
    longdescription  varchar(255) null,
    rarity           int          null,
    resellprice      int          null,
    shortdescription varchar(255) null,
    subType          varchar(255) null,
    tier             int          null,
    title            varchar(255) null,
    type             varchar(255) null,
    warnondelete     bit          null
)
    charset = latin1;

create table if not exists VISUALPART
(
    id          bigint auto_increment
        primary key,
    partHash    int    not null,
    slotHash    int    not null,
    customCarId bigint null,
    constraint FK_VISUALPART_CUSTOMCAR
        foreign key (customCarId) references CUSTOMCAR (id)
)
    charset = latin1;