package ewm.Dtos;

import ewm.Entityes.Event;

public class EventUpdateResponse extends Event {
    String stateAction;
    String state;

    public EventUpdateResponse(Event event, String stateAction) {
        super(event);
        this.stateAction = stateAction;
        this.state = null;
    }
}
