import Controllers.MarketController;

public class Market {

    public static void main(String[] args) {
        //argument 1 == quantity, argument 2 == price
        MarketController market = new MarketController(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        try {
            market.contact();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
