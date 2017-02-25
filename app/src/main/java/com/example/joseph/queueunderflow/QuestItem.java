package com.example.joseph.queueunderflow;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by josep on 2/19/2017.
 */
public class QuestItem implements Serializable {


    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    private boolean hasImage;

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    private Date postDate;

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    private ArrayList<String> tags;
    public String getqOwner() {
        return qOwner;
    }

    public void setqOwner(String qOwner) {
        this.qOwner = qOwner;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    private String qOwner;
    private String qTitle;

    public String getqDescription() {
        return qDescription;
    }

    public void setqDescription(String qDescription) {
        this.qDescription = qDescription;
    }

    private String qDescription;

    public String getQuestId() {
        return questId;
    }

    public void setQuestId(String questId) {
        this.questId = questId;
    }

    private String questId;

    public ArrayList<String> getImagesUri() {
        return imagesUri;
    }

    public void setImagesUri(ArrayList<String> imagesUri) {
        this.imagesUri = imagesUri;
    }

    private ArrayList<String> imagesUri;

    public QuestItem() {
        this.imagesUri = new ArrayList<>();
    }








}
