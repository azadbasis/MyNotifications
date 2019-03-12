package com.mynotifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import javax.annotation.Nullable;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<Notifications> mNotifList;
    private FirebaseFirestore firebaseFirestore;
    private Context context;

    public NotificationsAdapter(Context context, List<Notifications> mNotifList) {
        this.context = context;
        this.mNotifList = mNotifList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_notification, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        firebaseFirestore = FirebaseFirestore.getInstance();
        String from_id = mNotifList.get(position).getFrom();
        viewHolder.mNotifMessage.setText(mNotifList.get(position).getMessage());
        firebaseFirestore.collection("Users").document(from_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String name = documentSnapshot.getString("name");
                String image = documentSnapshot.getString("image");
                viewHolder.mNotifName.setText(name);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.empty);
                Glide.with(context).setDefaultRequestOptions(requestOptions).load(image).into(viewHolder.mNotifImage);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotifList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNotifName;
        public TextView mNotifMessage;
        public ImageView mNotifImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNotifName = (TextView) itemView.findViewById(R.id.notif_name);
            mNotifMessage = (TextView) itemView.findViewById(R.id.notif_message);
            mNotifImage = (ImageView) itemView.findViewById(R.id.notif_image);
        }
    }
}
