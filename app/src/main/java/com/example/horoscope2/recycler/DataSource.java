package com.example.horoscope2.recycler;

import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static List<TimelineItem> getTimelineData(){
        List<TimelineItem> mdata = new ArrayList<>();


        //Create text item
        TextItem itemText = new TextItem("Yesterday");
        TimelineItem textTimelineItem = new TimelineItem(itemText);

        //Create palm photo item
        //PalmPhotoItem itemPhoto = new PalmPhotoItem()

        mdata.add(textTimelineItem);

        return mdata;
    }
}
