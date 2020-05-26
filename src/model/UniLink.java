package model;

import java.util.HashSet;
import java.util.Set;


public class UniLink {

    //Common Collection to store and update Posts data
    //Collection of Type Post to store Post objects(Event/Job/Sale)
    private static Set<Post> postCollection = new HashSet<>();

    public static Set<Post> getPostCollection() {
        return postCollection;
    }

    public static void setPostCollection(Set<Post> postCollection) {
        UniLink.postCollection = postCollection;
    }
}
