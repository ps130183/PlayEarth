package com.km.rmbank.utils.timepicker;

import com.lvfq.pickerview.adapter.WheelAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by PengSong on 18/9/18.
 */

public class BookVenueWheelAdapter<T> implements WheelAdapter<String> {

    enum Type{
        DATE,
        TIME;
    }

    public DateFormat dateFormat = new SimpleDateFormat("MM月dd日");
    public DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    private Type type;
    private List<T> listContens;

    public BookVenueWheelAdapter( List<T> listContens,Type type) {
        this.type = type;
        this.listContens = listContens;
    }

    @Override
    public int getItemsCount() {
        return listContens.size();
    }



    @Override
    public String getItem(int index) {
        if (type == Type.DATE){
            return dateFormat.format(new Date((Long) listContens.get(index)));
        } else {
            return (String) listContens.get(index);
        }

    }

    public String getTime(int index){
        if (type == Type.DATE){
            return dateFormat2.format(new Date((Long) listContens.get(index)));
        } else {
            return ((String) listContens.get(index)).substring(0,((String) listContens.get(index)).length() - 1) + ":00";
        }
    }

    @Override
    public int indexOf(String o) {
        return listContens.indexOf(o);
    }

}
