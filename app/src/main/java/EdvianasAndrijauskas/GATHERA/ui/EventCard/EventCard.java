package EdvianasAndrijauskas.GATHERA.ui.EventCard;

import android.net.Uri;

import java.net.URI;

public class EventCard {
    private String id;
    private int peopleAttending;
    private String date;
    private String time;
    private String eventName;
    private String description;
    private int howManyPeopleAreComing;
    private String image;
    private String category;
    private String userId;
    private String location;

    public EventCard(int peopleAttending, String location,String userId,String date, String category, String time, String eventName, String description, int howManyPeopleAreComing, String image) {
        this.peopleAttending = peopleAttending;
        this.location = location;
        this.date = date;
        this.time = time;
        this.eventName = eventName;
        this.description = description;
        this.howManyPeopleAreComing = howManyPeopleAreComing;
        this.image = image;
        this.category = category;
        this.userId = userId;
    }
    public EventCard()
    {

    }

    public int getPeopleAttending() {
        return peopleAttending;
    }

    public void setPeopleAttending(int peopleAttending) {
        this.peopleAttending = peopleAttending;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getTime() {
        return time;
    }

    public String getEventName() { return eventName; }

    public String getDescription() {
        return description;
    }

    public int getHowManyPeopleAreComing() {
        return howManyPeopleAreComing;
    }


    public void setTime(String time) {
        this.time = time;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHowManyPeopleAreComing(int howManyPeopleAreComing) {
        this.howManyPeopleAreComing = howManyPeopleAreComing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

