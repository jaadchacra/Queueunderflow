package com.example.joseph.queueunderflow.authentication.createaccount;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.joseph.queueunderflow.MainActivity;
import com.example.joseph.queueunderflow.R;
import com.example.joseph.queueunderflow.headquarters.MainPage;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccount extends AppCompatActivity {

    @BindView(R.id.nameTF)
    EditText nameTF;
    @BindView(R.id.usernameTF)
    EditText usernameTF;
    @BindView(R.id.emailTF)
    EditText emailTF;
    @BindView(R.id.passwordTF)
    EditText passwordTF;

    @BindView(R.id.signUpBtn)
    TextView signUpBtn;

    private Dialog signDialog;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);



        mContext = this;




        /**********************************************Sign Up Listener *****************************************************************/

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signAlert();
                String fullNameText = nameTF.getText().toString();
                final String usernameText = usernameTF.getText().toString();
                String emailText = emailTF.getText().toString();
                String password = passwordTF.getText().toString();




                // Check conditions....

                if(fullNameText.equals("") || usernameText.equals("") || emailText.equals("") || password.equals("")){

                    signDialog.dismiss();
                    createAlert("Form is not Complete.");

                }

                else if(usernameText.length() <5){

                    signDialog.dismiss();
                    createAlert("Username length must be at least 5 characters long.");


                } else if(password.length()<8){

                    signDialog.dismiss();
                    createAlert("Password length must be at least 8 characters long.");

                }else{
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameText);
                    user.setPassword(password);
                    user.setEmail(emailText);


                    ArrayList<String> emptyArr = new ArrayList<String>();
                    user.put("FullName",fullNameText);
                    user.put("skills",emptyArr);


                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){
                                Intent intent = new Intent(mContext, MainPage.class);
                                intent.putExtra("firstTime",true);
                                signDialog.dismiss();
                                startActivity(intent);
                            }else{
                                if(e.getMessage().equals("Email address format is invalid.")){

                                    signDialog.dismiss();
                                    createAlert("Email address format is invalid.");

                                }else if(e.getMessage().equals("Account already exists for this username.")){

                                    signDialog.dismiss();
                                    createAlert("Account already exists for this username.");

                                }else if(e.getMessage().equals("Account already exists for this email address.")){

                                    signDialog.dismiss();
                                    createAlert("Account already exists for this email address.");
                                }
                                Log.d(CreateAccount.class.getSimpleName(),"The problem is : " + e.getMessage());//Show error message
                            }

                        }
                    });
                }
                
            }
        });
    }
    public void createAlert(String message){
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.customdialog);

        TextView alertMessage = (TextView) dialog.findViewById(R.id.alertMessage);
        Button okayBtn = (Button) dialog.findViewById(R.id.okayBtn);

        alertMessage.setText(message);
        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void signAlert(){
         signDialog = new Dialog(mContext);
        signDialog.setContentView(R.layout.signdialog);
        signDialog.show();
    }
}
