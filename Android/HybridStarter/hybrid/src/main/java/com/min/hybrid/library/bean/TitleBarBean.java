package com.min.hybrid.library.bean;

import java.util.List;

/**
 * Created by minyangcheng on 2018/1/29.
 */

public class TitleBarBean {

    public boolean canGoBack;
    public String title;
    public List<TitleBtn> titleBtnList;
    public int titleBtnShowNum = 1;

    public static class TitleBtn {

        public String btnStr;
        public String btnUrl;
        public String btnType;

    }

}
