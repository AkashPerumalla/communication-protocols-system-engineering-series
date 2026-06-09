import java.util.List;

public final class SNMPWalkDemo {
    public static void main(String[] args) {
        SnmpMibAgent agent = DemoDataFactory.walkAgent();
        List<SnmpVariable> variables = agent.walkFrom("1.3.6.1.2.1.1.1.0");

        System.out.println("SNMP WALK START");
        for (SnmpVariable variable : variables) {
            System.out.println(variable.oid() + " = " + variable.value());
        }
        System.out.println();
        System.out.println("GETNEXT traversal sample:");
        SnmpResponse next = agent.getNext("1.3.6.1.2.1.1.1.0");
        System.out.println(next.oid() + " -> " + next.format());
    }
}
