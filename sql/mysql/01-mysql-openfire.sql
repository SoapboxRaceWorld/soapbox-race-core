CREATE DATABASE
IF NOT EXISTS `OPENFIRE` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE UTF8_UNICODE_CI;
DROP USER IF EXISTS 'openfire'@'localhost';
CREATE USER 'openfire'@'localhost' IDENTIFIED BY 'openfire';
GRANT ALL ON OPENFIRE.* TO 'openfire'@'localhost' WITH GRANT OPTION;
CREATE TABLE
    OPENFIRE.ofExtComponentConf
    (
        subdomain VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        wildcard TINYINT NOT NULL,
        secret VARCHAR(255) COLLATE utf8_unicode_ci,
        permission VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (subdomain)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofGroup
    (
        groupName VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
        description VARCHAR(255) COLLATE utf8_unicode_ci,
        PRIMARY KEY (groupName)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofGroupProp
    (
        groupName VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        propValue text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (groupName, name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofGroupUser
    (
        groupName VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
        username VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        administrator TINYINT NOT NULL,
        PRIMARY KEY (groupName, username, administrator)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofID
    (
        idType INT NOT NULL,
        id bigint NOT NULL,
        PRIMARY KEY (idType)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofID
    (
        idType,
        id
    )
    VALUES
    (
        18,
        1
    );
INSERT
INTO
    OPENFIRE.ofID
    (
        idType,
        id
    )
    VALUES
    (
        19,
        6
    );
INSERT
INTO
    OPENFIRE.ofID
    (
        idType,
        id
    )
    VALUES
    (
        23,
        1
    );
INSERT
INTO
    OPENFIRE.ofID
    (
        idType,
        id
    )
    VALUES
    (
        25,
        9
    );
INSERT
INTO
    OPENFIRE.ofID
    (
        idType,
        id
    )
    VALUES
    (
        26,
        2
    );
CREATE TABLE
    OPENFIRE.ofMucAffiliation
    (
        roomID bigint NOT NULL,
        jid text COLLATE utf8_unicode_ci NOT NULL,
        affiliation TINYINT NOT NULL,
        PRIMARY KEY (roomID, jid(70))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofMucConversationLog
    (
        roomID bigint NOT NULL,
        sender text COLLATE utf8_unicode_ci NOT NULL,
        nickname VARCHAR(255) COLLATE utf8_unicode_ci,
        logTime CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        subject VARCHAR(255) COLLATE utf8_unicode_ci,
        body text COLLATE utf8_unicode_ci,
        INDEX ofMucConversationLog_time_idx (logTime)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofMucMember
    (
        roomID bigint NOT NULL,
        jid text COLLATE utf8_unicode_ci NOT NULL,
        nickname VARCHAR(255) COLLATE utf8_unicode_ci,
        firstName VARCHAR(100) COLLATE utf8_unicode_ci,
        lastName VARCHAR(100) COLLATE utf8_unicode_ci,
        url VARCHAR(100) COLLATE utf8_unicode_ci,
        email VARCHAR(100) COLLATE utf8_unicode_ci,
        faqentry VARCHAR(100) COLLATE utf8_unicode_ci,
        PRIMARY KEY (roomID, jid(70))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofMucRoom
    (
        serviceID bigint NOT NULL,
        roomID bigint NOT NULL,
        creationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        modificationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        name VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
        naturalName VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        description VARCHAR(255) COLLATE utf8_unicode_ci,
        lockedDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        emptyDate CHAR(15) COLLATE utf8_unicode_ci,
        canChangeSubject TINYINT NOT NULL,
        maxUsers INT NOT NULL,
        publicRoom TINYINT NOT NULL,
        moderated TINYINT NOT NULL,
        membersOnly TINYINT NOT NULL,
        canInvite TINYINT NOT NULL,
        roomPassword VARCHAR(50) COLLATE utf8_unicode_ci,
        canDiscoverJID TINYINT NOT NULL,
        logEnabled TINYINT NOT NULL,
        subject VARCHAR(100) COLLATE utf8_unicode_ci,
        rolesToBroadcast TINYINT NOT NULL,
        useReservedNick TINYINT NOT NULL,
        canChangeNick TINYINT NOT NULL,
        canRegister TINYINT NOT NULL,
        PRIMARY KEY (serviceID, name),
        INDEX ofMucRoom_roomid_idx (roomID),
        INDEX ofMucRoom_serviceid_idx (serviceID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofMucRoomProp
    (
        roomID bigint NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        propValue text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (roomID, name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofMucService
    (
        serviceID bigint NOT NULL,
        subdomain VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        description VARCHAR(255) COLLATE utf8_unicode_ci,
        isHidden TINYINT NOT NULL,
        PRIMARY KEY (subdomain),
        INDEX ofMucService_serviceid_idx (serviceID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofMucService
    (
        serviceID,
        subdomain,
        description,
        isHidden
    )
    VALUES
    (
        1,
        'conference',
        NULL,
        0
    );
CREATE TABLE
    OPENFIRE.ofMucServiceProp
    (
        serviceID bigint NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        propValue text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (serviceID, name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.canAnyoneDiscoverJID',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.canChangeNickname',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.canOccupantsChangeSubject',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.canOccupantsInvite',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.logEnabled',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.loginRestrictedToNickname',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.maxUsers',
        '10'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.membersOnly',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.moderated',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.persistent',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.publicRoom',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofMucServiceProp
    (
        serviceID,
        name,
        propValue
    )
    VALUES
    (
        1,
        'room.registrationEnabled',
        'true'
    );
CREATE TABLE
    OPENFIRE.ofOffline
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        messageID bigint NOT NULL,
        creationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        messageSize INT NOT NULL,
        stanza text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username, messageID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofOffline
    (
        username,
        messageID,
        creationDate,
        messageSize,
        stanza
    )
    VALUES
    (
        'admin',
        1,
        '001500847301847',
        121,
        '<message from="127.0.0.1" to="admin@127.0.0.1"><body>A server or plugin update was found: Openfire 4.1.5</body></message>'
    );
CREATE TABLE
    OPENFIRE.ofPresence
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        offlinePresence text COLLATE utf8_unicode_ci,
        offlineDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofPrivacyList
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        isDefault TINYINT NOT NULL,
        list text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username, name),
        INDEX ofPrivacyList_default_idx (username, isDefault)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofPrivate
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        namespace VARCHAR(200) COLLATE utf8_unicode_ci NOT NULL,
        privateData text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username, name, namespace(100))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofProperty
    (
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        propValue text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'adminConsole.port',
        '9090'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'adminConsole.securePort',
        '9091'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'connectionProvider.className',
        'org.jivesoftware.database.DefaultConnectionProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.connectionTimeout',
        '1.0'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.driver',
        'com.mysql.jdbc.Driver'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.maxConnections',
        '25'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.minConnections',
        '5'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.password',
        '5d21a3b0ed3d0d3747fd8b094f0f56cd4c7740b4dfbfaf0eb721067f038aa1a5'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.serverURL',
        'jdbc:mysql://localhost:3306/OPENFIRE?rewriteBatchedStatements=true'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.testAfterUse',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.testBeforeUse',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.testSQL',
        'select 1'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'database.defaultProvider.username',
        'e3ce871f175cac52fe9f97e4e948edf1ad4bfac1bbdf5a4c7afb3a553ece8577'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'locale',
        'en'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'passwordKey',
        'HO60KgOLdRqQE42'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'plugin.restapi.allowedIPs',
        ''
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'plugin.restapi.enabled',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'plugin.restapi.httpAuth',
        'secret'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'plugin.restapi.secret',
        '31CbMaN4mlk5C0xN'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.admin.className',
        'org.jivesoftware.openfire.admin.DefaultAdminProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.auth.className',
        'org.jivesoftware.openfire.auth.DefaultAuthProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.group.className',
        'org.jivesoftware.openfire.group.DefaultGroupProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.lockout.className',
        'org.jivesoftware.openfire.lockout.DefaultLockOutProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.securityAudit.className',
        'org.jivesoftware.openfire.security.DefaultSecurityAuditProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.user.className',
        'org.jivesoftware.openfire.user.DefaultUserProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'provider.vcard.className',
        'org.jivesoftware.openfire.vcard.DefaultVCardProvider'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'register.inband',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'register.password',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'sasl.scram-sha-1.iteration-count',
        '4096'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'setup',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'stream.management.active',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'stream.management.requestFrequency',
        '5'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'update.lastCheck',
        '1500847302623'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.auth.anonymous',
        'false'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.client.compression.policy',
        'disabled'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.client.idle',
        '240000'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.client.idle.ping',
        'true'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.domain',
        '127.0.0.1'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.session.conflict-limit',
        '0'
    );
INSERT
INTO
    OPENFIRE.ofProperty
    (
        name,
        propValue
    )
    VALUES
    (
        'xmpp.socket.ssl.active',
        'false'
    );
CREATE TABLE
    OPENFIRE.ofPubsubAffiliation
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        nodeID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        jid VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        affiliation VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (serviceID, nodeID, jid(70))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofPubsubAffiliation
    (
        serviceID,
        nodeID,
        jid,
        affiliation
    )
    VALUES
    (
        'pubsub',
        '',
        '127.0.0.1',
        'owner'
    );
CREATE TABLE
    OPENFIRE.ofPubsubDefaultConf
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        leaf TINYINT NOT NULL,
        deliverPayloads TINYINT NOT NULL,
        maxPayloadSize INT NOT NULL,
        persistItems TINYINT NOT NULL,
        maxItems INT NOT NULL,
        notifyConfigChanges TINYINT NOT NULL,
        notifyDelete TINYINT NOT NULL,
        notifyRetract TINYINT NOT NULL,
        presenceBased TINYINT NOT NULL,
        sendItemSubscribe TINYINT NOT NULL,
        publisherModel VARCHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        subscriptionEnabled TINYINT NOT NULL,
        accessModel VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
        language VARCHAR(255) COLLATE utf8_unicode_ci,
        replyPolicy VARCHAR(15) COLLATE utf8_unicode_ci,
        associationPolicy VARCHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        maxLeafNodes INT NOT NULL,
        PRIMARY KEY (serviceID, leaf)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofPubsubDefaultConf
    (
        serviceID,
        leaf,
        deliverPayloads,
        maxPayloadSize,
        persistItems,
        maxItems,
        notifyConfigChanges,
        notifyDelete,
        notifyRetract,
        presenceBased,
        sendItemSubscribe,
        publisherModel,
        subscriptionEnabled,
        accessModel,
        language,
        replyPolicy,
        associationPolicy,
        maxLeafNodes
    )
    VALUES
    (
        'pubsub',
        0,
        0,
        0,
        0,
        0,
        1,
        1,
        1,
        0,
        0,
        'publishers',
        1,
        'open',
        'English',
        NULL,
        'all',
        -1
    );
INSERT
INTO
    OPENFIRE.ofPubsubDefaultConf
    (
        serviceID,
        leaf,
        deliverPayloads,
        maxPayloadSize,
        persistItems,
        maxItems,
        notifyConfigChanges,
        notifyDelete,
        notifyRetract,
        presenceBased,
        sendItemSubscribe,
        publisherModel,
        subscriptionEnabled,
        accessModel,
        language,
        replyPolicy,
        associationPolicy,
        maxLeafNodes
    )
    VALUES
    (
        'pubsub',
        1,
        1,
        5120,
        0,
        -1,
        1,
        1,
        1,
        0,
        1,
        'publishers',
        1,
        'open',
        'English',
        NULL,
        'all',
        -1
    );
CREATE TABLE
    OPENFIRE.ofPubsubItem
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        nodeID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        id VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        jid VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        creationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        payload mediumtext COLLATE utf8_unicode_ci,
        PRIMARY KEY (serviceID, nodeID, id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofPubsubNode
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        nodeID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        leaf TINYINT NOT NULL,
        creationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        modificationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        parent VARCHAR(100) COLLATE utf8_unicode_ci,
        deliverPayloads TINYINT NOT NULL,
        maxPayloadSize INT,
        persistItems TINYINT,
        maxItems INT,
        notifyConfigChanges TINYINT NOT NULL,
        notifyDelete TINYINT NOT NULL,
        notifyRetract TINYINT NOT NULL,
        presenceBased TINYINT NOT NULL,
        sendItemSubscribe TINYINT NOT NULL,
        publisherModel VARCHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        subscriptionEnabled TINYINT NOT NULL,
        configSubscription TINYINT NOT NULL,
        accessModel VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
        payloadType VARCHAR(100) COLLATE utf8_unicode_ci,
        bodyXSLT VARCHAR(100) COLLATE utf8_unicode_ci,
        dataformXSLT VARCHAR(100) COLLATE utf8_unicode_ci,
        creator VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        description VARCHAR(255) COLLATE utf8_unicode_ci,
        language VARCHAR(255) COLLATE utf8_unicode_ci,
        name VARCHAR(50) COLLATE utf8_unicode_ci,
        replyPolicy VARCHAR(15) COLLATE utf8_unicode_ci,
        associationPolicy VARCHAR(15) COLLATE utf8_unicode_ci,
        maxLeafNodes INT,
        PRIMARY KEY (serviceID, nodeID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofPubsubNode
    (
        serviceID,
        nodeID,
        leaf,
        creationDate,
        modificationDate,
        parent,
        deliverPayloads,
        maxPayloadSize,
        persistItems,
        maxItems,
        notifyConfigChanges,
        notifyDelete,
        notifyRetract,
        presenceBased,
        sendItemSubscribe,
        publisherModel,
        subscriptionEnabled,
        configSubscription,
        accessModel,
        payloadType,
        bodyXSLT,
        dataformXSLT,
        creator,
        description,
        language,
        name,
        replyPolicy,
        associationPolicy,
        maxLeafNodes
    )
    VALUES
    (
        'pubsub',
        '',
        0,
        '001500847265871',
        '001500847265871',
        NULL,
        0,
        0,
        0,
        0,
        1,
        1,
        1,
        0,
        0,
        'publishers',
        1,
        0,
        'open',
        '',
        '',
        '',
        '127.0.0.1',
        '',
        'English',
        '',
        NULL,
        'all',
        -1
    );
CREATE TABLE
    OPENFIRE.ofPubsubNodeGroups
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        nodeID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        rosterGroup VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        INDEX ofPubsubNodeGroups_idx (serviceID, nodeID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofPubsubNodeJIDs
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        nodeID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        jid VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        associationType VARCHAR(20) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (serviceID, nodeID, jid(70))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofPubsubSubscription
    (
        serviceID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        nodeID VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        id VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        jid VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        owner VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        state VARCHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        deliver TINYINT NOT NULL,
        digest TINYINT NOT NULL,
        digest_frequency INT NOT NULL,
        expire CHAR(15) COLLATE utf8_unicode_ci,
        includeBody TINYINT NOT NULL,
        showValues VARCHAR(30) COLLATE utf8_unicode_ci,
        subscriptionType VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
        subscriptionDepth TINYINT NOT NULL,
        keyword VARCHAR(200) COLLATE utf8_unicode_ci,
        PRIMARY KEY (serviceID, nodeID, id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofRemoteServerConf
    (
        xmppDomain VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        remotePort INT,
        permission VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (xmppDomain)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofRoster
    (
        rosterID bigint NOT NULL,
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        jid VARCHAR(1024) COLLATE utf8_unicode_ci NOT NULL,
        sub TINYINT NOT NULL,
        ask TINYINT NOT NULL,
        recv TINYINT NOT NULL,
        nick VARCHAR(255) COLLATE utf8_unicode_ci,
        PRIMARY KEY (rosterID),
        INDEX ofRoster_unameid_idx (username),
        INDEX ofRoster_jid_idx (jid(255))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofRosterGroups
    (
        rosterID bigint NOT NULL,
        rank TINYINT NOT NULL,
        groupName VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (rosterID, rank),
        INDEX ofRosterGroup_rosterid_idx (rosterID)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofSASLAuthorized
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        principal text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username, principal(200))
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofSecurityAuditLog
    (
        msgID bigint NOT NULL,
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        entryStamp bigint NOT NULL,
        summary VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        node VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,
        details text COLLATE utf8_unicode_ci,
        PRIMARY KEY (msgID),
        INDEX ofSecurityAuditLog_tstamp_idx (entryStamp),
        INDEX ofSecurityAuditLog_uname_idx (username)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        1,
        'admin',
        1500847465072,
        'set compression policy',
        'ubuntu',
        'c2s compression = false
s2s compression = false'
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        2,
        'admin',
        1500847496178,
        'edited registration settings',
        'ubuntu',
        'inband enabled = false
can change password = false
anon login = false
allowed ips = 
blocked ips = '
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        3,
        'admin',
        1500847519612,
        'Updated connection settings for SOCKET_C2S',
        'ubuntu',
        'plain: enabled=true, port=5222
legacy: enabled=false, port=5223
'
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        4,
        'admin',
        1500847519637,
        'set server property xmpp.client.idle',
        'ubuntu',
        'xmpp.client.idle = 240000'
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        5,
        'admin',
        1500847519660,
        'set server property xmpp.client.idle.ping',
        'ubuntu',
        'xmpp.client.idle.ping = true'
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        6,
        'admin',
        1500847523192,
        'Updated connection settings for SOCKET_C2S',
        'ubuntu',
        'plain: enabled=true, port=5222
legacy: enabled=false, port=5223
'
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        7,
        'admin',
        1500847523205,
        'set server property xmpp.client.idle',
        'ubuntu',
        'xmpp.client.idle = 240000'
    );
INSERT
INTO
    OPENFIRE.ofSecurityAuditLog
    (
        msgID,
        username,
        entryStamp,
        summary,
        node,
        details
    )
    VALUES
    (
        8,
        'admin',
        1500847523217,
        'set server property xmpp.client.idle.ping',
        'ubuntu',
        'xmpp.client.idle.ping = true'
    );
CREATE TABLE
    OPENFIRE.ofUser
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        storedKey VARCHAR(32) COLLATE utf8_unicode_ci,
        serverKey VARCHAR(32) COLLATE utf8_unicode_ci,
        salt VARCHAR(32) COLLATE utf8_unicode_ci,
        iterations INT,
        plainPassword VARCHAR(32) COLLATE utf8_unicode_ci,
        encryptedPassword VARCHAR(255) COLLATE utf8_unicode_ci,
        name VARCHAR(100) COLLATE utf8_unicode_ci,
        email VARCHAR(100) COLLATE utf8_unicode_ci,
        creationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        modificationDate CHAR(15) COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username),
        INDEX ofUser_cDate_idx (creationDate)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofUser
    (
        username,
        storedKey,
        serverKey,
        salt,
        iterations,
        plainPassword,
        encryptedPassword,
        name,
        email,
        creationDate,
        modificationDate
    )
    VALUES
    (
        'admin',
        'MYxWXwdXZ3+yh/pfagG59kalN4Q=',
        'JE4PuyaBS/rSAwUiVHwpAho/fqc=',
        'Jr8Oyb8tdWfIP7+P7SruO0haygNB2svL',
        4096,
        NULL,
        'cc9b8da504c0ccf1c976957c844f963ed3971939f0f29ee1',
        'Administrator',
        'admin@example.com',
        '001500847262578',
        '0'
    );
CREATE TABLE
    OPENFIRE.ofUserFlag
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        startTime CHAR(15) COLLATE utf8_unicode_ci,
        endTime CHAR(15) COLLATE utf8_unicode_ci,
        PRIMARY KEY (username, name),
        INDEX ofUserFlag_sTime_idx (startTime),
        INDEX ofUserFlag_eTime_idx (endTime)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofUserProp
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        name VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
        propValue text COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username, name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofVCard
    (
        username VARCHAR(64) COLLATE utf8_unicode_ci NOT NULL,
        vcard mediumtext COLLATE utf8_unicode_ci NOT NULL,
        PRIMARY KEY (username)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE
    OPENFIRE.ofVersion
    (
        name VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
        version INT NOT NULL,
        PRIMARY KEY (name)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT
INTO
    OPENFIRE.ofVersion
    (
        name,
        version
    )
    VALUES
    (
        'openfire',
        22
    );
