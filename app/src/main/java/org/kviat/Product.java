package org.kviat;

@GenerateClass(className = "ProductGenerated", packageName = "org.kviat.generated", fields = {"String name", "String madeBy", "int price"})
public class Product {


    private String name;
    private String madeBy;

    private double price;

    public Product(String name, String madeBy, double price){
        this.name = name;
        this.madeBy = madeBy;
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public String getMadeBy() {
        return madeBy;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMadeBy(String madeBy) {
        this.madeBy = madeBy;
    }

    @ValidatePrice(min = 0.01)
    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", madeBy='" + madeBy + '\'' +
                ", price=" + price +
                '}';
    }
}
