package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.model.Suburb;
import java.util.Map;
import com.serverless.model.ApiGatewayResponse;
import com.serverless.util.DynamoDBHelper;

import com.serverless.util.Util;
import org.apache.log4j.Logger;
import java.util.HashMap;


public class CreateSuburbHandler implements RequestHandler<Suburb, ApiGatewayResponse> {

    private static final Logger LOG = Logger.getLogger(CreateSuburbHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Suburb suburb, Context context) {
        LOG.info("suburb: " + suburb);

        LOG.info("suburbName: " + suburb.getName()+" - postcode: " + suburb.getPostcode());

        Util.checkObjectParams(suburb);

        String response = DynamoDBHelper.createSuburbRecord(suburb);
        
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Powered-By", "AWS Lambda & Serverless");
        headers.put("Content-Type", "application/json");
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(response)
                .setHeaders(headers)
                .build();
    }


}
