INSERT
INTO
    SOAPBOX.SERVER_INFO
    (
        MESSAGESRV,
        COUNTRY,
        ADMINLIST,
        BANNERURL,
        DISCORDURL,
        FACEBOOKURL,
        HOMEPAGEURL,
        NUMBEROFREGISTERED,
        OWNERLIST,
        SERVERNAME,
        TIMEZONE,
        ACTIVATEDHOLIDAYSCENERYGROUPS,
        DISACTIVATEDHOLIDAYSCENERYGROUPS
    )
    VALUES
    (
        'Message server example',
        'FR (example)',
        'Admins pseudo here (To put several, separate the pseudo by ";")',
        'Your banner url (image)',
        'Your discord server url',
        'Your facebook page url',
        'Your website page url',
        0,
        'Owners pseudo here (To put several, separate the pseudo by ";")',
        'Name of server',
        0,
        'SCENERY_GROUP_CHRISTMAS;SCENERY_GROUP_NEWYEARS;SCENERY_GROUP_OKTOBERFEST;SCENERY_GROUP_HALLOWEEN'
        ,
        'SCENERY_GROUP_CHRISTMAS_DISABLE;SCENERY_GROUP_NEWYEARS_DISABLE;SCENERY_GROUP_HALLOWEEN_DISABLE;SCENERY_GROUP_OKTOBERFEST_DISABLE'
    );
