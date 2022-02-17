package com.example.horoscope2.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horoscope2.R;
import com.example.horoscope2.recycler.BaseViewHolder;
import com.example.horoscope2.recycler.Constant;
import com.example.horoscope2.recycler.PalmPhotoViewHolder;
import com.example.horoscope2.recycler.TextViewHolder;
import com.example.horoscope2.recycler.TimelineItem;

import java.util.List;

public class AdapterDatos extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<TimelineItem> mdata;

    public AdapterDatos(Context mContext, List<TimelineItem> mdata) {
        this.mContext = mContext;
        this.mdata = mdata;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch(viewType){
            case (Constant.ITEM_PALM_PHOTO_VIEWTYPE):
                view = LayoutInflater.from(mContext).inflate(R.layout.activity_1,parent,false);
                return new PalmPhotoViewHolder(view);
            case (Constant.ITEM_TEXT_VIEWTYPE):
                view = LayoutInflater.from(mContext).inflate(R.layout.item_message_received,parent,false);
                return new TextViewHolder(view);

            default: throw new IllegalArgumentException();
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        holder.setData(mdata.get(position));

    }

    @Override
    public int getItemViewType(int position) {
        return mdata.get(position).getViewType();
    }

    @Override
    public int getItemCount() {

        if(mdata!=null){
            return mdata.size();
        }else {
            return 0;
        }

    }

    public void setItems(List<TimelineItem> mdata) {
        this.mdata = mdata;
    }


}
