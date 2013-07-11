package net.es.topology.common.config;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Helper class to create the XML JAXB marshallers
 *
 * @author <a href="mailto:a.hassany@gmail.com">Ahmed El-Hassany</a>
 */
public class JAXBConfig {
    public final static String nmlPackage = "org.ogf.schemas.nml._2013._05.base";
    public final static String nsiPackage = "org.ogf.schemas.nsi._2013._09.topology";
    public final static String nsiMessagingPackage = "org.ogf.schemas.nsi._2013._09.messaging";
    public final static String jaxbBindings = nmlPackage + ":" + nsiPackage + ":" + nsiMessagingPackage;
    public final static String nmlNamespace = "http://schemas.ogf.org/nml/2013/05/base#";
    public final static String nsiNamespace = "http://schemas.ogf.org/nsi/2013/09/topology#";
    public final static String nsiMessagingNamespace = "http://schemas.ogf.org/nsi/2013/09/messaging#";
    public final static String nmlPrefix = "nml";
    public final static String nsiPrefix = "nsi";
    public final static String nsiMessagingPrefix = "nsiMsg";

    /**
     * Creates new instance of JAXB marshaller
     *
     * @return
     * @throws JAXBException
     */
    public static Marshaller getMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(jaxbBindings);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        // Just to make the namespace prefixes prettier in the output xml
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new NamespacePrefixMapper() {
            @Override
            public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
                if (namespaceUri.equalsIgnoreCase(nmlNamespace))
                    return nmlPrefix;
                else if (namespaceUri.equalsIgnoreCase(nsiNamespace))
                    return nsiPrefix;
                else if (namespaceUri.equalsIgnoreCase(nsiMessagingNamespace))
                    return nsiMessagingPrefix;
                else
                    return suggestion;
            }
        });

        return marshaller;
    }

    /**
     * Creates new instance of JAXB Unmarshaller
     *
     * @return
     * @throws JAXBException
     */
    public static Unmarshaller getUnMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(jaxbBindings);
        return context.createUnmarshaller();
    }
}
