package edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 02/01/2015.
 */
public class UsersCollection {
    private List<User> users;
    private Map<String, Link> links = new HashMap<String, Link>();
    public UsersCollection() {
        super();
        users = new ArrayList<User>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(User user) {
        users.add(user);
    }



    public Map<String, Link> getLinks() {
        return links;
    }
}
