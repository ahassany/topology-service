package net.es.topology.service;


import net.es.topology.resources.RootResource;
import org.atmosphere.nettosphere.Config;
import org.atmosphere.nettosphere.Nettosphere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Invoker {
    private static final Logger logger = LoggerFactory.getLogger(Nettosphere.class);

    public static void main(String[] args) throws IOException {

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

