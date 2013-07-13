package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * This is a helper class to represent LabelGroup in NML
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class LabelGroup {
    private NetworkObject networkObject;

    public LabelGroup(NetworkObject networkObject) {
        this.networkObject = networkObject;
    }

    /**
     * Returns the NetworkObject instance that this label group is bound to
     *
     * @return
     */
    public NetworkObject getNetworkObject() {
        return this.networkObject;
    }

    /**
     * Adds a technology label
     *
     * @param labelType URI to refer to a technology-specific labelset, e.g. a URI for VLANs
     * @param labelValue specific value taken from the labelset, e.g. a VLAN number
     */
    public void addLabel(String labelType, String labelValue) {
        if (this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP) == null) {
            this.networkObject.add(ReservedKeys.RECORD_LABEL_GROUP, new ArrayList<String>());
        }
        if (this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE) == null) {
            this.networkObject.add(ReservedKeys.RECORD_LABEL_GROUP_TYPE, new ArrayList<String>());
        }

        ((List<String>) this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE)).add(labelType);
        ((List<String>) this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP)).add(labelValue);
    }

    /**
     * Get the value of specific label index
     *
     * @param index
     * @return
     */
    public String getValue(int index) {
        List<String> label = getLabel(index);
        if (label == null) {
            return null;
        }
        return label.get(1);
    }

    /**
     * Get the type of specific label index
     *
     * @param index
     * @return
     */
    public String getLabelType(int index) {
        List<String> label = getLabel(index);
        if (label == null) {
            return null;
        }
        return label.get(0);
    }

    /**
     * Return a pair of (labeltype, labelvalue)
     *
     * @param index
     * @return
     */
    public List<String> getLabel(int index) {
        if (this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP) == null ||
                this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE) == null) {
            return null;
        }
        List<String> types = (List<String>) this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE);
        if (types.size() <= index) {
            return null;
        }
        List<String> values = (List<String>) this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP);
        if (values.size() <= index) {
            return null;
        }
        if (values.size() != types.size()) {
            return null;
        }
        List<String> label = new ArrayList<String>();
        label.add(types.get(index));
        label.add(values.get(index));
        return label;
    }

    /**
     * Return a list of all labels
     * warning: this list is unmodifiable
     *
     * @return
     */
    public List<List<String>> getLabels() {
        if (this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP) == null ||
                this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE) == null) {
            return null;
        }
        if (this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP) == null ||
                this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE) == null) {
            return null;
        }
        List<String> types = (List<String>) this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP_TYPE);
        List<String> values = (List<String>) this.networkObject.getValue(ReservedKeys.RECORD_LABEL_GROUP);
        if (values.size() != types.size()) {
            return null;
        }
        List<List<String>> labels = new ArrayList<List<String>>();
        for (int i = 0; i < types.size(); i++) {
            if (getLabel(i) == null)
                break;
            labels.add(getLabel(i));
        }
        return unmodifiableList(labels);
    }
}
