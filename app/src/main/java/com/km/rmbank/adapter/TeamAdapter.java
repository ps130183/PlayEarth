package com.km.rmbank.adapter;

import android.support.annotation.NonNull;

import com.km.rmbank.dto.MyTeamDto;
import com.zaihuishou.expandablerecycleradapter.adapter.BaseExpandableAdapter;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractAdapterItem;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

import java.util.List;

/**
 * Created by PengSong on 18/5/10.
 */

public class TeamAdapter extends BaseExpandableAdapter{

    public static final int ITEM_TYPE_PARENT = 0;
    public static final int ITEM_TYPE_SUB = 1;

    private TeamSubItem.OnClickSubListener onClickSubListener;

    public TeamAdapter(List data) {
        super(data);
    }

    @NonNull
    @Override
    public AbstractAdapterItem<Object> getItemView(Object type) {
        AbstractExpandableAdapterItem item = null;
        int itemType = (int) type;
        switch (itemType){
            case ITEM_TYPE_PARENT:
                item =  new TeamParentItem();
                break;
            case ITEM_TYPE_SUB:
                item = new TeamSubItem(onClickSubListener);
                break;
        }
        return item;
    }

    @Override
    public Object getItemViewType(Object t) {
        if (t instanceof MyTeamDto){
            return ITEM_TYPE_PARENT;
        } else if (t instanceof  MyTeamDto.MemberDtoListBean){
            return ITEM_TYPE_SUB;
        }
        return -1;
    }

    public void setOnClickSubListener(TeamSubItem.OnClickSubListener onClickSubListener) {
        this.onClickSubListener = onClickSubListener;
    }
}
