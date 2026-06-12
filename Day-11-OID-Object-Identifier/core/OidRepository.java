import java.util.List;

public final class OidRepository {
    private final OidTree tree;
    private final List<DeviceProfile> devices;

    private OidRepository(OidTree tree, List<DeviceProfile> devices) {
        this.tree = tree;
        this.devices = devices;
    }

    public static OidRepository createDefault() {
        OidTree tree = new OidTree();
        for (Oid oid : SampleData.allOids()) {
            tree.insert(oid);
        }
        return new OidRepository(tree, SampleData.deviceProfiles());
    }

    public OidTree tree() {
        return tree;
    }

    public List<DeviceProfile> devices() {
        return devices;
    }
}
