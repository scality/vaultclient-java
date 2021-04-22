package com.scality.vaultclient.utils;

public final class VaultServicesTestConstants {

    public static final String DEFAULT_ACCOUNT_ID = "123123123123";

    public static final String DEFAULT_ARN_STR = "\"arn:aws:iam::123123123123:/";

    public static final String DEFAULT_CANONICAL_ID = "31e38bcfda3ab1887587669ee25a348cc89e6e2e87dc38088289b1b3c5329b30";

    public static final String DEFAULT_ACCOUNT_NAME = "Account5425";

    public static final String DEFAULT_EMAIL_ADDR = "xyz@scality.com";

    public static final int DEFAULT_LIST_ACCOUNTS_COUNT = 10;
    public static final int TEST_LIST_ACCOUNTS_MARKER_VAL = 2;
    public static final int TEST_LIST_ACCOUNTS_COUNT = 4;
    public static final int TEST_LIST_ACCOUNTS_COUNT_2 = -1;

    public static final long TEST_ACCESS_KEY_DURATION_SECONDS = 60;
    public static final long TEST_ACCESS_KEY_DURATION_SECONDS_ERR = -1;
    public static final String ACTIVE_STR = "Active";
    public static final String NA_STR = "N/A";

    public static final String TEST_ACCESS_KEY = "access_key";
    public static final String TEST_SECRET_KEY = "secret_key";
    public static final String TEST_ACCESS_KEY1 = "access_key1";
    public static final String TEST_SECRET_KEY1 = "secret_key1";
    public static final String TEST_SESSION_TOKEN = "session_token";
    public static final String TEST_SESSION_NAME = "session";
    public static final String TEST_ROLE_ARN = "arn:aws:iam::0123456789:role/osis";
    public static final String TEST_ASSUMED_USER_ARN = "arn:aws:sts::0123456789:assumed-role/osis/session1";

    public static final String ERR_EMAIL_ADDR_INVALID = "Invalid EmailAddress";
    public static final String ERR_NAME_INVALID = "Invalid Name";
    public static final String ERR_ARN_NULL = "Arn cannot be null";
    public static final String ERR_CREATE_DATE_NULL = "CreateDate cannot be null";
    public static final String ERR_ID_NULL = "Id cannot be null";
    public static final String ERR_ID_VALUE = "Id not expected value";
    public static final String ERR_CANONICAL_ID_NULL = "CanonicalId cannot be null";
    public static final String ERR_CUSTOM_ATTRIBUTES_NULL = "Custom Attributes cannot be null";
    public static final String ERR_CUSTOM_ATTRIBUTES_IS_NULL = "Custom Attributes should be null";
    public static final String ERR_INVALID_FILTERKEY_RESPONSE = "Invalid response for filterKey";
    public static final String ERR_INVALID_FILTERKEYSTARTSWITH_RESPONSE = "Invalid response for filterKeyStartsWith";
    public static final String ERR_INVALID_ACCOUNT_COUNT = "Invalid Accounts count";
}
