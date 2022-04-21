package com.example.springsessiondemo.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper mapper() {
        return objectMapper;
    }

    public static <T> List<T> convertToList(Object from, Class<T> elemClass) {
        if (from == null) {
            return null;
        }
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elemClass);
        if (from instanceof String) {
            try {
                return objectMapper.readValue((String) from, type);
            } catch (IOException e) {
                return null;
            }
        } else {
            try {
                return objectMapper.convertValue(from, type);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public static <T, R> List<R> convertList(List<T> from, Class<R> elemClass) {
        if (from == null) {
            return null;
        }

        return from.stream().map(e -> convert(e, elemClass)).collect(Collectors.toList());
    }

    public static <T> T convertFromString(String from, Class<T> cls) {
        if (from == null) {
            return null;
        }

        try {
            return objectMapper.readValue(from, cls);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> T convertFromBytes(byte[] from, Class<T> cls) {
        if (from == null) {
            return null;
        }

        try {
            return objectMapper.readValue(from, cls);
        } catch (IOException e) {
            return null;
        }
    }

    public static <T> String convertToString(T from) {
        if (from == null) {
            return "";
        }
        if (from instanceof String) {
            return (String) from;
        }

        try {
            return objectMapper.writeValueAsString(from);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> String convertToStringPretty(T from) {
        if (from == null) {
            return "";
        }
        if (from instanceof String) {
            return (String) from;
        }

        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(from);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    public static <T> byte[] convertToBytes(T from) {
        if (from == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsBytes(from);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static <T, R> R convert(T from, Class<R> cls) {
        if (from == null) {
            return null;
        }

        try {
            return objectMapper.convertValue(from, cls);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static JsonNode readTree(String value) {
        try {
            return objectMapper.readTree(value);
        } catch (IOException e) {
            return null;
        }
    }

    public static JsonNode readTree(byte[] bytes) {
        try {
            return objectMapper.readTree(bytes);
        } catch (IOException e) {
            return null;
        }
    }

    public static String treeToString(JsonNode tree) {
        try {
            return objectMapper.treeToValue(tree, String.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static String readStringField(String content, String field) {
        try (JsonParser parser = mapper().getFactory().createParser(content)) {
            return doReadStringField(parser, field);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String doReadStringField(
            JsonParser parser,
            String field) throws IOException {
        JsonToken token = parser.nextToken();
        if (token != JsonToken.START_OBJECT) {
            return null;
        }

        token = parser.nextToken();
        while (token != null && token != JsonToken.END_OBJECT) {
            String name = parser.getCurrentName();
            if (field.equals(name)) {
                parser.nextToken();
                return parser.getText();
            }

            // 只读第一层
            if (token.isStructStart()) {
                parser.skipChildren();
            }

            token = parser.nextToken();
        }

        return null;
    }

    public static JsonNode readField(JsonNode kb, String path) {
        String[] fields = path.split("\\.");

        JsonNode value = null;
        for (String field : fields) {
            value = getField(value == null ? kb : value, field);
            if (value == null) {
                break;
            }
        }

        return value;
    }

    private static JsonNode getField(JsonNode root, String field) {
        if (root == null) {
            return null;
        }
        return root.get(field);
    }

    public static ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public static ArrayNode createArrayNode() {
        return objectMapper.createArrayNode();
    }

    public static <T> ArrayNode convertListToArrayNode(List<T> list) {
        return objectMapper.valueToTree(list);
    }
}
