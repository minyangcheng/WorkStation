package com.min.know.java8;

import com.min.know.util.L;

/**
 * Created by minyangcheng on 2017/8/14.
 */
public class LamdaDemo {

    public void click(OnClickListener listener) {
        listener.onClick();
    }

    public static void main(String[] args) {
        LamdaDemo demo = new LamdaDemo();
        demo.click(() -> {
            L.d("lamda demo");
        });
    }

}
