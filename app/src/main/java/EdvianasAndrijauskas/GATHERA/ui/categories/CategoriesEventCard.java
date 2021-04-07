package EdvianasAndrijauskas.GATHERA.ui.categories;

public class CategoriesEventCard {
    private int imageId;
    private String textView;
    private int iconId;

    public CategoriesEventCard(int imageId, String textView, int iconId) {
        this.imageId = imageId;
        this.textView = textView;
        this.iconId = iconId;
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
