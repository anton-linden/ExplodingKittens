package Cards;

public class CardFactory {

    /**
     * Factory initializer that creates a new card from a class name. Card must be in the packet Cards!
     * @param name Card name, for example cattermelon, skip or nope.
     * @return a new card object.
     */
    public Card makeCard(String name) {
        if (name.equals("Card")) { return null; }  //Guard statement to stop from accidentally instantiating the abstract class.

        Card card = null;

        try {
            card = (Card) Class.forName("Cards." + name).newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: The given card name is not a class.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }

        return card;
    }
}
