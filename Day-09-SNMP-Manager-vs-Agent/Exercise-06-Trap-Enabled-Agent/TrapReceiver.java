public class TrapReceiver extends TrapReceiverCore {
    public void printReceipt(TrapEvent event) {
        receive(event);
        System.out.println("TRAP RECEIVED");
        System.out.println("Source: " + event.source());
        System.out.println("Severity: " + event.severity());
        System.out.println("Alarm: " + event.alarm());
        System.out.println("Detail: " + event.detail());
        System.out.println();
    }
}
