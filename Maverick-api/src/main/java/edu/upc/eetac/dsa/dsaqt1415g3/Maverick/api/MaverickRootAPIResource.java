package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model.MaverickRootAPI;



@Path("/")
public class MaverickRootAPIResource {
	@GET
	public MaverickRootAPI getRootAPI() {
		MaverickRootAPI api = new MaverickRootAPI();
		return api;
	}
}