package org.wso2.carbon.privacy.forgetme.userstore.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.privacy.forgetme.userstore.exception.UserStoreModuleException;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.ldap.LDAPConnectionContext;
import org.wso2.carbon.user.core.ldap.LDAPConstants;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

/**
 * Performs user store operations against an LDAP user store.
 */
public class LDAPUserStoreHandler extends UserStoreHandler {

    private static final Log log = LogFactory.getLog(LDAPUserStoreHandler.class);

    @Override
    public String getName() {

        return "read-write-ldap-handler";
    }

    /**
     * Renames the user in the underlying LDAP user store.
     *
     * @param currentName The existing username.
     * @param newName     The new username.
     * @throws UserStoreModuleException
     */
    @Override
    public void renameUser(String currentName, String newName) throws UserStoreModuleException {

        try {

            String userSearchBase = getRealmConfiguration().getUserStoreProperty(LDAPConstants.USER_SEARCH_BASE);
            String userNameAttribute = getRealmConfiguration().getUserStoreProperty(LDAPConstants.USER_NAME_ATTRIBUTE);

            String currentDN = String.format("%s=%s,%s", userNameAttribute, currentName, userSearchBase);
            String newDN = String.format("%s=%s,%s", userNameAttribute, newName, userSearchBase);

            LDAPConnectionContext ldapConnectionContext = new LDAPConnectionContext(getRealmConfiguration());
            DirContext context = ldapConnectionContext.getContext();

            if (log.isDebugEnabled()) {
                log.debug(String.format("Renaming the user. Search base : '%s', Username attribute : '%s'",
                        userSearchBase, userNameAttribute));
            }
            context.rename(currentDN, newDN);

        } catch (UserStoreException | NamingException e) {
            throw new UserStoreModuleException("An error occurred while renaming a user in an LDAP user store.", e);
        }

    }
}
