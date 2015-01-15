package edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by david on 02/01/2015.
 */

public class MaverickRootAPI {

    private Map<String, Link> links;

    public MaverickRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }

}