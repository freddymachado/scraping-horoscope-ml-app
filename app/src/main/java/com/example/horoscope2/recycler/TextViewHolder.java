package com.example.horoscope2.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.horoscope2.R;

public class TextViewHolder extends BaseViewHolder {

    private TextView message;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.text_message_body);
    }

    @Override
    void setData(TimelineItem item) {

        TextItem textItem = item.getTextItem();
        message.setText(textItem.getPostText());

    }
}
