public final class TrapReceiver {
    public void receive(SnmpTrap trap) {
        System.out.println(trap.formatBlock());
        System.out.println();
    }
}
