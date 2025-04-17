package org.todolist.services;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

public class DynamoDbService {

	private final DynamoDbClient dynamoDbClient;

	private final String tableName;


	public DynamoDbService(String tableName) {
		this.dynamoDbClient = DynamoDbClient.builder().region(Region.US_EAST_1).build();
		this.tableName = tableName;
	}


	public void putItem(String id, String description) {
		Map<String, AttributeValue> item = new HashMap<>();
		item.put("id", AttributeValue.builder().s(id).build());
		item.put("descricao", AttributeValue.builder().s(description).build());

		PutItemRequest request = PutItemRequest.builder().tableName(tableName).item(item).build();

		dynamoDbClient.putItem(request);
	}


	public String getItem(String key, String keyValue) {
		GetItemRequest request = GetItemRequest.builder().tableName(tableName)
			.key(Map.of(key, AttributeValue.builder().s(keyValue).build())).build();

		return dynamoDbClient.getItem(request).item().get("descricao").s();
	}

	public void deleteItem(String key, String keyValue) {
		DeleteItemRequest request = DeleteItemRequest.builder().tableName(tableName)
			.key(Map.of(key, AttributeValue.builder().s(keyValue).build())).build();

		dynamoDbClient.deleteItem(request);
	}
}