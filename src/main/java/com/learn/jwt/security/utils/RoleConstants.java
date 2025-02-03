package com.learn.jwt.security.utils;

public class RoleConstants {

    public static final String ROLE_USER = "USER";

    public static final String ROLE_ADMIN = "ADMIN";

    public static final String ROLE_SUPER_ADMIN = "SUPER_ADMIN";

    public static String getRoleUser() {
        return ROLE_USER;
    }

    public static String getRoleAdmin() {
        return ROLE_ADMIN;
    }

    public static String getRoleSuperAdmin() {
        return ROLE_SUPER_ADMIN;
    }


}
