package machine;

import java.io.BufferedReader;
import java.io.IOException;

public class CoffeeMachineIns {
    private int water;
    private int milk;
    private int coffeeBeans;
    private int cups;
    private int money;
    private CoffeMachineState state = CoffeMachineState.GET_MAIN_MENU;

    public CoffeeMachineIns(int water, int milk, int coffeeBeans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cups = cups;
        this.money = money;
        externalInputHandler("");
    }


    public boolean externalInputHandler(String input) {
        switch (state) {
            case GROUND_STATE:
                if (!interactionHandler(input)) {
                    return false;
                }
                break;
            case COFFEE_MENU:
                buy(input);
                state = CoffeMachineState.GET_MAIN_MENU;
                break;
            case FILLING_WATER:
            case FILLING_MILK:
            case FILLING_COFFEE:
            case FILLING_CUPS:
                fill(input);
                break;
        }
        switch (state) {
            case GET_MAIN_MENU:
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                state = CoffeMachineState.GROUND_STATE;
                break;
            case GET_COFFEE_MENU:
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - previous menu:");
                state = CoffeMachineState.COFFEE_MENU;
                break;
            case FILLING_WATER_MENU:
                System.out.println("\nWrite how many ml of water do you want to add:");
                state = CoffeMachineState.FILLING_WATER;
                break;
            case FILLING_MILK_MENU:
                System.out.println("\nWrite how many ml of milk do you want to add:");
                state = CoffeMachineState.FILLING_MILK;
                break;
            case FILLING_COFFEE_MENU:
                System.out.println("\nWrite how many grams of coffee beans do you want to add:");
                state = CoffeMachineState.FILLING_COFFEE;
                break;
            case FILLING_CUPS_MENU:
                System.out.println("\nWrite how many disposable cups of coffee do you want to add:");
                state = CoffeMachineState.FILLING_CUPS;
                break;

        }
        return true;
    }

    private boolean interactionHandler(String input) {
        switch (input) {
            case "buy":
                state = CoffeMachineState.GET_COFFEE_MENU;
                break;
            case "fill":
                state = CoffeMachineState.FILLING_WATER_MENU;
                break;
            case "take":
                takeMoney();
                state = CoffeMachineState.GET_MAIN_MENU;
                break;
            case "remaining":
                System.out.println("\n"+this);
                state = CoffeMachineState.GET_MAIN_MENU;
                break;
            case "exit":
                return false;
            default:
                System.out.println("\nNo such option!");
                state = CoffeMachineState.GET_MAIN_MENU;
                break;
        }
        return true;
    }

    private boolean checkIfThereIsEnoughtResources(CoffeeType coffeeType, int cups) {
        if (coffeeType.getWater() < this.water) {
            if (coffeeType.getMilk() < this.milk) {
                if (coffeeType.getCoffeeBeans() < this.coffeeBeans) {
                    if (cups < this.cups) {
                        System.out.println("\nI have enough resources, making you a coffee!");
                        return true;
                    } else {
                        System.out.println("\nSorry, not enough cups!");
                    }
                } else {
                    System.out.println("\nSorry, not enough coffee!");
                }
            } else {
                System.out.println("\nSorry, not enough milk!");
            }
        } else {
            System.out.println("\nSorry, not enough water!");
        }
        return false;
    }

    private void buy(String input) {
        switch (input) {
            case "1":
                if (checkIfThereIsEnoughtResources(CoffeeType.ESPRESSO, 1)) {
                    sellCoffee(CoffeeType.ESPRESSO, 1);
                }
                break;
            case "2":
                if (checkIfThereIsEnoughtResources(CoffeeType.LATTE, 1)) {
                    sellCoffee(CoffeeType.LATTE, 1);
                }
                break;
            case "3":
                if (checkIfThereIsEnoughtResources(CoffeeType.CAPPUCCINO, 1)) {
                    sellCoffee(CoffeeType.CAPPUCCINO, 1);
                }
                break;
            case "back":
                break;
            default:
                System.out.println("\nNo such choice!");
        }
    }

    private void fill(String input) {
        switch (state){
            case FILLING_WATER:
                addWater(Integer.parseInt(input));
                state = CoffeMachineState.FILLING_MILK_MENU;
                break;
            case FILLING_MILK:
                addMilk(Integer.parseInt(input));
                state  = CoffeMachineState.FILLING_COFFEE_MENU;
                break;
            case FILLING_COFFEE:
                addCoffeeBeans(Integer.parseInt(input));
                state  = CoffeMachineState.FILLING_CUPS_MENU;
                break;
            case FILLING_CUPS:
                addCups(Integer.parseInt(input));
                state = CoffeMachineState.GET_MAIN_MENU;
                break;
        }
    }

    private void takeMoney() {
        System.out.println(String.format("I gave you $%d", encashment()));
    }

    private void sellCoffee(CoffeeType coffeeType, int cups) {
        getPayment(coffeeType.getPrice());
        reduceWater(coffeeType.getWater());
        reduceMilk(coffeeType.getMilk());
        reduceCoffeeBeans(coffeeType.getCoffeeBeans());
        reduceCups(cups);
    }

    private void addWater(int amount) {
        water += amount;
    }

    private void reduceWater(int amount) {
        water -= amount;
    }

    private void addMilk(int amount) {
        milk += amount;
    }

    private void reduceMilk(int amount) {
        milk -= amount;
    }

    private void addCoffeeBeans(int amount) {
        coffeeBeans += amount;
    }

    private void reduceCoffeeBeans(int amount) {
        coffeeBeans -= amount;
    }

    private void addCups(int amount) {
        cups += amount;
    }

    private void reduceCups(int amount) {
        cups -= amount;
    }

    private void getPayment(int amount) {
        money += amount;
    }

    private int encashment() {
        int money = this.money;
        this.money = 0;
        return money;
    }

    @Override
    public String toString() {
        return String.format(
                "The coffee machine has:\n%d of water\n%d of milk" +
                        "\n%d of coffee beans\n%d of disposable cups\n%d of money",
                water, milk, coffeeBeans, cups, money);
    }
}
