package com.me.keepme.keepme;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

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

public class AppService extends Service {
    Appdb db;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        db = new Appdb(this);

        List<String[]> list = db.getSugessions();

        if(Utils.isNetworkAvailable(this)){
            new SendSugessionsAsync(list).execute();
        }

    }

    public class SendSugessionsAsync extends AsyncTask<Void,Void,Void> {
        private List<String[]> suggesions;
        boolean isSuccess = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        public SendSugessionsAsync(List<String[]> sugession){
            this.suggesions = sugession;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            String URL = "http://apkgrouphub.com/uniquemarketing/app/addSug.php";
            JSONParser j = new JSONParser();
            int size = suggesions.size();
            for(int i = 0 ;i<size; i++){
                String[] data= suggesions.get(i);

                List<NameValuePair> list = new ArrayList<>();
                list.add(new BasicNameValuePair("sug",data[1]));


                try {
                    JSONObject jO = j.makeHttpRequest(URL, "POST", list);
                    if (jO.getInt("success") > 0) {
                        isSuccess = true;
                        db.deleteSugession(data[0]);
                    }
                }catch (JSONException e){
                    isSuccess = false;
                }catch (Exception e){
                    isSuccess = false;
                }
            }


            return null;
        }


    }
}
