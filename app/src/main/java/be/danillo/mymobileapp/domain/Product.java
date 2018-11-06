package be.danillo.mymobileapp.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Product {


    private int id;
    private String name;
    private List<Ingredient> ingredientList =  new ArrayList<>();


    public Product() {
    }

    public Product(int id, String name, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredients;
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

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }


}
