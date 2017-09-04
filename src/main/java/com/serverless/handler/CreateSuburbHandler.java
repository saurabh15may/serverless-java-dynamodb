package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.model.Suburb;
import java.util.Map;
import com.serverless.model.ApiGatewayResponse;
import com.serverless.util.DynamoDBHelper;
import com.serverless.util.Constants;
import com.serverless.util.Util;
import org.apache.log4j.Logger;
import java.util.HashMap;


public class CreateSuburbHandler implements RequestHandler<Suburb, ApiGatewayResponse> {

    private static final Logger LOG = Logger.getLogger(CreateSuburbHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Suburb suburb, Context context) {
        LOG.info("suburb: " + suburb);

        Util.checkObjectParams(suburb);

        int statusCode = 0;
        Object responseBody = null;  
		Map<String, String> headers = createHeader();

        try{
            //Create record into DynamoDB
            responseBody = DynamoDBHelper.createSuburbRecord(suburb);
            statusCode = Constants.STATUS_CODE_SUCCESS;
        }catch(Exception e){
            LOG.error("Exception in creating record in DynamoDB "+e);
            responseBody = Constants.RESPONSE_ERROR;
            statusCode = Constants.STATUS_CODE_ERROR;
        }

        return ApiGatewayResponse.builder()
                .setStatusCode(statusCode)
                .setObjectBody(responseBody)
                .setHeaders(headers)
                .build();
    }

    private static Map<String, String> createHeader(){
		Map<String, String> headers = new HashMap<>();
		headers.put("X-Powered-By", "AWS Lambda & Serverless");
		headers.put("Content-Type", "application/json");
		return headers;
	}


}
