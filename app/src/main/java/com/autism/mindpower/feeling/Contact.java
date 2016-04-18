package com.autism.mindpower.feeling;

/**
 * Created by Arash Nase on 4/17/2016.
 * Class representing a contact
 */
public class Contact {

    private String number = null;
    private String name = null;

    public Contact(String number, String name) {
        this.number = number;
        this.name = name;;
    }

    //getters and setters
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
