package com.serverless.util;

import com.serverless.model.Suburb;

public class Util {
    public static void throwMissingParameterException(String param) {
        throw new IllegalArgumentException("400 Bad Parameter: '" + param + "' is required");
    }

    public static int getIntegerValue(String param) {
        if (param != null) return Integer.parseInt(param);
        return 0;
    }

    public static void checkParameter(String actualParam, String expectedParam) {
        if (actualParam == null) Util.throwMissingParameterException(expectedParam);
    }

    public static void checkObjectParams(Suburb suburb) {
        if (suburb.getName() == null) Util.throwMissingParameterException(Constants.SUBURB);
        if (suburb.getPostcode() == 0) Util.throwMissingParameterException(Constants.POSTCODE);
    }

}