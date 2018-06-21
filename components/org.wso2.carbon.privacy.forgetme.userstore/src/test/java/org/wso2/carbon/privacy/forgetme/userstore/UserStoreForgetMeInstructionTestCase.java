package org.wso2.carbon.privacy.forgetme.userstore;

import org.testng.annotations.Test;
import org.wso2.carbon.privacy.forgetme.api.user.UserIdentifier;

import java.util.UUID;

public class UserStoreForgetMeInstructionTestCase {

    @Test
    public void testExecute()throws Exception{

        UserIdentifier userIdentifier = new UserIdentifier();
        userIdentifier.setUsername("admin");
        userIdentifier.setUserStoreDomain("PRIMARY");
        userIdentifier.setTenantDomain("carbon.super");
        userIdentifier.setPseudonym(UUID.randomUUID().toString());


    }

}
