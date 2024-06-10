class Product {
    constructor(id, name, shortDescription, brand, category, listPrice, cost) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.brand = brand;
        this.category = category;
        this.listPrice = listPrice;
        this.cost = cost;
    }

    // Métodos getters e setters simplificados
    getId() {
        return this.id;
    }

    setId(id) {
        this.id = id;
    }

    getName() {
        return this.name;
    }

    setName(name) {
        this.name = name;
    }

    getShortDescription() {
        return this.shortDescription;
    }

    setShortDescription(shortDescription) {
        this.shortDescription = shortDescription;
    }

    getBrand() {
        return this.brand;
    }

    setBrand(brand) {
        this.brand = brand;
    }

    getCategory() {
        return this.category;
    }

    setCategory(category) {
        this.category = category;
    }

    getListPrice() {
        return this.listPrice;
    }

    setListPrice(listPrice) {
        this.listPrice = listPrice;
    }

    getCost() {
        return this.cost;
    }

    setCost(cost) {
        this.cost = cost;
    }
}

module.exports = Product;
