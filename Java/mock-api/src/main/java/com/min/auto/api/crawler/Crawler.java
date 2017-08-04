package com.min.auto.api.crawler;

import us.codecraft.webmagic.Spider;

/**
 * Created by minyangcheng on 2017/8/4.
 */
public class Crawler {

    public static void start() {
        Spider.create(new ShowDocPageProcessor())
                .addUrl("http://10.10.13.12/showdoc/index.php/Home/item/show?item_id=4")
//                .addUrl("http://10.10.13.12/showdoc/index.php/Home/page/index?page_id=735")
                .addPipeline(new DbPipeline())
                .thread(3)
                .run();
    }

}
