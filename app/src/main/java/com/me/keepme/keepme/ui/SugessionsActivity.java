package com.me.keepme.keepme.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.me.keepme.keepme.JSONParser;
import com.me.keepme.keepme.R;
import com.me.keepme.keepme.util.Appdb;
import com.me.keepme.keepme.util.Utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiginVp on 8/3/2017.
 */

public class SugessionsActivity extends AppCompatActivity
{
    private static EditText sugession;
    Button send;
    ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugessions);

        sugession = (EditText) findViewById(R.id.sugTxt);
        send = (Button) findViewById(R.id.sugBt);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sug = sugession.getText().toString();
                if(sug.isEmpty()){
                    sugession.requestFocus();
                    sugession.setError("Please fill any suggessions");
                }
                else {
                    if(Utils.isNetworkAvailable(getApplicationContext())){
                        new SendSugessionsAsync(sug).execute();
                    }
                    else {
                        Appdb db = new Appdb(getApplicationContext());
                        db.addsugessions(sug);
                        Toast.makeText(getApplicationContext(),"Thanks for your feedback",Toast.LENGTH_SHORT).show();
                        sugession.setText("");
                    }
                }
            }
        });
    }
    public class SendSugessionsAsync extends AsyncTask<Void,Void,Void> {
        private String suggesions;
        boolean isSuccess = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SugessionsActivity.this);
            dialog.setMessage("Your message..");
            dialog.show();
        }

        public SendSugessionsAsync(String sugession){
            this.suggesions = sugession;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(dialog !=null)
                dialog.dismiss();

            if(isSuccess){
                Toast.makeText(getApplicationContext(),"Thanks for you feedback",Toast.LENGTH_SHORT).show();
                sugession.setText("");
            }
            else {
                Appdb db =new Appdb(getApplicationContext());
                db.addsugessions(suggesions);
                sugession.setText("");
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            String URL = "http://apkgrouphub.com/uniquemarketing/app/addSug.php";

            List<NameValuePair>list = new ArrayList<>();
            list.add(new BasicNameValuePair("sug",suggesions));

            JSONParser j = new JSONParser();
            try {
                JSONObject jO = j.makeHttpRequest(URL, "POST", list);
                if (jO.getInt("success") > 0) {
                    isSuccess = true;
                }
            }catch (JSONException e){
                isSuccess = false;
            }catch (Exception e){
                isSuccess = false;
            }
            return null;
        }


    }
}
