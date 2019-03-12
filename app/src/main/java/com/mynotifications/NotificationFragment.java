package com.mynotifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    public NotificationFragment() {
        // Required empty public constructor
    }


    private List<Notifications> mNotifList;
    private FirebaseFirestore mFireStore;
    private NotificationsAdapter adapter;

    private RecyclerView mNotificationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNotifList = new ArrayList<>();
        mFireStore = FirebaseFirestore.getInstance();
        adapter = new NotificationsAdapter(container.getContext(), mNotifList);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        mNotificationList=(RecyclerView)view.findViewById(R.id.notif_list);
        mNotificationList.setAdapter(adapter);

        String current_user_id= FirebaseAuth.getInstance().getCurrentUser().getUid();

        mFireStore.collection("Users").document(current_user_id).collection("Notifications").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){

                    Notifications notifications=doc.getDocument().toObject(Notifications.class);
                    mNotifList.add(notifications);
                    adapter.notifyDataSetChanged();

                }
            }
        });

        return view;
    }

}
