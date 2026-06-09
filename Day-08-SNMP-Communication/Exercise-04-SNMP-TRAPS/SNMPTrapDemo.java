public final class SNMPTrapDemo {
    public static void main(String[] args) {
        TrapGenerator generator = new TrapGenerator();
        TrapReceiver receiver = new TrapReceiver();

        for (SnmpTrap trap : generator.traps()) {
            receiver.receive(trap);
        }
    }
}
