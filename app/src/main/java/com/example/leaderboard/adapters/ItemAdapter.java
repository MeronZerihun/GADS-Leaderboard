package com.example.leaderboard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.leaderboard.R;
import com.example.leaderboard.models.Learner;
import com.example.leaderboard.models.Skill;

import java.util.List;

public class ItemAdapter<T> extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context mContext;
    private LayoutInflater mInflater;
    private List<T> mItems;

    public ItemAdapter(Context context, List<T> items){
        mContext = context;
        mItems = items;

        mInflater = LayoutInflater.from(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_content, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        T item = mItems.get(position);

        if(item instanceof Learner){
            Glide.with(mContext)
                    .load(((Learner) item).getBadgeUrl())
                    .into(holder.mBadgeImage);


            holder.mUserName.setText(((Learner) item).getName());
            holder.mUserDescription.setText(((Learner) item).generateDescription());

        }
        else if(item instanceof Skill){
            Glide.with(mContext)
                    .load(((Skill) item).getBadgeUrl())
                    .into(holder.mBadgeImage);

            holder.mUserName.setText(((Skill) item).getName());
            holder.mUserDescription.setText(((Skill) item).generateDescription());

        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder<T> extends RecyclerView.ViewHolder{

        private final TextView mUserName;
        private final TextView mUserDescription;
        private final ImageView mBadgeImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mUserName = itemView.findViewById(R.id.name);
            mUserDescription = itemView.findViewById(R.id.description);
            mBadgeImage = itemView.findViewById(R.id.badge_image);
        }
    }
}
