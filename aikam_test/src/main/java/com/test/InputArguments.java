package com.test;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;

public class InputArguments {
    @Parameter(description = "command")
    private String command;

    @Parameter(names = {"--input"}, description = "input file")
    private String inputFile;

    @Parameter(names = {"--output"}, description = "output file")
    private String outputFile;

    public static InputArguments parse(String[] args) {
        InputArguments inputArgs = new InputArguments();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(inputArgs)
                .build();

        try {
            jCommander.parse(args);
            inputArgs.validateCommand();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            jCommander.usage();
            System.exit(1);
        }

        return inputArgs;
    }

    private void validateCommand() {
        if (!isValidCommand(command)) {
            throw new IllegalArgumentException("Invalid command. Use 'search' or 'stat'.");
        }

        File inpFile = new File(inputFile);
        if (!inpFile.exists()) {
            throw new IllegalArgumentException("Input file doesn't exist");
        }

        File outFile = new File(outputFile);
        if (!outFile.exists()) {
            throw new IllegalArgumentException("Output file doesn't exist");
        }
    }

    private static boolean isValidCommand(String command) {
        return "search".equals(command) || "stat".equals(command);
    }

    public String getCommand() {
        return command;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputFile() {
        return outputFile;
    }
}
