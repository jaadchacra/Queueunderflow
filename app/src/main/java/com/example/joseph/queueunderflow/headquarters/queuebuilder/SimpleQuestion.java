package com.example.joseph.queueunderflow.headquarters.queuebuilder;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by josep on 2/12/2017.
 */
public class SimpleQuestion {

    private String title;
    private String owner;
    private String description;

    public boolean isNoPicture() {
        return noPicture;
    }

    public void setNoPicture(boolean noPicture) {
        this.noPicture = noPicture;
    }

    boolean noPicture;

    public ArrayList<Uri> getImagesQ() {
        return imagesQ;
    }

    public void setImagesQ(ArrayList<Uri> imagesQ) {
        this.imagesQ = imagesQ;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    private ArrayList<Uri> imagesQ;


}



