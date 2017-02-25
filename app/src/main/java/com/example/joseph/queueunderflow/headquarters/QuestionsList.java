package com.example.joseph.queueunderflow.headquarters;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;

import com.example.joseph.queueunderflow.QuestItem;
import com.example.joseph.queueunderflow.QuestRecycler;
import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.headquarters.skills.SkillLoader;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionsList extends AppCompatActivity {

    @BindView(R.id.questlv)
    RecyclerView questlv;


    private LinearLayoutManager mLinearLayoutManager;
    private QuestRecycler mAdapter;

    private ArrayList<QuestItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_list);
        ButterKnife.bind(this);



        mLinearLayoutManager = new LinearLayoutManager(this);
        questlv.setLayoutManager(mLinearLayoutManager);

        new LoadQuests().execute();


    }


    private class LoadQuests extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            items = new ArrayList<>();

            ParseQuery fetchQuests = new ParseQuery("Questions");
            fetchQuests.orderByDescending("createdAt");
            fetchQuests.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(java.util.List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject userData : objects) {

                            QuestItem item = new QuestItem();

                           String title = userData.getString("title");
                            Date postDate = userData.getCreatedAt();

                            String owner = "#";

                            owner += userData.getString("owner");


                            ArrayList<String> tags = (ArrayList<String>) userData.get("tags");


                            ArrayList<String> images = new ArrayList<String>();

                            ParseFile qImage = (ParseFile) userData.get("image1");
                            if(qImage == null){

                                // images.add(Uri.parse("https://s24.postimg.org/4t25k2v7p/flowiboss.png"));
                                item.setHasImage(false);
                            }else{
                                String imageUrl = qImage.getUrl() ;//live url


                                images.add(imageUrl);

                                item.setHasImage(true);
                                qImage = (ParseFile) userData.get("image2");

                                if(qImage == null){
                                    //Do nothing
                                }else{
                                    imageUrl = qImage.getUrl() ;//live url


                                    images.add(imageUrl);


                                    qImage = (ParseFile) userData.get("image3");

                                    if(qImage == null){

                                    }else{
                                        imageUrl = qImage.getUrl() ;//live url

                                        images.add(imageUrl);
                                    }


                                }
                            }


                            String description = userData.getString("description");

                            item.setTags(tags);
                            item.setPostDate(postDate);
                            item.setqDescription(description);
                            item.setqOwner(owner);
                            item.setqTitle(title);
                            if(item.isHasImage()){
                                item.setImagesUri(images);
                            }


                            items.add(item);


                        }

                        mAdapter = new QuestRecycler(QuestionsList.this,items);

                        questlv.setAdapter(mAdapter);

                    }
                }

            });


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml


        }
    }

}
