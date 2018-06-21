package org.wso2.carbon.privacy.forgetme.rest.domain;

/**
 * The data object which captured a user in an API call.
 */
public class User {

    private String username;
    private int tenantId;
    private String tenantDomain;
    private String userStoreDomain;

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public int getTenantId() {

        return tenantId;
    }

    public void setTenantId(int tenantId) {

        this.tenantId = tenantId;
    }

    public String getTenantDomain() {

        return tenantDomain;
    }

    public void setTenantDomain(String tenantDomain) {

        this.tenantDomain = tenantDomain;
    }

    public String getUserStoreDomain() {

        return userStoreDomain;
    }

    public void setUserStoreDomain(String userStoreDomain) {

        this.userStoreDomain = userStoreDomain;
    }
}
