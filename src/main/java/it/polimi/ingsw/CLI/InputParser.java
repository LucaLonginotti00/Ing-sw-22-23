package it.polimi.ingsw.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Predicate;

/**
 * This class represents the input parser.
 */
public class InputParser {

    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * This method gets an input line.
     * @return A string.
     */
    public static String getLine(){
        String line;

        do {
            try {
                while(InputParser.input.ready())
                    InputParser.input.readLine();
                while (!InputParser.input.ready()) {
                    Thread.sleep(100);
                }
                line = InputParser.input.readLine();
            } catch (InterruptedException | IOException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        } while ("".equals(line));
        return line;
    }

    /**
     * This method gets a string.
     * @param errorMessage An error message.
     * @param condition A condition.
     * @return A string.
     */
    public static String getString(String errorMessage, Predicate<String> condition){
        String line;
        do{
            line = getLine();
            if (condition.test(line))
                return line;
            else
                System.out.println(errorMessage);
        } while (true);
    }

    /**
     * This method gets an integer.
     * @return An integer.
     */
    public static Integer getInt(){
        String string;
        Integer i = null;
        boolean done = false;
        try {
            do {

                while(input.ready())
                    input.readLine();
                while (!input.ready()) {
                    Thread.sleep(200);
                }
                string = input.readLine();
                try {
                    i = Integer.parseInt(string);
                    done=true;
                } catch (NumberFormatException e) {
                    System.out.println("Error, please insert a number");
                }
            }while(!done);

        }catch (InterruptedException | IOException e){
            Thread.currentThread().interrupt();
            return null;
        }
        return i;
    }

    /**
     * This method gets an integer.
     * @param errorMessage An error message.
     * @param condition A condition.
     * @return An integer.
     */
    public static Integer getInt(String errorMessage, Predicate<Integer> condition){
        String string;
        Integer i = null;
        boolean done = false;

        try {

            while(input.ready())
                input.readLine();
            while (!input.ready()) {
                Thread.sleep(100);
            }

            do {
                string = input.readLine();
                try {
                    i = Integer.parseInt(string);
                    if (condition.test(i)) {
                        done = true;
                    }else{
                        System.out.println(errorMessage);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error, please insert a number");
                }
            }while(!done);

        }catch (InterruptedException | IOException e){
            Thread.currentThread().interrupt();
            return null;
        }
        return i;
    }

}
