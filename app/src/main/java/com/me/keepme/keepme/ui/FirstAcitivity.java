package com.me.keepme.keepme.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.me.keepme.keepme.R;
import com.me.keepme.keepme.model.GroupModel;
import com.me.keepme.keepme.util.Appdb;
import com.me.keepme.keepme.util.Pref;

import java.util.ArrayList;
import java.util.List;

public class FirstAcitivity extends AppCompatActivity {
    private EditText pass1,pass2;
    private Button conf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Pref p = new Pref(this);
        if(!p.getPref().isEmpty()) {
            Intent in = new Intent(getApplicationContext(), AskPassword.class);
            startActivity(in);
            finish();
        }

        setContentView(R.layout.activity_first);

        setUI();

        conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p1 = pass1.getText().toString();
                String p2 = pass2.getText().toString();
                if(p1.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Password",Toast.LENGTH_SHORT).show();
                }
                else if(p2.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Enter Confirm Password",Toast.LENGTH_SHORT).show();
                }
                else if(!p1.equals(p2)){
                    Toast.makeText(getApplicationContext(),"Your Password don't match",Toast.LENGTH_SHORT).show();
                }
                else {
                    Pref p = new Pref(getApplicationContext());
                    p.savePref(p1);

                    insertGrp();

                    Intent in = new Intent(getApplicationContext(),GroupActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }
    void setUI(){
        pass1 = (EditText) findViewById(R.id.pass1);
        pass2 = (EditText) findViewById(R.id.pass2);
        conf = (Button) findViewById(R.id.btConfirm);
    }
    void insertGrp(){
        Appdb db = new Appdb(this);
        db.insertGroup(new GroupModel(0,"Add Group"));
        db.insertGroup(new GroupModel(5,"Mail"));
        db.insertGroup(new GroupModel(10,"Website"));
        db.insertGroup(new GroupModel(7,"Social"));
        db.insertGroup(new GroupModel(6,"Others"));
        db.insertGroup(new GroupModel(2,"Important"));
    }
}
