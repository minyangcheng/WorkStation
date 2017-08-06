package com.min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Created by minyangcheng on 2017/8/6.
 */
@Component
public class StartUpRunner implements CommandLineRunner {

    private Logger logger= LoggerFactory.getLogger(StartUpRunner.class);

    @Autowired
    private AsyncTask asyncTask;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作<<<<<<<<<<<<<");

        Future<String> task1 = asyncTask.doTask1();
        Future<String> task2 = asyncTask.doTask2();
        while(true) {
            if(task1.isDone() && task2.isDone()) {
                logger.info("Task1 result: {}", task1.get());
                logger.info("Task2 result: {}", task2.get());
                break;
            }
            Thread.sleep(1000);
        }
        logger.info("All tasks finished.");
    }
}
