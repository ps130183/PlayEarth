package com.km.rmbank.adapter;

import android.view.View;
import android.widget.TextView;

import com.km.rmbank.R;
import com.km.rmbank.dto.MyTeamDto;
import com.nineoldandroids.animation.ObjectAnimator;
import com.zaihuishou.expandablerecycleradapter.viewholder.AbstractExpandableAdapterItem;

/**
 * Created by PengSong on 18/5/10.
 */

public class TeamParentItem extends AbstractExpandableAdapterItem {

    private TextView teamName;
    private TextView teamNumber;

    @Override
    public void onExpansionToggled(boolean expanded) {
//        float start, target;
//        if (expanded) {
//            start = 0f;
//            target = 90f;
//        } else {
//            start = 90f;
//            target = 0f;
//        }
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mArrow, View.ROTATION, start, target);
//        objectAnimator.setDuration(300);
//        objectAnimator.start();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_team_parent;
    }

    @Override
    public void onBindViews(View root) {
//        root.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doExpandOrUnexpand();
//            }
//        });
        teamName = root.findViewById(R.id.tv_team_name);
        teamNumber = root.findViewById(R.id.teamNumber);
    }

    @Override
    public void onSetViews() {

    }

    @Override
    public void onUpdateViews(Object model, int position) {
        super.onUpdateViews(model, position);
        onSetViews();
        onExpansionToggled(getExpandableListItem().isExpanded());
        MyTeamDto teamDto = (MyTeamDto) model;
        teamName.setText(teamDto.getRoleName());
        teamNumber.setText(teamDto.getNum()+"");
    }
}
