package com.min.ws.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minyangcheng on 2016/5/15.
 */
public class DataUtils {

    public static List<String> getAdImageList(){
        List<String> urlList=new ArrayList<>();
        urlList.add("http://img5.duitang.com/uploads/item/201312/27/20131227233154_3aeeS.thumb.600_0.jpeg");
        urlList.add("http://img5.imgtn.bdimg.com/it/u=2342902481,4277613580&fm=21&gp=0.jpg");
        urlList.add("http://img1.gamersky.com/image2016/03/20160329_lc_40_4/gamersky_04small_08_2016329151212A.jpg");
        return urlList;
    }

    public static List<String> getUpdateAdImageList(){
        List<String> urlList=new ArrayList<>();
        urlList.add("http://picture.youth.cn/dmzb/201305/W020130514542662922703.jpg");
        urlList.add("http://img4.duitang.com/uploads/item/201301/06/20130106192436_yKYL5.jpeg");
        return urlList;
    }

    public static List<String> getRecycleViewData(){
        List<String> dataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            dataList.add("----"+i+"----");
        }
        return dataList;
    }

    public static List<String> getUpdateRecycleViewData(){
        List<String> dataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            dataList.add("update----"+i+"----");
        }
        return dataList;
    }

}
