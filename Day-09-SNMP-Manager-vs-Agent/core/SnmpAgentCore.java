import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SnmpAgentCore {
    private final String deviceName;
    private final List<MibObject> mibObjects;

    public SnmpAgentCore(String deviceName, List<MibObject> mibObjects) {
        this.deviceName = deviceName;
        this.mibObjects = new ArrayList<>(mibObjects);
        this.mibObjects.sort(Comparator.comparing(MibObject::oid));
    }

    public String deviceName() {
        return deviceName;
    }

    public MibObject get(String oid) {
        for (MibObject mibObject : mibObjects) {
            if (mibObject.oid().equals(oid)) {
                return mibObject;
            }
        }
        return null;
    }

    public Optional<MibObject> next(String oid) {
        for (MibObject mibObject : mibObjects) {
            if (mibObject.oid().compareTo(oid) > 0) {
                return Optional.of(mibObject);
            }
        }
        return Optional.empty();
    }

    public List<MibObject> browse() {
        return List.copyOf(mibObjects);
    }
}
