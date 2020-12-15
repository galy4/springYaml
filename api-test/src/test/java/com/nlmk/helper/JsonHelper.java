package com.nlmk.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import io.qameta.allure.Step;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

@Component
public class JsonHelper {

    @Step("convert json file to map")
    public Map<String, String> addKeys(String currentPath, JsonNode jsonNode, Map<String, String> map, List<Integer> suffix) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + "-";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map, suffix);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;

            for (int i = 0; i < arrayNode.size(); i++) {
                suffix.add(i + 1);
                addKeys(currentPath, arrayNode.get(i), map, suffix);

                if (i + 1 <arrayNode.size()){
                    suffix.remove(arrayNode.size() - 1);
                }
            }

        } else if (jsonNode.isValueNode()) {
            if (currentPath.contains("-")) {
                for (int i = 0; i < suffix.size(); i++) {
                    currentPath += "-" + suffix.get(i);
                }

                suffix = new ArrayList<>();
            }

            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
        return map;
    }

    @Step("сравнить POJO с файлом {filename}")
    public void comparePOJO_andFile(Object model, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonParser parser = mapper.getFactory().createParser(new File(filename));
        JsonNode jsonNode = mapper.readTree(parser);
        Map<String, String> map = new HashMap<>();
        addKeys("", jsonNode, map, new ArrayList<>());
        JsonNode jsonNode1 = mapper.valueToTree(model);
        Map<String, String> map1 = new HashMap<>();
        addKeys("", jsonNode1, map1, new ArrayList<>());
        map.forEach((key, value) -> assertThat(map1, hasEntry(key, value)));
    }
}
