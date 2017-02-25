package com.example.joseph.queueunderflow.headquarters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.authentication.IntroPage;
import com.example.joseph.queueunderflow.authentication.askquestion.AskQuestionMain;
import com.example.joseph.queueunderflow.headquarters.queuebuilder.QueueBuilder;
import com.example.joseph.queueunderflow.headquarters.queuebuilder.SimpleQuestion;
import com.example.joseph.queueunderflow.headquarters.skills.Skill;
import com.example.joseph.queueunderflow.headquarters.skills.SkillLoader;
import com.example.joseph.queueunderflow.headquarters.skills.SkillsRecycler;
import com.example.joseph.queueunderflow.viewpager.ViewPagerAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPage extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {





    @BindView(R.id.pager_introduction)
    ViewPager intro_images;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mViewPagerAdapter;

    @BindView(R.id.spin_kit)
    SpinKitView spin_kit;
    @BindView(R.id.searchingTxT)
    TextView searchingTxt;

    @BindView(R.id.logOutBtn)
    TextView logoutBtn;

    @BindView(R.id.qOwner)
    TextView qOwner;
    @BindView(R.id.qTitle)
    TextView qTitle;
    @BindView(R.id.qDescription)
    TextView qDescription;
    @BindView(R.id.nextBtn)
    ImageView nextBtn;
    @BindView(R.id.backwardBtn)
    ImageView backwardBtn;
    @BindView(R.id.askBtn)
    TextView askBtn;

    private ArrayList<String> listSkill;

    private QueueBuilder queueBuilder;

    /*************************************************Dialog properties ***********************************************************/

    SkillLoader skillLoader;
    private boolean firstTime = false;


    /*************************************************Dialog properties ***********************************************************/



    private int[] mImageResources = {
            R.drawable.searchico,
            R.drawable.addbtn,
            R.drawable.donepick,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            firstTime = extras.getBoolean("firstTime");

        }

        setVisSpin();


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(MainPage.this, IntroPage.class);
                startActivity(intent);

            }
        });

        askBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, AskQuestionMain.class);

                startActivity(intent);
            }
        });



        if(firstTime) {
           suggestSkills();
        }else{
            new LoadUserSkills().execute();
        }



    }



    private void setUiPageViewController() {

        pager_indicator.removeAllViews();
        dotsCount = mViewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void suggestSkills(){
        new LoadUserSkills().execute();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View v = getWindow().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
        }
    return true;
    }


    public void setQuestion(final ArrayList<SimpleQuestion> questionList){
        final SimpleQuestion currQ = questionList.get(queueBuilder.getCurrPosition());
        mViewPagerAdapter = new ViewPagerAdapter(MainPage.this, currQ.getImagesQ(),false);

        intro_images.setAdapter(mViewPagerAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);

        setInVisSpin();


        setUiPageViewController();




        qOwner.setText("#"+ currQ.getOwner().toString());
        qTitle.setText(currQ.getTitle().toString());
        qDescription.setText(currQ.getDescription().toString());

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queueBuilder.getQuestionList().size() - 1  >= queueBuilder .getCurrPosition() + 1) {
                    queueBuilder.setCurrPosition(queueBuilder.getCurrPosition() + 1);
                    setQuestion(questionList);
                }
            }
        });

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(queueBuilder.getCurrPosition() >0){
                    queueBuilder.setCurrPosition(queueBuilder.getCurrPosition() - 1);
                    setQuestion(questionList);
                }
            }
        });



    }


    private class LoadQueueBuilder extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {


            queueBuilder = new QueueBuilder(MainPage.this,listSkill);
            queueBuilder.buildQueue();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in listview_main.xml


        }
    }


    private class LoadUserSkills extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            ParseQuery fetchUser = new ParseQuery("_User");
            fetchUser.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            fetchUser.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(java.util.List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        for (ParseObject userData : objects) {

                            listSkill = (ArrayList<String>) userData.get("skills");
                            if(firstTime) {
                                skillLoader = new SkillLoader(listSkill, MainPage.this);
                                skillLoader.loadSkills();
                            }else {
                                new LoadQueueBuilder().execute();
                            }


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

    public void setInVisSpin(){
        spin_kit.setVisibility(View.INVISIBLE);
        searchingTxt.setVisibility(View.INVISIBLE);
        qOwner.setVisibility(View.VISIBLE);
        qTitle.setVisibility(View.VISIBLE);
        qDescription.setVisibility(View.VISIBLE);
        pager_indicator.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.VISIBLE);
        backwardBtn.setVisibility(View.VISIBLE);
        intro_images.setVisibility(View.VISIBLE);

    }

    public void setVisSpin(){
        spin_kit.setVisibility(View.VISIBLE);
        searchingTxt.setVisibility(View.VISIBLE);
        qOwner.setVisibility(View.INVISIBLE);
        qTitle.setVisibility(View.INVISIBLE);
        qDescription.setVisibility(View.INVISIBLE);
        pager_indicator.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        backwardBtn.setVisibility(View.INVISIBLE);
        intro_images.setVisibility(View.INVISIBLE);
    }

    public void setDefaultBackground(){
        intro_images.setBackgroundColor(getResources().getColor(R.color.colorBg));
    }


    public void giveOrderQueue(){
        new LoadQueueBuilder().execute();
    }






}
