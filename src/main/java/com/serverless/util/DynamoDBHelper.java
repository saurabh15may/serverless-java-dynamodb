package com.serverless.util;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.serverless.model.Suburb;
import java.util.UUID;
import java.util.HashMap;
import java.util.Map;

public class DynamoDBHelper {

    public static String createSuburbRecord(Suburb suburb){
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient().withRegion(Region.getRegion(Regions.US_EAST_1));
        DynamoDB dynamoDB = new DynamoDB(dynamoDBClient);
        Table table = dynamoDB.getTable(Constants.DB_NAME);

        try {
            PutItemSpec putItemSpec = new PutItemSpec().withItem(DynamoDBHelper.suburbToDBItem(suburb));
            table.putItem(putItemSpec);
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("400 Bad Rating");

        }

        return Constants.RESPONSE_CREATED;
    }

    public static Suburb getRecordforPostcode(String postcode){
          AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient().withRegion(Region.getRegion(Regions.US_EAST_1));
        
          Condition scanFilterCondition = new Condition()
                  .withComparisonOperator(ComparisonOperator.EQ.toString())
                  .withAttributeValueList(new AttributeValue().withS(postcode));
  
          HashMap<String, Condition> conditions = new HashMap<>();
  
          conditions.put(Constants.POSTCODE, scanFilterCondition);
          ScanRequest scanRequest = new ScanRequest().withTableName(Constants.DB_NAME).withScanFilter(conditions);
          ScanResult scan = dynamoDBClient.scan(scanRequest);
  
          Suburb suburb = new Suburb();
          for (Map<String, AttributeValue> item : scan.getItems()) {
              suburb.setName(item.get(Constants.SUBURB).getS());
              suburb.setPostcode(Util.getIntegerValue(item.get(Constants.SUBURB).getN()));
          }

        return suburb;
    }

    public static Suburb getRecordforSuburb(String suburbName){
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient().withRegion(Region.getRegion(Regions.US_EAST_1));
      
        Condition scanFilterCondition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(suburbName));

        HashMap<String, Condition> conditions = new HashMap<>();

        conditions.put(Constants.POSTCODE, scanFilterCondition);
        ScanRequest scanRequest = new ScanRequest().withTableName(Constants.DB_NAME).withScanFilter(conditions);
        ScanResult scan = dynamoDBClient.scan(scanRequest);

        Suburb suburb = new Suburb();
        for (Map<String, AttributeValue> item : scan.getItems()) {
            suburb.setName(item.get(Constants.SUBURB).getS());
            suburb.setPostcode(Util.getIntegerValue(item.get(Constants.SUBURB).getN()));
        }

      return suburb;
  }

    public static Item suburbToDBItem(Suburb suburb) {
        Item item = new Item();
        item.withString("id", UUID.randomUUID().toString());
        item.withString(Constants.SUBURB, suburb.getName());
        item.withInt(Constants.POSTCODE, suburb.getPostcode());

        return item;
    }
}