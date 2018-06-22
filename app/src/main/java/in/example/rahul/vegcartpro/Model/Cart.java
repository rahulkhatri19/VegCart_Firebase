package in.example.rahul.vegcartpro.Model;

/**
 * Created by Rahul on 01-05-2018.
 */

public class Cart {
    private String Name;
    private String Price;
    private String Quantity;
    private String Address;

    public Cart() {
    }

    public Cart(String name, String price, String quantity, String address) {
        Name = name;
        Price = price;
        Quantity = quantity;
        Address = address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
