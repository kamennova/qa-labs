package models;

public class User {
    public Long id;
    public String email;
    public String passwordHash;

    User(Long id, String email, String hash){
        this.email = email;
        this.id = id;
        this.passwordHash = hash;
    }
}
