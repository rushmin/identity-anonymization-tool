/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.privacy.forgetme.userstore.instructions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.privacy.forgetme.api.runtime.ProcessorConfig;
import org.wso2.carbon.privacy.forgetme.userstore.exception.UserStoreModuleException;
import org.wso2.carbon.privacy.forgetme.userstore.handler.UserStoreHandler;
import org.wso2.carbon.privacy.forgetme.userstore.util.SecondaryUserStoreReader;
import org.wso2.carbon.user.api.RealmConfiguration;
import org.wso2.carbon.user.core.UserStoreException;
import org.wso2.carbon.user.core.config.RealmConfigXMLProcessor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Module config for user stores.
 */
public class UserStoreProcessorConfig implements ProcessorConfig {

    private static final Log log = LogFactory.getLog(UserStoreProcessorConfig.class);

    private static final int SUPER_TENANT_ID = -1234;
    public static final String PRIMARY_USER_STORE_NAME = "PRIMARY";

    private final Path carbonHome;
    private final Map<String, String> properties;
    private ServiceLoader<UserStoreHandler> userStoreHandlerServiceLoader;
    private Map<String, UserStoreHandler> userStoreHandlerMappings;

    public UserStoreProcessorConfig(Path carbonHome, Map<String, String> properties) {

        this.carbonHome = carbonHome;
        this.properties = properties;

        // Populate user store handler implementation mappings.
        this.userStoreHandlerMappings = new HashMap<>();
        this.userStoreHandlerServiceLoader = ServiceLoader.load(UserStoreHandler.class);
        this.userStoreHandlerServiceLoader.forEach(r -> userStoreHandlerMappings.put(r.getName(), r));
    }

    /**
     * Returns the {@link UserStoreHandler} for the given user store domain in the given tenant.
     *
     * @param tenantId        The tenant id of the user store domain.
     * @param userStoreDomain Name of the user store domain.
     * @return The applicable {@link UserStoreHandler}
     */
    public UserStoreHandler getUserStoreHandler(int tenantId, String userStoreDomain) throws UserStoreModuleException {

        if (log.isDebugEnabled()) {
            log.debug(String.format("Looking for the user store handler for the tenant id : '%d', " +
                    "user store domain : '%s'", tenantId, userStoreDomain));
        }

        RealmConfiguration realmConfiguration = null;
        try {
            realmConfiguration = getRealmConfig(tenantId, userStoreDomain);

            String userStoreManagerClassName = realmConfiguration.getUserStoreClass();

            if (log.isDebugEnabled()) {
                log.debug(String.format("User store manager class name : '%s'", userStoreManagerClassName));
            }

            // Find the applicable handler implementation using the configuration.
            UserStoreHandler applicableHandler = userStoreHandlerMappings
                    .get(this.properties.get(userStoreManagerClassName));

            if (applicableHandler != null) {

                if (log.isDebugEnabled()) {
                    log.debug(String.format("Found an applicable handler : '%s'",
                            applicableHandler.getClass().getName()));
                }

                // Set the context for the handler implementation.
                applicableHandler.setRealmConfiguration(realmConfiguration);
                applicableHandler.setTenantId(tenantId);
                applicableHandler.setCarbonHome(carbonHome);
            }

            return applicableHandler;

        } catch (IOException | UserStoreException e) {
            throw new UserStoreModuleException(
                    String.format("An error occurred while retrieving the applicable user store handler for " +
                            "tenant id : '%d', user store domain : '%s'", tenantId, userStoreDomain), e);
        }
    }

    /**
     * Returns the realm configuration for the given user store domain int the given tenant.
     *
     * @param tenantId        The tenant id of the user store domain.
     * @param userStoreDomain Name of the user store domain.
     * @return The realm configuration.
     */
    private RealmConfiguration getRealmConfig(int tenantId, String userStoreDomain)
            throws IOException, UserStoreException, UserStoreModuleException {

        if (log.isDebugEnabled()) {
            log.debug(String.format("Looking for the realm config for the tenant id : '%d', " +
                    "user store domain : '%s'", tenantId, userStoreDomain));
        }

        RealmConfiguration realmConfiguration = getPrimaryRealmConfiguration();

        if (log.isDebugEnabled()) {
            log.debug("Retrieved the primary realm configuration.");
        }

        Path userStoreConfigFilePath = null;
        if (SUPER_TENANT_ID == tenantId) {

            if (PRIMARY_USER_STORE_NAME.equals(userStoreDomain)) {
                return realmConfiguration;
            } else {
                userStoreConfigFilePath = carbonHome.resolve("repository/deployment/server/userstores/" +
                        userStoreDomain.toUpperCase() + ".xml");
            }
        } else {
            if (PRIMARY_USER_STORE_NAME.equals(userStoreDomain)) {
                return realmConfiguration;
            }else {
                userStoreConfigFilePath = carbonHome.resolve("repository/tenants/" + tenantId + "/userstores/" +
                        userStoreDomain.toUpperCase() + ".xml");
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("Secondary user store configuration file path : " + userStoreConfigFilePath.toString());
        }

        SecondaryUserStoreReader secondaryUserStoreReader = new SecondaryUserStoreReader();
        realmConfiguration = secondaryUserStoreReader.read(userStoreConfigFilePath, realmConfiguration);

        if (log.isDebugEnabled()) {
            log.debug("Built the realm configuration for the secondary user store.");
        }

        return realmConfiguration;
    }

    private RealmConfiguration getPrimaryRealmConfiguration() throws IOException, UserStoreException {

        Path primaryUserStoreConfigFilePath = carbonHome.resolve("repository/conf/user-mgt.xml");

        try (InputStream inStream = new FileInputStream(primaryUserStoreConfigFilePath.toFile())) {

            RealmConfigXMLProcessor builder = new RealmConfigXMLProcessor();
            return builder.buildRealmConfiguration(inStream);
        }
    }
}
