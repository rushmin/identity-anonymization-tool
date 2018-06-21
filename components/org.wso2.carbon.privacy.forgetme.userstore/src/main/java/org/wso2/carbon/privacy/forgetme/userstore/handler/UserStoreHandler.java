package org.wso2.carbon.privacy.forgetme.userstore.handler;

import org.wso2.carbon.privacy.forgetme.userstore.exception.UserStoreModuleException;
import org.wso2.carbon.user.api.RealmConfiguration;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Service contract for a user store handler.
 * <p>
 * The handler implementations know how to do user store operations such as renaming users.
 */
public abstract class UserStoreHandler {

    private RealmConfiguration realmConfiguration;
    private int tenantId;
    private Path carbonHome;
    private Map<String, String> properties;

    public UserStoreHandler(){
        properties = new HashMap<>();
    }

    /**
     * Returns the name of the handler.
     *
     * @return
     */
    public abstract String getName();

    /**
     * Renames the given username.
     *
     * @param currentName The existing username.
     * @param newName     The new username.
     */
    public abstract void renameUser(String currentName, String newName) throws UserStoreModuleException;

    public RealmConfiguration getRealmConfiguration() {

        return realmConfiguration;
    }

    public void setRealmConfiguration(RealmConfiguration realmConfiguration) {

        this.realmConfiguration = realmConfiguration;
    }

    public int getTenantId() {

        return tenantId;
    }

    public void setTenantId(int tenantId) {

        this.tenantId = tenantId;
    }

    public Path getCarbonHome() {

        return carbonHome;
    }

    public void setCarbonHome(Path carbonHome) {

        this.carbonHome = carbonHome;
    }

    public void addProperty(String name, String value){
        properties.put(name, value);
    }

    public String getProperty(String name){
        return properties.get(name);
    }
}
