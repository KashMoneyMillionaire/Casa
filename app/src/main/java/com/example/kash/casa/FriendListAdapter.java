package com.example.kash.casa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kash.casa.api.userProfileApi.model.UserProfile;

import java.util.List;

/**
 * Created by Kash on 2/15/2015.
 * Do work homie.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder>
{
    private List<UserProfile> mDataset;
    private Context context;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View mView;
        public TextView mTextView;
        private Context context;

        public ViewHolder(View v, Context context) {
            super(v);
            mView = v;
            mTextView = ((TextView) v.findViewById(R.id.info_text));
            this.context = context;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Toast.makeText(context, mTextView.getText(), Toast.LENGTH_SHORT ).show();
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendListAdapter(List<UserProfile> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        context = parent.getContext();

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.view_friend, parent, false);

        ViewHolder vh = new ViewHolder(v, context);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getUsername());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
