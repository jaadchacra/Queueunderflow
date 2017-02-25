package com.example.joseph.queueunderflow.authentication.askquestion;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.headquarters.MainPage;
import com.example.joseph.queueunderflow.viewpager.ViewPagerAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FinalQuestionStep extends AppCompatActivity  implements ViewPager.OnPageChangeListener, View.OnClickListener {


    @BindView(R.id.ownerQs)
    TextView ownerQs;
    @BindView(R.id.titleQs)
    TextView titleQs;
    @BindView(R.id.descitptionQs)
    EditText descriptionQs;
    @BindView(R.id.addNewImg)
    ImageView addNewImg;
    @BindView(R.id.pager_introduction)
    ViewPager intro_images;
    @BindView(R.id.postBtn)
    Button postButton;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout pager_indicator;
    @BindView(R.id.cropImageView)
    CropImageView cropImageView;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mViewPagerAdapter;


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    @BindView(R.id.spin_kit)
    SpinKitView spin_kit;
    @BindView(R.id.searchingTxT)
    TextView searchingTxt;

    private Bitmap bitmapParse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_question_step);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();


        if (extras != null) {
            titleQs.setText(extras.getString("title"));
        }

        ownerQs.setText("#"+ ParseUser.getCurrentUser().getUsername());

        initVis();

        addNewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postVis();





                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmapParse.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("androidbegin.jpeg", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();








                ArrayList<String> tagsList = new ArrayList<String>();
                tagsList.add("Java");
                tagsList.add("C++");
                tagsList.add("Python");


                ParseObject newQuestion = new ParseObject("Questions");
                newQuestion.put("title",titleQs.getText().toString());
                newQuestion.put("owner",ParseUser.getCurrentUser().getUsername());
                newQuestion.put("tags",tagsList);
                newQuestion.put("description",descriptionQs.getText().toString());
                newQuestion.put("image1",file);



                newQuestion.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Intent intent = new Intent(FinalQuestionStep.this, MainPage.class);

                            startActivity(intent);
                        }else{
                            Log.d(FinalQuestionStep.class.getSimpleName(),"el meskle eno : " + e.getMessage());
                        }
                    }
                });

            }

        });



    }

    public void initVis(){
        spin_kit.setVisibility(View.INVISIBLE);
        searchingTxt.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(FinalQuestionStep.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(FinalQuestionStep.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                ArrayList<Uri> bmList = new ArrayList<>();

                try {
                    bitmapParse = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                bmList.add(result.getUri());

               // mViewPagerAdapter = new ViewPagerAdapter(FinalQuestionStep.this,bmList);

                intro_images.setAdapter(mViewPagerAdapter);
                intro_images.setCurrentItem(0);
                intro_images.setOnPageChangeListener(this);





                setUiPageViewController();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

            }
        }

        if (resultCode == FinalQuestionStep.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }


    }



    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
Bitmap bm = null;
        if (data != null) {
            try {
                 bitmapParse = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





        ByteArrayOutputStream bytes = new ByteArrayOutputStream();


        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList <Uri> bmList = new ArrayList<>();





        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmapParse, "Title", null);
        Uri imageUri =  Uri.parse(path);


        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);




    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArrayList <Bitmap> bmList = new ArrayList<>();
        bmList.add(bm);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bm, "Title", null);
        Uri imageUri =  Uri.parse(path);


        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);

      //  mViewPagerAdapter = new ViewPagerAdapter(FinalQuestionStep.this,bmList);

      //  intro_images.setAdapter(mViewPagerAdapter);
       // intro_images.setCurrentItem(0);
       // intro_images.setOnPageChangeListener(this);




       // setUiPageViewController();
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

    public void postVis(){
        titleQs.setVisibility(View.INVISIBLE);
        ownerQs.setVisibility(View.INVISIBLE);
        intro_images.setVisibility(View.INVISIBLE);
        pager_indicator.setVisibility(View.INVISIBLE);
        addNewImg.setVisibility(View.INVISIBLE);
        postButton.setVisibility(View.INVISIBLE);
        descriptionQs.setVisibility(View.INVISIBLE);
        spin_kit.setVisibility(View.VISIBLE);
        searchingTxt.setVisibility(View.VISIBLE);

    }





}
