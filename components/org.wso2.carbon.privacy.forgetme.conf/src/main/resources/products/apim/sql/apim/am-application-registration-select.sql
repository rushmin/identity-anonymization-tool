SELECT INPUTS, SUBSCRIBER_ID FROM AM_APPLICATION_REGISTRATION AM
WHERE (SELECT TENANT_ID FROM AM_SUBSCRIBER SUB WHERE SUB.SUBSCRIBER_ID = AM.SUBSCRIBER_ID) = `tenant_id`
