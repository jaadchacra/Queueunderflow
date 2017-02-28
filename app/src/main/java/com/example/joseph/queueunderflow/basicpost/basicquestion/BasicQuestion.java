package com.example.joseph.queueunderflow.basicpost.basicquestion;

import com.example.joseph.queueunderflow.basicpost.BasicPost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by josep on 2/28/2017.
 */
public class BasicQuestion extends BasicPost implements Serializable {



    private String qTitle;

    private ArrayList<String> tags;

    public BasicQuestion(String qOwner, String qTitle, String qDescription, String postId, Date postDate,ArrayList<String>tags){
        super(qOwner,qDescription,postId,postDate);
        this.qTitle = qTitle;
        this.tags = new ArrayList<>();
        for(int i=0;i<tags.size();i++){
            this.tags.add(tags.get(i));
        }
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getqTitle() {
        return qTitle;
    }

    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

}
