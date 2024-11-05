package com.core.coffee.util;



public class Constants {

    // Private constructor to prevent instantiation
    private Constants() {
        throw new IllegalStateException("Cannot instantiate a constant class.");
    }


    

    // For messages
    public static final String ERROR = "ERROR";
    public static final String WARN = "WARNING";
    public static final String IN = "IN";
    public static final String OUT = "OUT";
    public static final String METHOD_GET = "get";
    public static final String METHOD_LIST = "list";
    public static final String METHOD_CREATE = "create";
    public static final String METHOD_DELETE = "delete";
    public static final String METHOD_UPDATE = "update";

    public static final String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DATE_SHORT = "yyyy-MM-dd";
    public static final String KEY_TIMESTAMP= "timestamp";
    public static final String KEY_ERROR = "error";
    public static final String KEY_ERROR_CODE = "errorCode";
    public static final String INVALID_ID = "Invalid ID";

    public static final String GENERIC_ERROR = "An error occurred while processing the request";


    public static final String API_DESCRIPTION = "API for managing users";
    public static final String API_USERS = "Users";
    public static final String API_CREATE_USER="Create User";
    public static final String API_CREATE_USER_DESCRIPTION = "Creates a new user with the provided details";
    public static final String API_UPDATE_USER="Update User";
    public static final String API_UPDATE_USER_DESCRIPTION = "Updates an existing user with the provided details";
    public static final String API_DELETE_USER="Delete User";
    public static final String API_DELETE_USER_DESCRIPTION = "Deletes an existing user by its ID";
    public static final String API_GET_USER="Get User";
    public static final String API_GET_USER_DESCRIPTION = "Retrieves a user by its ID";
    public static final String API_GET_USERS="Get Users";
    public static final String API_GET_USERS_DESCRIPTION = "Retrieves all users";

    
    public static final String API_USER_NOT_FOUND="User not found";
    public static final String API_USER_ALREADY_EXISTS = "User already exists";

    public static final String API_INVALID_REQUEST="Invalid Request";
    public static final String API_RESOURCE_NOT_FOUND ="Resource not found";

    public static final String API_AUTH = "Authentication";
    public static final String API_AUTH_DESCRIPTION = "API for user authentication";
    public static final String API_LOGIN = "Login";
    public static final String API_LOGIN_DESCRIPTION = "Logs in a user with the provided credentials";
    public static final String API_REGISTER = "Register";
    public static final String API_REGISTER_DESCRIPTION = "Registers a new user with the provided details";
    public static final String API_REFRESH_TOKEN ="Refresh Token";
    public static final String API_REFRESH_TOKEN_DESCRIPTION = "Refreshes the token for the user";

    public static final String API_REGISTER_SUCCESS = "User registered successfully";
    public static final String API_LOGIN_SUCCESS = "User logged in successfully";
    public static final String API_REFRESH_TOKEN_SUCCESS = "Token refreshed successfully";

    public static final String API_TOKEN_INVALID = "Token invalid";
    public static final String API_TOKEN_REFRESH_INVALID= "Token refresh invalid";

    public static final String API_ACCESS_DENIED = "Access Denied"; 


    public static String API_INFO_SWAGGER ="Backend Core API";
    public static String API_DESCRIPTION_SWAGGER ="This is a Backend Core API";
    public static String API_VERSION_SWAGGER ="1.0";





    public static enum ERROR_CODE {

        BAD_REQUEST("400"),
        NOT_FOUND("404"),
        INTERNAL_SERVER_ERROR("500"),
        ACCESS_DENIED("403"),

        RESOURCE_NOT_FOUND("404"),
        VALIDATIONS_ERROR("001"),
        FORBIDDEN("403"),
        GENERIC_ERROR("002");
        

        private String value;
 
        private ERROR_CODE(final String value) {
           this.value = value;
        }
     
        public String getValue() {
           return this.value;
        }
    }

}
