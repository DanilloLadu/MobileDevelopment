package be.pxl.student.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Product implements Parcelable {


    private int id;
    private String name;
    private Set<Ingredient> ingredientList =  new HashSet<Ingredient>();


    public Product() {
    }

    public Product(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(Parcel in) {
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

    public Set<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(Set<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeList(new ArrayList<>(getIngredientList()));
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public String toString() {
        return this.getName();
    }
}
