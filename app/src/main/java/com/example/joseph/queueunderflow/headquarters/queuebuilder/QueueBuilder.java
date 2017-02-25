package com.example.joseph.queueunderflow.headquarters.queuebuilder;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.joseph.queueunderflow.headquarters.MainPage;
import com.example.joseph.queueunderflow.headquarters.skills.SkillLoader;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by josep on 2/12/2017.
 */
public class QueueBuilder {


    private int currPosition;
    private Context mContext;
    public QueueBuilder(MainPage MainPage,ArrayList<String>userSkills){
        mContext = MainPage;
        this.userSkills = userSkills;
        currPosition = 0;
        questionList = new ArrayList<>();

    }

    public int getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(int currPosition) {
        this.currPosition = currPosition;
    }

    public ArrayList<String> getUserSkills() {
        return userSkills;
    }

    public void setUserSkills(ArrayList<String> userSkills) {
        this.userSkills = userSkills;
    }

    private ArrayList<String> userSkills;

    public ArrayList<SimpleQuestion> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<SimpleQuestion> questionList) {
        this.questionList = questionList;
    }

    private ArrayList<SimpleQuestion> questionList;

    public void buildQueue(){
        ParseQuery fetchQuestions = new ParseQuery("Questions");
        fetchQuestions.whereNotEqualTo("owner", ParseUser.getCurrentUser().getUsername());
        fetchQuestions.whereContainedIn("tags", userSkills);

        fetchQuestions.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(java.util.List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject userData : objects) {

                      SimpleQuestion question = new SimpleQuestion();
                        ArrayList<Uri> images = new ArrayList<Uri>();

                        ParseFile qImage = (ParseFile) userData.get("image1");
                        if(qImage == null){

                            // images.add(Uri.parse("https://s24.postimg.org/4t25k2v7p/flowiboss.png"));
                            question.setNoPicture(true);
                        }else{
                            String imageUrl = qImage.getUrl() ;//live url
                            Uri imageUri = Uri.parse(imageUrl);

                            images.add(imageUri);

                            question.setNoPicture(false);
                            qImage = (ParseFile) userData.get("image2");

                            if(qImage == null){
                                //Do nothing
                            }else{
                                imageUrl = qImage.getUrl() ;//live url
                                imageUri = Uri.parse(imageUrl);

                                images.add(imageUri);


                                qImage = (ParseFile) userData.get("image3");

                                if(qImage == null){

                                }else{
                                    imageUrl = qImage.getUrl() ;//live url
                                    imageUri = Uri.parse(imageUrl);

                                    images.add(imageUri);
                                }


                            }
                        }



                        question.setImagesQ(images);
                        question.setTitle(userData.getString("title"));
                        question.setOwner(userData.getString("owner"));
                        question.setDescription(userData.getString("description"));


                        questionList.add(question);


                    }

                    ((MainPage) mContext).setQuestion(questionList);


                }

            }

        });

    }
}
