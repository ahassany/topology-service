package net.es.topology.common.records.ts;

import net.es.topology.common.records.ts.keys.ReservedKeys;
import net.es.topology.common.records.ts.keys.ReservedValues;

import java.util.List;

/**
 * The NSI framework contains many different services,
 * the details of these can be described using a Service object.
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 * @see: Network Service Interface Topology Representation
 */
public class NSIService extends NetworkObject {
    public NSIService() {
        super(ReservedValues.RECORD_TYPE_NSI_SERVICE);
    }

    /**
     * To allow extending this class with custom record type
     *
     * @param recordType
     */
    public NSIService(String recordType) {
        super(recordType);
    }

    /**
     * The URL endpoint for the service
     *
     * @return URL
     */
    public String getLink() {
        return (String) this.getValue(ReservedKeys.RECORD_NSI_LINK);
    }

    /**
     * The URL endpoint for the service
     *
     * @param link URL
     */
    public void setLink(String link) {
        this.add(ReservedKeys.RECORD_NSI_LINK, link);
    }

    /**
     * NSI Service type
     * <p/>
     * The values of the type attribute should be specified in a Grid Forum Documents (GFD)
     *
     * @return URI
     */
    public String getType() {
        return (String) this.getValue(ReservedKeys.RECORD_NSI_SERVICE_TYPE);
    }

    /**
     * NSI Service type
     * <p/>
     * The values of the type attribute should be specified in a Grid Forum Documents (GFD)
     *
     * @param type URI
     */
    public void setType(String type) {
        this.add(ReservedKeys.RECORD_NSI_SERVICE_TYPE, type);
    }

    /**
     * Point to a WADL file that describes the service
     *
     * @return URI of the WADL file
     */
    public String getDescribedBy() {
        return (String) this.getValue(ReservedKeys.RECORD_NSI_DESCRIBED_BY);
    }

    /**
     * Point to a WADL file that describes the service
     *
     * @param uri URI of the WADL file
     */
    public void setDescribedBy(String uri) {
        this.add(ReservedKeys.RECORD_NSI_DESCRIBED_BY, uri);
    }

    /**
     * NSAs that provide this service
     *
     * @return list of the URNs of NSAs.
     */
    public List<String> getProvidedBy() {
        return (List<String>) this.getValue(ReservedKeys.RECORD_RELATION_PROVIDED_BY);
    }

    /**
     * NSAs that provide this service
     *
     * @param urns list of the URNs of NSAs.
     */
    public void setProvidedBy(List<String> urns) {
        this.add(ReservedKeys.RECORD_RELATION_PROVIDED_BY, urns);
    }

}
