package app.modules.appmcdouglas.models;

public class Food {
    private String key;
    private String name;
    private String category;
    private String tokenimg;
    private String description;
    private String price;

    public Food(){

    }

    public Food(String key, String name, String category, String tokenimg, String description, String price) {
        this.key = key;
        this.name = name;
        this.category = category;
        this.tokenimg = tokenimg;
        this.description = description;
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTokenimg() {
        return tokenimg;
    }

    public void setTokenimg(String tokenimg) {
        this.tokenimg = tokenimg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
