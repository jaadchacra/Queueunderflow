package com.example.joseph.queueunderflow.basicpost.basicanswer.imageanswer;

import com.example.joseph.queueunderflow.basicpost.basicanswer.BasicAnswer;

import java.util.ArrayList;

/**
 * Created by josep on 2/28/2017.
 */
public class ImageAnswer extends BasicAnswer {

    private ArrayList<String> imagesUri;

    public ImageAnswer(String qOwner,String qTitle,String qDescription,String postId,boolean chosenAnswer){
        super(qOwner,qTitle,qDescription,postId,chosenAnswer);
        for(int i=0;i<imagesUri.size();i++){
            this.imagesUri.add(imagesUri.get(i));
        }
    }

    public ArrayList<String> getImagesUri() {
        return imagesUri;
    }

    public void setImagesUri(ArrayList<String> imagesUri) {
        this.imagesUri = imagesUri;
    }
}
