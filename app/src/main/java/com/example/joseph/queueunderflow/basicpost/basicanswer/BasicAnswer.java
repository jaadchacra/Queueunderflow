package com.example.joseph.queueunderflow.basicpost.basicanswer;

import com.example.joseph.queueunderflow.basicpost.BasicPost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by josep on 2/28/2017.
 */
public class BasicAnswer extends BasicPost implements Serializable {


    // Is this the answer chosen by the question owner ?
    private boolean chosenAnswer;

    public BasicAnswer(String qOwner, String qDescription, String postId, Date postDate,boolean chosenAnswer){
        super(qOwner,qDescription,postId,postDate);
        this.chosenAnswer = chosenAnswer;
    }
}
