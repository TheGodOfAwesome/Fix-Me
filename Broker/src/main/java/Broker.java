import Controllers.BrokerController;

import java.util.Scanner;

public class Broker {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to the Stock Broker App\n");
        String help = "Menu\n " +
                "'Buy'/'B' To Buy.\n " +
                "'Sell'/'S' To Sell.\n " +
                "'Crazy'/'C' To Sell Too High.\n " +
                "'Exit'/'X' to quit game\n";
        System.out.println(help);
        boolean run = true;
        Scanner sc = new Scanner(System.in);
        while (run) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("x"))
                break;
            else if (input.equalsIgnoreCase("Buy") || input.equalsIgnoreCase("B")) {
                Trade(Integer.parseInt(args[0]), 1);
                break;
            } else if (input.equalsIgnoreCase("Sell") || input.equalsIgnoreCase("S")) {
                Trade(Integer.parseInt(args[0]), 2);
                break;
            } else if (input.equalsIgnoreCase("Crazy") || input.equalsIgnoreCase("C")) {
                Trade(Integer.parseInt(args[0]), 3);
                break;
            } else
                System.out.println(input + " is an invalid command\n" + help + "Continue...");
        }
        sc.close();
        return;


    }

    private static void Trade(int marketId, int transaction) {
        //args[0] == market id && args[1] == 1 == buy || args[1] == 2 == sell
        BrokerController broker = new BrokerController(marketId, transaction);
        try {
            broker.contact();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
