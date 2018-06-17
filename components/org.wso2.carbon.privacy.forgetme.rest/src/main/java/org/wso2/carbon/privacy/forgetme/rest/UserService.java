package org.wso2.carbon.privacy.forgetme.rest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.privacy.forgetme.ForgetMeTool;
import org.wso2.carbon.privacy.forgetme.rest.domain.User;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * API implementation for user store operations.
 *
 */
@Path("/user")
public class UserService {

    private static final Log log = LogFactory.getLog(UserService.class);

    private final String configDirectoryPath;

    public UserService(String configDirectoryPath) {
        this.configDirectoryPath = configDirectoryPath;
    }

    @PUT
    @Path("/{tenantId}/{tenantDomain}/{userStoreDomain}/{username}")
    public void renameUser(@PathParam("tenantId") Integer tenantId,
                           @PathParam("tenantDomain") String tenantDomain,
                           @PathParam("userStoreDomain") String userStoreDomain,
                           @PathParam("username") String username,
                           User user) {

        List<String> args = new ArrayList<>();

        args.add("-d");
        args.add(configDirectoryPath);

        args.add("-U");
        args.add(username);

        args.add("-pu");
        args.add(user.getUsername());

        args.add("-TID");
        args.add(String.valueOf(tenantId));

        args.add("-T");
        args.add(tenantDomain);

        args.add("-D");
        args.add(userStoreDomain);

        try {
            ForgetMeTool.main(args.toArray(new String[0]));
        } catch (Exception e) {
            log.error("An error occurred while renaming the user.", e);
        }
    }

}
