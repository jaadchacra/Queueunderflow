package com.example.joseph.queueunderflow.basicpost.basicanswer;

import com.example.joseph.queueunderflow.basicpost.BasicPost;

import java.util.ArrayList;

/**
 * Created by josep on 2/28/2017.
 */
public class BasicAnswer extends BasicPost {


    // Is this the answer chosen by the question owner ?
    private boolean chosenAnswer;

    public BasicAnswer(String qOwner,String qTitle,String qDescription,String postId,boolean chosenAnswer){
        super(qOwner,qTitle,qDescription,postId);
        this.chosenAnswer = chosenAnswer;
    }
}
