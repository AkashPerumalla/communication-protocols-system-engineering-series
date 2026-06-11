import java.util.List;

public final class SystemGroupExplorer {
    public static void main(String[] args) {
        MibRepository repository = MibRepository.createDefault();
        MibConsole.printBanner("DAY 10 - EXERCISE 01 - MIB-II SYSTEM GROUP", "MIB-II System Group Explorer");
        printSystemGroup(repository.systemGroup());
    }

    private static void printSystemGroup(List<MibObject> objects) {
        for (MibObject object : objects) {
            MibConsole.printObject(object);
        }
    }
}
