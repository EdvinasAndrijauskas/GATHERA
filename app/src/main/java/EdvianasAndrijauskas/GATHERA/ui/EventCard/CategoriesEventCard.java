package EdvianasAndrijauskas.GATHERA.ui.EventCard;

public class CategoriesEventCard {
    private int imageId;
    private String textView;
    private int iconId;
    private boolean isFav;

    public CategoriesEventCard(int imageId, String textView, int iconId) {
        this.imageId = imageId;
        this.textView = textView;
        this.iconId = iconId;
        this.isFav = false;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isFav() {
        return isFav;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
