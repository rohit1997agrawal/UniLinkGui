package model;

import java.util.ArrayList;

public abstract class Post {  //Abstract class , as it contains Abstract methods
    private String id;
    private String title;
    private String description;
    private String creator_id;
    private String status;
    private String image_name;
    private ArrayList<Reply> replyList = new ArrayList<Reply>(); //ArrayList of type "Reply" class , to store Replies of Post

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public Post() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //ToString used to export data to text file
    @Override
    public String toString() {
        return
                "id='" + id + '\'' +
                        "~ title='" + title + '\'' +
                        "~ description='" + description + '\'' +
                        "~ creator_id='" + creator_id + '\'' +
                        "~ status='" + status + '\'' +
                        "~ image_name='" + image_name + '\'' +
                        "~ replyList=" + replyList;

    }

    // Parameterized Constructor to be used by sub Classes to to initialize the attributes
    public Post(String id, String title, String description, String creator_id, String image_name) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator_id = creator_id;
        this.status = "OPEN";  //By Default Status is set to "OPEN"
        this.image_name = image_name;

    }

    // Getters and setters
    public String getCreator_id() {
        return creator_id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    //Abstract methods which are implemented in the subclasses Event/Job/Sale
    public abstract boolean handleReply(Reply reply);


}
