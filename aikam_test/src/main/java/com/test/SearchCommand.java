package com.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchCommand {
    public static void run(String inputFile, String outputFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<Object[]>> res = new ArrayList<>();
        List<String[]> criterias = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(inputFile));

            for (JsonNode criteriaNode : rootNode.get("criterias")) {
                if (criteriaNode.has("lastName")) {

                    String lastName = criteriaNode.get("lastName").asText();

                    try {
                        List<Object[]> result = DatabaseWorker.searchByLastName(lastName);
                        res.add(result);
                        criterias.add(new String[] {"lastName", lastName});
//                        WriterJson.writeResultToJson(result, outputFile, new String[] {"lastName", lastName});
                    } catch (ErrorResponse e) {
                        WriterJson.writeErrorToJson(e.getMessage(), outputFile);
                        System.exit(1);
                    }
                } else if (criteriaNode.has("productName") && criteriaNode.has("minTimes")) {

                    String productName = criteriaNode.get("productName").asText();
                    int minTimes = criteriaNode.get("minTimes").asInt();

                    try {
                        List<Object[]> result = DatabaseWorker.searchByProductsAndCount(productName, minTimes);
                        res.add(result);
                        criterias.add(new String[] {"productName", productName, "minTimes", String.valueOf(minTimes)});
//                        WriterJson.writeResultToJson(result, outputFile,
//                                new String[] {"productName", productName, "minTimes", String.valueOf(minTimes)});
                    } catch (ErrorResponse e) {
                        WriterJson.writeErrorToJson(e.getMessage(), outputFile);
                        System.exit(1);
                    }
                }
//                else if (criteriaNode.has("minExpenses") && criteriaNode.has("maxExpenses")) {
//                    double minExpenses = criteriaNode.get("minExpenses").asDouble();
//                    double maxExpenses = criteriaNode.get("maxExpenses").asDouble();
//
//                    processByExpenses(minExpenses, maxExpenses);
//                } else if (criteriaNode.has("badCustomers")) {
//                    int badCustomers = criteriaNode.get("badCustomers").asInt();
//                    processByBadCustomers(badCustomers);
//                }
                // Другие обработки для остальных критериев
            }
            WriterJson.writeResultToJson(res, outputFile, criterias);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
