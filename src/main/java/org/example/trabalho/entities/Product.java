package org.example.trabalho.entities;

import javafx.beans.property.*;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty shortDescription;
    private StringProperty brand;
    private StringProperty category;
    private DoubleProperty listPrice;
    private DoubleProperty cost;

    public Product(String name, Double listPrice) {
        this.name = new SimpleStringProperty(name);
        this.listPrice = new SimpleDoubleProperty(listPrice);
    }

    public Product(String name, String shortDescription, String brand, String category, Double listPrice, Double cost) {
        this.name = new SimpleStringProperty(name);
        this.shortDescription = new SimpleStringProperty(shortDescription);
        this.brand = new SimpleStringProperty(brand);
        this.category = new SimpleStringProperty(category);
        this.listPrice = new SimpleDoubleProperty(listPrice);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public Product(Integer id,String name, String shortDescription, String brand, String category, Double listPrice, Double cost) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.shortDescription = new SimpleStringProperty(shortDescription);
        this.brand = new SimpleStringProperty(brand);
        this.category = new SimpleStringProperty(category);
        this.listPrice = new SimpleDoubleProperty(listPrice);
        this.cost = new SimpleDoubleProperty(cost);
    }

    public Integer getId() {
        return id.get();
    }

    public void setId(IntegerProperty id) {
        this.id = id;
    }

    public String getName() {
        // converter para string
        return name.get();
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription.get();
    }

    public void setShortDescription(StringProperty shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getBrand() {
        return brand.get();
    }

    public void setBrand(StringProperty brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(StringProperty category) {
        this.category = category;
    }

    public Double getListPrice() {
        return listPrice.get();
    }

    public void setListPrice(DoubleProperty listPrice) {
        this.listPrice = listPrice;
    }

    public Double getCost() {
        return cost.get();
    }

    public void setCost(DoubleProperty cost) {
        this.cost = cost;
    }



    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty shortDescriptionProperty() {
        return shortDescription;
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public DoubleProperty listPriceProperty() {
        return listPrice;
    }

    public DoubleProperty costProperty() {
        return cost;
    }
}
