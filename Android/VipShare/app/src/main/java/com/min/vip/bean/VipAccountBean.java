package com.min.vip.bean;

import com.min.vip.adapter.RefactorVipListAdapter;
import com.min.vip.widget.adapter.ExpandableRecyclerAdapter;

/**
 * Created by minyangcheng on 2016/10/14.
 */
public class VipAccountBean extends ExpandableRecyclerAdapter.ListItem{

    public String userAccount;
    public String userPass;

    public VipAccountBean(){
        super(RefactorVipListAdapter.TYPE_ITEM);
    }

}
