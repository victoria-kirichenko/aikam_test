package com.test.command;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.database.DatabaseWorker;
import com.test.response.ErrorResponse;
import com.test.json.WriterJson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class StatCommand implements Command {
    @Override
    public void run(String inputFile, String outputFile) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<Object[]>> res = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(inputFile));

            if (rootNode.has("startDate") && rootNode.has("endDate")) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                String startDateStr = rootNode.get("startDate").asText();
                Date startDate = Date.valueOf(startDateStr);

                String endDateStr = rootNode.get("endDate").asText();
                Date endDate = Date.valueOf(endDateStr);

                try {
                    List<Object[]> result = DatabaseWorker.stat(startDate, endDate);
                    res.add(result);
                } catch (ErrorResponse e) {
                    WriterJson.writeErrorToJson(e.getMessage(), outputFile);
                    System.exit(1);
                }
                WriterJson.writeStatResultToJson(res, outputFile, startDate, endDate);
            }
        } catch (IOException e) {
            WriterJson.writeErrorToJson(e.getMessage(), outputFile);
        }
    }
}
