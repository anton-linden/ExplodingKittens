package Events;
public class EventFactory {

    /**
     * Factory design pattern to create a new event from a given class in the Events packet.
     * @param name class name in the Events packet to create.
     * @return the new Event.
     */
    public Event makeEvent(String name) {
        if (name.equals("Event")) { return null; }  //Guard statement to stop from accidentally instantiating the abstract class.

        Event event = null;

        try {
            event = (Event) Class.forName("Events." + name).newInstance();
        } catch (ClassNotFoundException e) {
            System.out.println("Error: The given event name is not a class.");
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }

        return event;
    }

}
