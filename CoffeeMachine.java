package machine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CoffeeMachine {


    public static void main(String[] args) {

        String userInput = null;

        CoffeeMachineIns currentCoffeeMachine = new CoffeeMachineIns(
                400,
                540,
                120,
                9,
                550
        );

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            do{
                userInput = reader.readLine();
            } while (currentCoffeeMachine.externalInputHandler(userInput));
        } catch (IOException ioe) {
            System.out.println("IO Exception occurred!");
            ioe.printStackTrace();
            System.exit(1);
        } catch (NumberFormatException nfe) {
            System.out.println("Incorrect input!");
            nfe.printStackTrace();
            System.exit(2);
        }
    }
}
