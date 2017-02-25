package com.example.joseph.queueunderflow.authentication.askquestion;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.headquarters.skills.SkillLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AskQuestionMain extends AppCompatActivity {

    @BindView(R.id.pickTitleTF)
    EditText pickTitleTF;
    @BindView(R.id.pickSkillsTF)
    Button pickSkillsTF;
    @BindView(R.id.tag1Pic)
    ImageView tag1Pic;
    @BindView(R.id.tag1Name)
    TextView tag1Name;


    @BindView(R.id.nextStepBtn)
    ImageView nextStepBtn;

    private SkillLoader skillLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question_main);
        ButterKnife.bind(this);

        tag1Name.setVisibility(View.INVISIBLE);
        pickSkillsTF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skillLoader = new SkillLoader(AskQuestionMain.this);
                skillLoader.loadSkills();
            }
        });

        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AskQuestionMain.this, FinalQuestionStep.class);
                intent.putExtra("title",pickTitleTF.getText().toString());
                startActivity(intent);
            }
        });
    }

    public void setTagFirst(String tagName,Uri tagPic){
        tag1Name.setVisibility(View.VISIBLE);
        skillLoader.setTag();
        tag1Name.setText(tagName);
        Glide.with(this).load(tagPic).dontAnimate().into(tag1Pic);
    }



}
