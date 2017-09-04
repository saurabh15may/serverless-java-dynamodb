package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.model.Suburb;
import java.util.Map;
import com.serverless.model.ApiGatewayResponse;
import com.serverless.util.Constants;
import com.serverless.util.DynamoDBHelper;

import com.serverless.util.Util;
import org.apache.log4j.Logger;
import java.util.HashMap;

public class GetPostcodeHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private static final Logger LOG = Logger.getLogger(GetPostcodeHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {

        String suburbName = (String) input.get("name");
        //Util.checkParameter(suburbName,Constants.SUBURB);
        
		LOG.info("suburbName: " + suburbName);
		Suburb suburb= null;
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");

        if(suburbName != null){
            suburb = DynamoDBHelper.getRecordforSuburb(suburbName);
            return ApiGatewayResponse.builder()
                    .setStatusCode(200)
                    .setObjectBody(suburb)
                    .setHeaders(headers)
                    .build();
        }

        return ApiGatewayResponse.builder()
        .setStatusCode(400)
        .setObjectBody("400 Bad Parameter")
        .setHeaders(headers)
        .build();
    }

}
