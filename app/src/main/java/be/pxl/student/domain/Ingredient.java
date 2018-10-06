package be.pxl.student.domain;


import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements Parcelable {


    private int id;
    private String name;
    private double ammount;
    private Product product;

    public Ingredient(String name, double ammount, Product product) {
        this.name = name;
        this.ammount = ammount;
        this.product = product;
    }

    public Ingredient(String name, double ammount) {
        this.name = name;
        this.ammount = ammount;
    }

    public Ingredient() {
    }

    public Ingredient(Parcel in) {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getName());
        dest.writeDouble(getAmmount());
        dest.writeInt(getId());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
