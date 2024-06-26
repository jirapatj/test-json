package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try {
            Main m = new Main();
            m.process();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process() throws IOException {
        // Read json file
        String jsonString = new String(getClass().getResourceAsStream("/input.json").readAllBytes());

        // Read JSON string
        JsonNode jsonNode = new ObjectMapper().readTree(jsonString);
        int targetLevel = 2;
        processJsonAtLevel(jsonNode, targetLevel, 0, "");
    }

    private void processJsonAtLevel(JsonNode jsonNode, int targetLevel, int currentLevel, String parentName) {
        if (currentLevel == targetLevel) {
            // If the current level matches the target level, print attributes with parent name
            printAttributes(jsonNode, parentName);
        } else if (currentLevel < targetLevel) {
            // If current level is less than the target level, continue recursion
            if (jsonNode.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
                while (fieldsIterator.hasNext()) {
                    Map.Entry<String, JsonNode> field = fieldsIterator.next();
                    String attributeName = field.getKey();
                    JsonNode fieldValue = field.getValue();
                    processJsonAtLevel(fieldValue, targetLevel, currentLevel + 1, attributeName);
                }
            } else if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    processJsonAtLevel(node, targetLevel, currentLevel + 1, parentName);
                }
            }
        }
    }

    private void printAttributes(JsonNode jsonNode, String parentName) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
            while (fieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> field = fieldsIterator.next();
                String attributeName = field.getKey();
                System.out.println("Parent: " + parentName + ", Attribute: " + attributeName);
            }
        }
    }
}