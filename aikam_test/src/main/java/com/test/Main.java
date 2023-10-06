package com.test;

import com.test.command.SearchCommand;
import com.test.command.StatCommand;

public class Main {
    public static void main(String[] args) {
        InputArguments inputArgs = InputArguments.parse(args);
        if (inputArgs.getCommand().equals("search")) {
            new SearchCommand().run(inputArgs.getInputFile(), inputArgs.getOutputFile());
        } else {
            new StatCommand().run(inputArgs.getInputFile(), inputArgs.getOutputFile());
        }
    }
}