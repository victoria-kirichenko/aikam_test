package com.test;
public class Main {
    public static void main(String[] args) {
        InputArguments inputArgs = InputArguments.parse(args);
        if (inputArgs.getCommand().equals("search")) {
            SearchCommand.run(inputArgs.getInputFile(), inputArgs.getOutputFile());
        }
    }
}