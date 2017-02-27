package com.example.joseph.queueunderflow.basicpost.basicquestion;

import com.example.joseph.queueunderflow.basicpost.BasicPost;

import java.util.ArrayList;

/**
 * Created by josep on 2/28/2017.
 */
public class BasicQuestion extends BasicPost {


    private ArrayList<String> tags;

    public BasicQuestion(String qOwner,String qTitle,String qDescription,String postId,ArrayList<String>tags){
        super(qOwner,qTitle,qDescription,postId);
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

}
