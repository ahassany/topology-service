package net.es.topology.service;


import net.es.topology.common.config.sls.JsonClientProvider;
import net.es.topology.resources.RootResource;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Nettosphere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;


public class Invoker {
    private static final Logger logger = LoggerFactory.getLogger(Nettosphere.class);

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Invalid command line, exactly one argument required for the sls.json");
            System.exit(1);
        }

        // Check the file is loadable
        try {
            FileInputStream fstream = new FileInputStream(args[0]);
        } catch (Exception e) {
            System.err.println("Cannot load configuration file: " + args[0]);
            System.exit(1);
        }

        // configure the sLS client
        JsonClientProvider clientProvider = JsonClientProvider.getInstance();
        String slsConfigFilename = args[0];
        clientProvider.setFilename(slsConfigFilename);

        /*
         * A simple way to embed netty withing the code
		 * This handles one resource only, there is other
		 * way to handle multiple resources but I haven't
		 * had time to explore them.
		 */
        Nettosphere server = new Nettosphere.Builder().config(
                new Config.Builder()
                        .host("0.0.0.0")
                        .port(8080)
                        .resource(RootResource.class)
                        .build())
                .build();
        server.start();
    }
}


