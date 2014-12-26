package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;

import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MaverickRootAPIResource;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.MediaType;
import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.UserResource;

public class MaverickRootAPI {
	@InjectLinks({ //nos devuelve los primeros enlaces a partir de los cuales evoluciona la aplicacion
		@InjectLink(resource = MaverickRootAPIResource.class, style = Style.ABSOLUTE, rel = "self bookmark home", title = "Maverick Root API", method = "getRootAPI"),
		@InjectLink(resource = UserResource.class, style = Style.ABSOLUTE, rel = "users", title = "create user", type = MediaType.MAVERICK_API_USER)
		 })
	private List<Link> links; //atributo con getters y setters
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
