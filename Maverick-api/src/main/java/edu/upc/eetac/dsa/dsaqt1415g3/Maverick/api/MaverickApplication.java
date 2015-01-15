package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class MaverickApplication extends ResourceConfig {
	public MaverickApplication() {
		super();
		register(DeclarativeLinkingFeature.class); // registra modulos, registra
													// que quiere utilizar
													// jersey
		register(MultiPartFeature.class);
		ResourceBundle bundle = ResourceBundle.getBundle("application");

		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			property(key, bundle.getObject(key));
		}
	}
}