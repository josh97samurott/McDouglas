package app.modules.appmcdouglas.usermanagement;

public class User {
    private String key;
    private String email;
    private String name;
    private String lastname;
    private String phone;

    public User(){
    }

    public User(String key, String email, String name, String lastname, String phone) {
        this.key = key;
        this.email = email;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
