package com.mynotifications;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {

    List<Users> usersList;
    Context context;

    public UsersRecyclerAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UsersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_list_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerAdapter.ViewHolder viewHolder, int position) {


        viewHolder.user_name_view.setText(usersList.get(position).getName());
        CircleImageView user_image_view = viewHolder.user_image_view;
        Glide.with(context).load(usersList.get(position).getImage()).into(user_image_view);

        final String user_id=usersList.get(position).userId;
        final String user_name=usersList.get(position).getName();
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(context,SendActivity.class);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_name",user_name);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView user_image_view;
        public TextView user_name_view;
        public View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_image_view = (CircleImageView) itemView.findViewById(R.id.user_list_image);
            user_name_view = (TextView) itemView.findViewById(R.id.user_list_name);
            mView = itemView;
        }
    }
}
