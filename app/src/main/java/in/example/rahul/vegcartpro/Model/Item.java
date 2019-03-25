package in.example.rahul.vegcartpro.Model;

/**
 * Created by Rahul on 19-03-2018.
 */

public class Item {
    private int imageId;
    private String title;
    private String subtitle;
    public Item(Integer imageId, String title, String subtitle){
        this.imageId=imageId;
        this.title=title;
        this.subtitle=subtitle;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
