package models;

import java.util.Date;

public class Dog {
    private Long id;

    private String name;
    private Date birthDate;
    private String breed;

    public Dog(String name, String breed){
        this.name = name;
        this.breed = breed;
    }

    public Long getId(){
        return this.id;
    }

    public String getBreed(){
        return this.breed;
    }

    public String getName(){
        return this.name;
    }

    public Date getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }
}
