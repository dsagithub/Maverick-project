package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MaverickApplication extends ResourceConfig {
	public MaverickApplication() {
		super();
		register(DeclarativeLinkingFeature.class); //registra modulos, registra que quiere utilizar jersey
	}
}