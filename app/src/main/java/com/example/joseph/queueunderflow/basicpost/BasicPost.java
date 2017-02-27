package com.example.joseph.queueunderflow.basicpost;

import java.util.Date;

/**
 * Created by josep on 2/28/2017.
 */
abstract public class BasicPost {
    private String qOwner;
    private String qTitle;
    private String qDescription;
    private String postId;


    private Date postDate;




    //Default Constructor
    public BasicPost(){
        qOwner = "";
        qTitle = "";
        qDescription = "";
        postId = "";
    }

    public BasicPost(String qOwner,String qTitle,String qDescription,String postId){
        this.qOwner = qOwner;
        this.qTitle = qTitle;
        this.qDescription = qDescription;
        this.postId = postId;
    }


    /********************************************************Getters And Setters ********************************************************/
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

    public String getqDescription() {
        return qDescription;
    }

    public void setqDescription(String qDescription) {
        this.qDescription = qDescription;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }


    /********************************************************Getters And Setters ********************************************************/



}
