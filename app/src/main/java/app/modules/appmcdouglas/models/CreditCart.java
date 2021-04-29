package app.modules.appmcdouglas.models;

public class CreditCart {
    private String keyuser;
    private String numcard;
    private String duedate;
    private String cardholder;
    private String carcode;
    private String address;

    public CreditCart(){

    }

    public CreditCart(String keyuser, String numcard, String duedate, String cardholder, String carcode, String address) {
        this.keyuser = keyuser;
        this.numcard = numcard;
        this.duedate = duedate;
        this.cardholder = cardholder;
        this.carcode = carcode;
        this.address = address;
    }

    public String getKeyuser() {
        return keyuser;
    }

    public void setKeyuser(String keyuser) {
        this.keyuser = keyuser;
    }

    public String getNumcard() {
        return numcard;
    }

    public void setNumcard(String numcard) {
        this.numcard = numcard;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
