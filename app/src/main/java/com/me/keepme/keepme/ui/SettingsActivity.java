package com.me.keepme.keepme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.me.keepme.keepme.R;
import com.me.keepme.keepme.TimerClass;
import com.me.keepme.keepme.util.Pref;

/**
 * Created by JiginVp on 8/3/2017.
 */

public class SettingsActivity extends AppCompatActivity
{
    EditText curPass,newPass,confNewPass;
    Button changePass;
    Pref p ;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUI();

        p = new Pref(this);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCurPass = curPass.getText().toString();
                String sNewPass = newPass.getText().toString();
                String sConfPass = confNewPass.getText().toString();

                Toast.makeText(getApplicationContext(),"button clickked",Toast.LENGTH_SHORT).show();
                if(sCurPass.isEmpty()){
                    curPass.requestFocus();
                    curPass.setError("This field can't be empty");
                }else{
                    if(sCurPass.equals(p.getPref())){
                        if(sNewPass.isEmpty()){
                            newPass.requestFocus();
                            newPass.setError("Field can't be empty");
                        }else if(sConfPass.isEmpty()){
                            confNewPass.requestFocus();
                            confNewPass.setError("Field can't be empty");
                        }else if(!sNewPass.equals(sConfPass)){
                            Toast.makeText(getApplicationContext(),"New passwords don't match",Toast.LENGTH_SHORT).show();
                        }else {
                            p.savePref(sNewPass);
                            Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getApplicationContext(),AskPassword.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                        }
                    }else{
                        curPass.requestFocus();
                        curPass.setError("Wrong password");
                    }
                }
            }
        });


    }
    void setUI(){
        curPass = (EditText) findViewById(R.id.curPass);
        newPass = (EditText) findViewById(R.id.newPass);
        confNewPass = (EditText) findViewById(R.id.confNewPass);

        changePass = (Button) findViewById(R.id.changeBt);
    }
}
