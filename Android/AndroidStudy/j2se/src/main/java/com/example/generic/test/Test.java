package com.example.generic.test;

import com.example.util.L;

/**
 * Created by minyangcheng on 2016/9/21.
 */
public class Test {

    public static void main(String args[]){
        Client client=new Client();
        client.request(new CallBack<Person>() {
            @Override
            public void onSuccess(Person person) {
                L.d(person.toString());
            }
        });
    }

}
