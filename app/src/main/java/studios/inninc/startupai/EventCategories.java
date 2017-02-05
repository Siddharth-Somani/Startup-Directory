package studios.inninc.startupai;

public class EventCategories {

    private String eventName;
    private Integer thumbnail;

    public EventCategories() {
    }

    public EventCategories(String eventName, Integer thumbnail) {
        this.eventName = eventName;
        this.thumbnail = thumbnail;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Integer thumbnail) {
        this.thumbnail = thumbnail;
    }
}
