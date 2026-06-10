import java.util.ArrayList;
import java.util.List;

public class TrapReceiverCore {
    private final List<TrapEvent> received = new ArrayList<>();

    public void receive(TrapEvent event) {
        received.add(event);
    }

    public List<TrapEvent> received() {
        return List.copyOf(received);
    }
}
