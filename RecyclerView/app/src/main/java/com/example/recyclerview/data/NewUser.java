package com.example.recyclerview.data;

public class NewUser {
    private String name;
    private int id;
    private String email;
    private String about;
    private boolean isChecked;

    public NewUser(String name, int id, String email, String about) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.about = about;
    }

    public NewUser(){}


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAbout() {
        return about;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
