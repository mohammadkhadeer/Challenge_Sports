package com.example.model.odds;

public class OddsCompanyComp {
    String name;
    int image_path,id;

    public OddsCompanyComp(String name, int image_path, int id) {
        this.name = name;
        this.image_path = image_path;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage_path() {
        return image_path;
    }

    public void setImage_path(int image_path) {
        this.image_path = image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
