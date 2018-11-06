package be.danillo.mymobileapp.domain;



public class Ingredient{


    private int id;
    private String name;
    private double ammount;
    private Product product;

    public Ingredient(int id ,String name, double ammount) {
        this.id = id;
        this.name = name;
        this.ammount = ammount;
    }

    public Ingredient(String name, double ammount) {
        this.name = name;
        this.ammount = ammount;
    }

    public Ingredient() {
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

}
