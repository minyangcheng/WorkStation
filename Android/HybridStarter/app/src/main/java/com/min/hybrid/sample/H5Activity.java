package com.min.hybrid.sample;

import com.min.hybrid.library.HybridActivity;

/**
 * Created by minyangcheng on 2018/1/15.
 */

public class H5Activity extends HybridActivity {

    @Override
    protected void render() {
        loadUrl("http://10.10.13.117:8080/");
    }
}
