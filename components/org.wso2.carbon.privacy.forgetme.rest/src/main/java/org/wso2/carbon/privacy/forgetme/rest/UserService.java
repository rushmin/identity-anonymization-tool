package org.wso2.carbon.privacy.forgetme.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.privacy.forgetme.rest.domain.User;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * API implementation for user store operations.
 */
@Path("/user")
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    @PUT
    @Path("/{tenantId}/{tenantDomain}/{userStoreDomain}/{username}")
    public void renameUser(@PathParam("tenantId") Integer tenantId,
                           @PathParam("tenantDomain") String tenantDomain,
                           @PathParam("userStoreDomain") String userStoreDomain,
                           @PathParam("username") String username,
                           User user) {

        try {

            new ForgetMeToolExecutor().execute(tenantId, tenantDomain, userStoreDomain, username, user);

        } catch (Exception e) {
            log.error("An error occurred while renaming the user.", e);
        }
    }

}
