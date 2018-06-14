package com.min.know.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintThreadDemo {

    public static void main(String args[]) {

        PrintThread aThread = new PrintThread("a");
        aThread.start();
        PrintThread bThread = new PrintThread("b");
        bThread.start();
        PrintThread cThread = new PrintThread("c");
        cThread.start();

        int a=1;
        String.class.notify();
        a=2;


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static class PrintThread extends Thread {

        private static AtomicInteger num = new AtomicInteger(1);
        private static Object lock = new Object();

        private static volatile int[] counterArr = new int[]{0, 0, 0};

        private String name;
        private int index;

        public PrintThread(String name) {
            this.name = name;
            if (name.equals("a")) {
                this.index = 0;
            } else if (name.equals("b")) {
                this.index = 1;
            } else if (name.equals("c")) {
                this.index = 2;
            }
        }

        @Override
        public void run() {
            while (num.get() < 100) {
                synchronized (lock) {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("thread-" + name + "   "+ counterArr[0] +"  "+counterArr[1]+" "+counterArr[2]);


                        boolean tempFlag = false;
                        if (index == 0) {
                            tempFlag = counterArr[0] == 0;
                        } else if (index == 1) {
                            tempFlag = counterArr[0] == 1 && counterArr[1] == 0;
                        } else if (index == 2) {
                            tempFlag = counterArr[0] == 1 && counterArr[1] == 1 && counterArr[2] == 0;
                        }

                        if(tempFlag){
                            break;
                        }else {
                            lock.notifyAll();
                        }

                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    System.out.println("thread-" + name + "  -- > " + num.getAndIncrement());
                    System.out.println("thread-" + name + "  -- > " + num.getAndIncrement());
                    System.out.println("thread-" + name + "  -- > " + num.getAndIncrement());
                    counterArr[index] = counterArr[index] + 1;

                    if (index == 2) {
                        counterArr[0] = 0;
                        counterArr[1] = 0;
                        counterArr[2] = 0;
                    }
                }
            }
        }

    }

}
