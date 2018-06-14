package com.min.know.thread;

public class PublicBox {

    private int apple;

    public synchronized void increase() {
        while (apple == 5) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apple++;
        System.out.println(Thread.currentThread().getId()+"生成苹果成功--" + apple);
        notify();
    }

    public synchronized void decrease() {
        while (apple == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        apple--;
        System.out.println(Thread.currentThread().getId()+"消费苹果---" + apple);
        notify();
    }

    public int getApple() {
        return apple;
    }

    public static void main(String args[]) {

        PublicBox box=new PublicBox();
        for(int i=0;i<10;i++){
            Produce produce=new Produce(box);
            Consume consume=new Consume(box);
            produce.start();
            consume.start();
        }
    }

}

class Produce extends Thread {

    PublicBox box;

    public Produce(PublicBox box) {
        this.box = box;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            box.increase();
        }
    }
}

class Consume extends Thread {
    PublicBox box;

    public Consume(PublicBox box) {
        this.box = box;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            box.decrease();
        }
    }
}