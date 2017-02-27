package com.example.joseph.queueunderflow.basicpost.basicquestion.imagequestion;

import com.example.joseph.queueunderflow.basicpost.basicquestion.BasicQuestion;

import java.util.ArrayList;

/**
 * Created by josep on 2/28/2017.
 */
public class ImageQuestion extends BasicQuestion {

    private ArrayList<String> imagesUri;

    public ImageQuestion(String qOwner,String qTitle,String qDescription,String postId,ArrayList<String> tags,ArrayList<String>imagesUri){
        super(qOwner,qTitle,qDescription,postId,tags);
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
