package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class FindLevel {
    public static void main(String[] args) throws IOException {
        String jsonString = new String(FindLevel.class.getResourceAsStream("/input.json").readAllBytes());
        // Read JSON string

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Read JSON string
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        // Specify the level to search for
        int endTargetLevel = 6;

        // Find all attributes at the specified level
        for (int idx = 0; idx < endTargetLevel; idx++) {
            findAttributesAtLevel(jsonNode, idx, 1, "");
        }
    }

    private static void findAttributesAtLevel(JsonNode jsonNode, int targetLevel, int currentLevel, String parentName) {
        if (currentLevel == targetLevel) {
            // If the current level matches the target level, print attributes with parent name
            printAttributes(jsonNode, parentName, currentLevel);
        } else if (currentLevel < targetLevel) {
            // If current level is less than the target level, continue recursion
            if (jsonNode.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
                while (fieldsIterator.hasNext()) {
                    Map.Entry<String, JsonNode> field = fieldsIterator.next();
                    String attributeName = field.getKey();
                    JsonNode fieldValue = field.getValue();
                    findAttributesAtLevel(fieldValue, targetLevel, currentLevel + 1, attributeName);
                }
            } else if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    findAttributesAtLevel(node, targetLevel, currentLevel + 1, parentName);
                }
            }
        }
    }

    private static void printAttributes(JsonNode jsonNode, String parentName, int level) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fieldsIterator = jsonNode.fields();
            while (fieldsIterator.hasNext()) {
                Map.Entry<String, JsonNode> field = fieldsIterator.next();
                String attributeName = field.getKey();
//                System.out.println("Parent: " + parentName + ", Level: " + level + ", Attribute: " + attributeName);
                String content = "Parent: " + parentName + ", Level: " + level + ", Attribute: " + attributeName;
                writeFile(content);
            }
        }
    }

    private static void writeFile(String contentToAppend) {
        String filePath = "output.txt";

        try (FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            bufferedWriter.write(contentToAppend);
            bufferedWriter.newLine();  // Adds a new line after the content

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
