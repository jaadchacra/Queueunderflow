package com.example.joseph.queueunderflow.cardpage;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.joseph.queueunderflow.QuestItem;
import com.example.joseph.queueunderflow.QuestRecycler;
import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.authentication.IntroPage;
import com.example.joseph.queueunderflow.headquarters.QuestionsList;
import com.example.joseph.queueunderflow.headquarters.skills.SkillLoader;
import com.example.joseph.queueunderflow.viewpager.ViewPagerAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardPage extends AppCompatActivity  {

    @BindView(R.id.postlv)
    RecyclerView poslv;


    private LinearLayoutManager mLinearLayoutManager;
    private PostRecycler mAdapter;


    private ArrayList<QuestItem> items;

    private String  questId;
    private String  owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_page);
        ButterKnife.bind(this);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.statusBar));
        }


        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            items = (ArrayList<QuestItem>) extras.getSerializable("items");
        }


        mLinearLayoutManager = new LinearLayoutManager(this);
        poslv.setLayoutManager(mLinearLayoutManager);

        mAdapter = new PostRecycler(CardPage.this,items,mAdapter);

        poslv.setAdapter(mAdapter);


    }



    private class LoadPost extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            ParseQuery fetchUser = new ParseQuery("Answers");
            fetchUser.whereEqualTo("posterid", items.get(0).getPostId());
            fetchUser.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(java.util.List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject userData : objects) {





                        }


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
