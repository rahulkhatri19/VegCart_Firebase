package in.example.rahul.vegcartpro.Model;


/**
 * Created by Rahul on 12-03-2018.
 */

public class FoodData {
    private String Image;
    private String Name;
    private String NameHindi;
    private String Advt;
    private String Vam;
    private String Dis;
    private String Dat;
    private String Price;

    public FoodData() {
    }

    public FoodData(String image, String name, String nameHindi, String advt, String vam, String dis, String dat, String price) {
        Image = image;
        Name = name;
        NameHindi = nameHindi;
        Advt = advt;
        Vam = vam;
        Dis = dis;
        Dat = dat;
        Price = price;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getAdvt() {
        return Advt;
    }

    public void setAdvt(String advt) {
        Advt = advt;
    }

    public String getVam() {
        return Vam;
    }

    public void setVam(String vam) {
        Vam = vam;
    }

    public String getDis() {
        return Dis;
    }

    public void setDis(String dis) {
        Dis = dis;
    }

    public String getDat() {
        return Dat;
    }

    public void setDat(String dat) {
        Dat = dat;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNameHindi() {
        return NameHindi;
    }

    public void setNameHindi(String nameHindi) {
        NameHindi = nameHindi;
    }
}
