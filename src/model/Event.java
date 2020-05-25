public class Event extends Post {
    private String venue; //String to Store Venue of Event
    private String date; //String to store date of Event
    private int capacity; //Integer to store Capacity
    private int attendee_count; //Integer to store attendee count

    public Event() {
    }

    //Parametrized constructor to initialize attributes and create an "Event"
    public Event(String id, String title, String description, String venue, String date, int capacity, String creator_id) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id);
        this.venue = venue;
        this.date = date;
        this.capacity = capacity;
    }

    //Getters and Setters
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAttendee_count() {
        return attendee_count;
    }

    public void setAttendee_count(int attendee_count) {
        this.attendee_count = attendee_count;
    }

    //Override the getPostDetails() method of Super Class
    @Override
    public String getPostDetails() {
        String post_details = super.getPostDetails();  //To call the method from Super Class "Post"
        String event_details = post_details + "\nVenue:\t\t\t" + this.getVenue() + "\nDate:\t\t\t" + this.getDate() + "\nCapacity:\t\t" + this.getCapacity() + "\nAttendees:\t\t" + this.getAttendee_count();
        return event_details; //Contains All Details i.e Post Details + Event Details
    }

    //Implementation of Abstract method "handleReply" to handle Reply to an "Event"
    public boolean handleReply(Reply reply) {
        Boolean add_reply = false;
        //Condition to check if Reply is in "1" and Event is still "OPEN"
        if (reply.getValue() == 1)
            if (this.getStatus().equals("OPEN")) {
                //If attendees list is Empty , no need to check if "Student id" is already present
                if (this.getReplyList().size() == 0) {
                    add_reply = true;
                } else {
                    //If Student list not Empty, check if "student id" is already part of Attendee List
                    add_reply = true;
                    for (Reply iterator : this.getReplyList()) {
                        {
                            if (iterator.getResponder_id().equals(reply.getResponder_id())) {
                                System.out.println("User already part of Event!! ");
                                add_reply = false;
                                break;
                            }
                        }
                    }
                }
            } else {
                System.out.println("Event CLOSED!!");
            }
        else {
            System.out.println("Invalid input!!");
        }
        if (add_reply) {
            //All Criteria match , Adding current "Reply object" to ArrayList "ReplyList"
            this.getReplyList().add(reply);
            this.attendee_count++;
            //Condition to Close the event if Attendees count match the capacity
            if (this.attendee_count == this.capacity) {
                this.setStatus("CLOSE");
            }
            return true;
        }

        return false; //One or more criteria not passed, not able to add "student id" in attendee list


    }

    //Implementation of Abstract method "getReplyDetails" to display "Attendee List" of "Event Post"
    public String getReplyDetails() {
        String reply_details = "";
        if (this.getReplyList().size() == 0) {
            reply_details = "EMPTY";
        } else {
            for (Reply iterator : this.getReplyList()) {
                reply_details = reply_details + "," + iterator.getResponder_id();
            }

            reply_details = reply_details.substring(1);   //To Remove the first character "," from the String
        }
        return "Attendee list: \t" + reply_details;

    }


}
