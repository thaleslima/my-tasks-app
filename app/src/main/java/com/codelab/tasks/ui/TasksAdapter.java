package com.codelab.tasks.ui;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codelab.tasks.R;
import com.codelab.tasks.data.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 2/23/15.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {
    private List<Task> mDataset;
    private final View.OnClickListener mOnClickListener;
    private SparseBooleanArray selectedItems;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) itemView.findViewById(R.id.tvTask);;
        }
    }

    public TasksAdapter(Context context, List<Task> myDataset, View.OnClickListener onClickListener) {
        mContext = context;
        mDataset = myDataset;
        this.mOnClickListener = onClickListener;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false);

        ViewHolder vh = new ViewHolder(v);
        Integer i = vh.getPosition();
        v.setOnClickListener(mOnClickListener);
        v.setTag(vh);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = mDataset.get(position);

        holder.mTextView.setText(task.getText());
        if (task.isCompleted()) {
            holder.mTextView.setPaintFlags(holder.mTextView .getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.text_complete));
        } else {
            holder.mTextView.setPaintFlags(holder.mTextView.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mTextView.setTextColor(mContext.getResources().getColor(R.color.text_no_complete));

        }

        holder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

}
