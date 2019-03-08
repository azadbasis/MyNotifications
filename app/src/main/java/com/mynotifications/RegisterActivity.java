package com.mynotifications;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity {


    private ImageButton mImageButton;
    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegBtn;
    private Button mLoginPageBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mImageButton = (ImageButton)findViewById(R.id.register_image_btn);
        mNameField=(EditText)findViewById(R.id.register_name);
        mEmailField=(EditText)findViewById(R.id.register_email);
        mPasswordField=(EditText)findViewById(R.id.register_password);
        mRegBtn=(Button)findViewById(R.id.register_btn);
        mLoginPageBtn=(Button)findViewById(R.id.register_login_btn);

        mLoginPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
    }
}
