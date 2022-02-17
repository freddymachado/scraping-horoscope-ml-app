package com.example.horoscope2.recycler;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.horoscope2.R;

public class PalmPhotoViewHolder extends BaseViewHolder {

    private ImageView palmPhoto;

    public PalmPhotoViewHolder(@NonNull View itemView) {
        super(itemView);

        palmPhoto = itemView.findViewById(R.id.palmPhoto);
    }
    @Override
    void setData(TimelineItem item) {
        PalmPhotoItem photo = item.getPalmPhotoItem();
        Glide.with(itemView.getContext()).load(photo.getFoto()).into(palmPhoto);
        /**Glide.with(itemView.getContext()).load(new File(String.valueOf(photo.getFoto())))// Uri of the picture
                .into(palmPhoto);*/


    }
}
