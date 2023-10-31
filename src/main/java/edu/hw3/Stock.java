package edu.hw3;

public class Stock {
    public Stock(Integer price, final String companyName) {
        this.price = price;
        this.companyName = companyName;
    }

    public Stock(final String companyName) {
        this(0, companyName);
    }

    public Integer getPrice() {
        return price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void changeCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private Integer price;
    private String companyName;
}
