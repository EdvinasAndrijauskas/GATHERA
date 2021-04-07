package EdvianasAndrijauskas.GATHERA.ui.home;

public class EventCard {
    private String day;
    private String month;
    private int monthDay;
    private String time;
    private String eventName;
    private String description;
    private int howManyPeopleAreComing;
    private int imageId;

    public EventCard(String day, String month, String time, String eventName, String description, int howManyPeopleAreComing, int imageId, int monthDay) {
        this.day = day;
        this.month = month;
        this.time = time;
        this.eventName = eventName;
        this.description = description;
        this.howManyPeopleAreComing = howManyPeopleAreComing;
        this.imageId = imageId;
        this.monthDay = monthDay;
    }

    public int getMonthDay() {
        return monthDay;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
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

    public int getImageViewId() {
        return imageId;
    }

}

