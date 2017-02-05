package studios.inninc.startupai;

/**
 * Created by Pradyumna1 on 1/16/2017.
 */
public class EventContent {


    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventLoc;
    private String eventDesc;
    private String eventImage;

    private String eventId;
    private String eventLikes;

    private String eventCategory;

    private String rating;


    public EventContent() {
    }

    public EventContent(String eventName, String eventDate, String eventTime, String eventLoc, String eventDesc, String eventImage) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLoc = eventLoc;
        this.eventDesc = eventDesc;
        this.eventImage = eventImage;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventLoc() {
        return eventLoc;
    }

    public void setEventLoc(String eventLoc) {
        this.eventLoc = eventLoc;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventLikes() {
        return eventLikes;
    }

    public void setEventLikes(String eventLikes) {
        this.eventLikes = eventLikes;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
