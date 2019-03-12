package com.mynotifications;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    private TextView user_id_view;
    private EditText mMessageView;
    private Button mSendBtn;
    private ProgressBar mMessageProgressBar;

    private FirebaseFirestore mFirestore;

    private String mUserId;
    private String mUserName;
    private String mCurrentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();

        mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");


        user_id_view = (TextView) findViewById(R.id.user_id_view);
        mMessageView = (EditText) findViewById(R.id.message_view);
        mSendBtn = (Button) findViewById(R.id.send_btn);
        mMessageProgressBar = (ProgressBar) findViewById(R.id.messageProgressBar);

        user_id_view.setText("Send to "+mUserName);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = mMessageView.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    mMessageProgressBar.setVisibility(View.VISIBLE);
                    Map<String, Object> notificationMessage = new HashMap<>();
                    notificationMessage.put("message", message);
                    notificationMessage.put("from", mCurrentId);

                    mFirestore.collection("Users/" + mUserId + "/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            mMessageProgressBar.setVisibility(View.INVISIBLE);
                            mMessageView.setText("");
                            Toast.makeText(SendActivity.this, "Notification sent..", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SendActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            mMessageProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }

            }
        });
    }
}
