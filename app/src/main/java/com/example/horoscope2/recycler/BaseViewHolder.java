package com.example.horoscope2.recycler;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horoscope2.recycler.TimelineItem;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    abstract  void setData(TimelineItem item);

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
