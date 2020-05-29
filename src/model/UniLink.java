package model;

import java.util.HashSet;
import java.util.Set;


public class UniLink {

    //Common Collection to store and update Posts data
    //Collection of Type Post to store Post objects(Event/Job/Sale)
    private  Set<Post> postCollection = new HashSet<>();

    public  Set<Post> getPostCollection() {
        return postCollection;
    }

    public  void setPostCollection(Set<Post> postCollection) {
        this.postCollection = postCollection;
    }

    //Function to generate Auto increment id for Event/Sale/Job (User input : Type of Post)
    public String generateAutoIncrementId(String type_post) {
        int current_max = 0;
        //Get current maximum id of selected type of Post
        for (Post iterator : postCollection) {

            if (iterator.getId().substring(0, 3).equals(type_post)) {
                int iterator_id = Integer.parseInt(iterator.getId().substring(3, 6));
                if (iterator_id > current_max) {
                    current_max = iterator_id;
                }
            }
        }
        //Generate new Id ,  by incrementing value by 1
        int new_id = ++current_max;
        String new_max_id = String.format("%03d", new_id);
        return type_post + new_max_id;
    }

}
