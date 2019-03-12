package com.mynotifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    public UsersFragment() {
        // Required empty public constructor
    }

    FirebaseFirestore mFirestore;

    private RecyclerView mUserListView;
    private List<Users> usersList;
    private UsersRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        usersList=new ArrayList<>();
        mFirestore=FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        mUserListView=(RecyclerView)view.findViewById(R.id.users_list);
        adapter=new UsersRecyclerAdapter(container.getContext(),usersList);
        mUserListView.setAdapter(adapter);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        usersList.clear();
        mFirestore.collection("Users").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {


                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                        String user_id=doc.getDocument().getId();
                        Users users=doc.getDocument().toObject(Users.class).withId(user_id);
                        usersList.add(users);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }
}
