package com.test.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class WriterJson {
    private static String errorFilePath = "error.json";
    public static void writeErrorToJson(String message, String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("type", "error");
        objectNode.put("message", message);

        try {
            objectMapper.writeValue(new File((filePath == null) ? errorFilePath : filePath), objectNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSearchResultToJson(List<List<Object[]>> data, String filePath, List<String[]> criteria) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("type", "search");

        ArrayNode resultsArray = objectMapper.createArrayNode();

        for (int i = 0; i < data.size(); i++) {
            ObjectNode resultNode = objectMapper.createObjectNode();
            ObjectNode innerCriteriaNode = objectMapper.createObjectNode();

            for (int j = 0; j < criteria.get(i).length - 1; j += 2) {
                innerCriteriaNode.put(criteria.get(i)[j], criteria.get(i)[j + 1]);
            }

            resultNode.put("criteria", innerCriteriaNode);

            ArrayNode dataArray = objectMapper.createArrayNode();

            for (Object[] rowData : data.get(i)) {
                ObjectNode temp = objectMapper.createObjectNode();
                for (int j = 0; j < rowData.length - 1; j += 2) {
                    temp.put(String.valueOf(rowData[j]), String.valueOf(rowData[j + 1]));
                }
                dataArray.add(temp);
            }

            resultNode.put("results", dataArray);
            resultsArray.add(resultNode);
        }

        objectNode.putArray("results").addAll(resultsArray);

        try {
            objectMapper.writeValue(new File((filePath == null) ? errorFilePath : filePath), objectNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStatResultToJson(List<List<Object[]>> data, String filePath, Date startDate, Date endDate) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("type", "stat");

        int totalDays = getBusinessDaysBetweenDates(startDate, endDate);
        objectNode.put("totalDays", String.valueOf(totalDays));


        // здесь должно было быть заполнение ObjectNode результатом операции stat

        try {
            objectMapper.writeValue(new File((filePath == null) ? errorFilePath : filePath), objectNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getBusinessDaysBetweenDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int workDays = 0;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                workDays++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return workDays;
    }
}
