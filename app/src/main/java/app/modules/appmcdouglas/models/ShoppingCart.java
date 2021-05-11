package app.modules.appmcdouglas.models;

import java.io.Serializable;

public class ShoppingCart implements Serializable {
    private String keyuser;
    private String keyfood;
    String key;

    public ShoppingCart(){

    }

    public ShoppingCart(String keyuser, String keyfood) {
        this.keyuser = keyuser;
        this.keyfood = keyfood;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyuser() {
        return keyuser;
    }

    public void setKeyuser(String keyuser) {
        this.keyuser = keyuser;
    }

    public String getKeyfood() {
        return keyfood;
    }

    public void setKeyfood(String keyfood) {
        this.keyfood = keyfood;
    }
}
