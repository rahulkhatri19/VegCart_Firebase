package in.example.rahul.vegcartpro.Model;

/**
 * Created by Rahul on 22-04-2018.
 */

public class AllFood {
    private String Image;
    private String Name;
    private String NameHindi;
    private String Price;
    private String Advt;
    private String Vam;
    private String Dis;
    private String Dat;

    public AllFood() {
    }

    public AllFood(String image, String name, String nameHindi, String price, String advt, String vam, String dis, String dat) {
        Image = image;
        Name = name;
        NameHindi = nameHindi;
        Price = price;
        Advt = advt;
        Vam = vam;
        Dis = dis;
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
}
