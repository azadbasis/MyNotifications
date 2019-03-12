package com.mynotifications;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFireStore;

    private Button mLogoutBtn;
    private CircleImageView mProfileImage;
    private TextView mProfileName;

    private String mUserId;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        mProfileName = (TextView) view.findViewById(R.id.profile_name);
        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> removeToken=new HashMap<>();
                removeToken.put("token_id", FieldValue.delete());
                mFireStore.collection("Users").document(mUserId).update(removeToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mAuth.signOut();
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }
                });

            }
        });


        mUserId = mAuth.getCurrentUser().getUid();
        mFireStore.collection("Users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String user_name=documentSnapshot.getString("name");
                String user_image=documentSnapshot.getString("image");

                mProfileName.setText(user_name);
                RequestOptions placeHolderOption=new RequestOptions();
                placeHolderOption.placeholder(R.drawable.empty);
                Glide.with(container.getContext()).applyDefaultRequestOptions(placeHolderOption).load(user_image).into(mProfileImage);
            }
        });


        return view;
    }

}
