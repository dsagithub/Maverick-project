package edu.upc.eetac.dsa.dsaqt1415g3.Maverick.api.model;

import java.util.List;

import javax.ws.rs.core.Link;

public class MaverickRootAPI {
	private List<Link> links; //atributo con getters y setters
	 
	public List<Link> getLinks() {
		return links;
	}
 
	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
