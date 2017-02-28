package com.example.joseph.queueunderflow.basicpost.basicanswer.imageanswer;

import com.example.joseph.queueunderflow.basicpost.basicanswer.BasicAnswer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by josep on 2/28/2017.
 */
public class ImageAnswer extends BasicAnswer implements Serializable {

    private ArrayList<String> imagesUri;

    public ImageAnswer(String qOwner, String qDescription, String postId, Date postDate,ArrayList<String>imagesUri,boolean chosenAnswer){
        super(qOwner,qDescription,postId,postDate,chosenAnswer);
        this.imagesUri = new ArrayList<>();
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
