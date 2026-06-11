import java.util.List;

public final class MibRepository {
    private final MibTree tree;

    private MibRepository(MibTree tree) {
        this.tree = tree;
    }

    public static MibRepository createDefault() {
        MibTree tree = new MibTree();
        for (MibObject object : SampleData.allObjects()) {
            tree.addObject(object);
        }
        return new MibRepository(tree);
    }

    public MibTree tree() {
        return tree;
    }

    public List<MibObject> systemGroup() {
        return SampleData.systemGroupObjects();
    }

    public List<MibObject> interfaceObjects() {
        return SampleData.interfaceObjects();
    }

    public List<MibObject> enterpriseObjects() {
        return EnterpriseMib.avantelObjects();
    }

    public List<MibObject> telecomObjects() {
        return SampleData.telecomObjects();
    }

    public List<MibObject> satcomObjects() {
        return SampleData.satcomObjects();
    }

    public List<String[]> dashboardRows() {
        return SampleData.dashboardRows();
    }
}
