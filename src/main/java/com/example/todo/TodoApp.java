package com.example.todo;

import com.example.todo.utils.CommandLineInput;
import com.example.todo.utils.CommandLineInputHandler;

import java.io.IOException;

/**
 * Created by krishna_hotha on 3/28/16.
 */
public class TodoApp {

    public static final char DEFAULT_INPUT = '\u0000';

    public static void main(String[] args) throws IOException {

        CommandLineInputHandler commandLineInputHandler=new CommandLineInputHandler();
        commandLineInputHandler.printOptions();
        char command=DEFAULT_INPUT;
        while(CommandLineInput.EXIT.getShortCmd()!=command){
            String input=commandLineInputHandler.readInput();
            char[] inputChars= input.length()==1 ? input.toCharArray():new char[]{DEFAULT_INPUT};
            command=inputChars[0];
            CommandLineInput commandLineInput=CommandLineInput.getCommandLineInputForInput(command);
            commandLineInputHandler.processInput(commandLineInput);
        }
    }

}
