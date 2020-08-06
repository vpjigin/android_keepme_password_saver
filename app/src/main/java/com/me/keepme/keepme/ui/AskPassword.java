package com.me.keepme.keepme.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.me.keepme.keepme.R;
import com.me.keepme.keepme.TimerClass;
import com.me.keepme.keepme.util.Pref;

import java.util.ArrayList;

public class AskPassword extends AppCompatActivity {

    @Override
    public void onResume()
    {
        super.onResume();
        TimerClass t = TimerClass.getObj();
        if (t.wasInBackground )
        {
            //Do specific came-here-from-background code
            Intent in = new Intent(getApplicationContext(),AskPassword.class);
            startActivity(in);
        }

        t.stopActivityTransitionTimer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        TimerClass t = TimerClass.getObj();
        t.startActivityTransitionTimer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_password);

        Pref p = new Pref(this);
        final String PASSWORD = p.getPref();

        EditText pass = (EditText) findViewById(R.id.password);

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(PASSWORD.equals(String.valueOf(s))){
                    Intent in = new Intent(getApplicationContext(),GroupActivity.class);
                    startActivity(in);
                    finish();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent in  = new Intent(getApplicationContext(),AskPassword.class);
        startActivity(in);
        finish();
    }
}
