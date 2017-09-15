package com.min.optimize;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.min.optimize.bean.Person;
import com.min.optimize.fragment.MyFragment;
import com.min.optimize.util.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.view_fl, new MyFragment()).commit();
        Person person = new Person();
        Utils utils = new Utils();
    }

    public void clickGoLeakActivity(View view) {
        Intent intent = new Intent(this, MockLeakActivity.class);
        startActivity(intent);
    }

}
