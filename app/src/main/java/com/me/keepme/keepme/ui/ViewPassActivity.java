package com.me.keepme.keepme.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.me.keepme.keepme.Communicate;
import com.me.keepme.keepme.R;
import com.me.keepme.keepme.TimerClass;
import com.me.keepme.keepme.adapter.PassAdapter;
import com.me.keepme.keepme.model.DataModel;
import com.me.keepme.keepme.model.GroupModel;
import com.me.keepme.keepme.util.Appdb;
import com.me.keepme.keepme.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiginVp on 7/28/2017.
 */

public class ViewPassActivity extends AppCompatActivity implements Communicate
{
    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    Button pss;
    String gpId1 ;

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
            Log.e("groupactivity","onresume");
        }

        t.stopActivityTransitionTimer();

    }

    @Override
    public void onPause()
    {
        super.onPause();
        TimerClass t = TimerClass.getObj();
        t.startActivityTransitionTimer();
        Log.e("groupactivity","onpause");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpass);

        Utils.flagPlus();

        final String gpId = getIntent().getStringExtra("id");
        gpId1 = gpId;

        pss = (Button) findViewById(R.id.btPassword);

        Appdb db = new Appdb(getApplicationContext());
        List<DataModel> list = new ArrayList<>();
        list.addAll(db.getAllDatas(gpId));

        rv = (RecyclerView) findViewById(R.id.viewPass);
        lm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm);
        PassAdapter ad = new PassAdapter(ViewPassActivity.this, list, gpId);
        rv.setAdapter(ad);

        pss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final MaterialDialog m = new MaterialDialog.Builder(ViewPassActivity.this)
                        .customView(R.layout.dialog_add_pass, true)
                        .show();

                final EditText n, u, p, r, no;
                Button b;

                n = (EditText) m.findViewById(R.id.name);
                u = (EditText) m.findViewById(R.id.username);
                p = (EditText) m.findViewById(R.id.password);
                r = (EditText) m.findViewById(R.id.recoveryMail);
                no = (EditText) m.findViewById(R.id.notes);

                b = (Button) m.findViewById(R.id.addBt);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nS, uS, pS, rS, noS;

                        nS = n.getText().toString();
                        uS = u.getText().toString();
                        pS = p.getText().toString();
                        rS = r.getText().toString();
                        noS = no.getText().toString();

                        if (!nS.isEmpty() && !uS.isEmpty() && !pS.isEmpty()) {
                            DataModel dm = new DataModel();
                            dm.setName(nS);
                            dm.setUsername(uS);
                            dm.setPassword(pS);
                            dm.setRecovery(rS);
                            dm.setNotes(noS);
                            dm.setGroupId(gpId);

                            Appdb db = new Appdb(getApplicationContext());
                            db.insertData(dm);

                            m.dismiss();

                            Intent in =new Intent(getApplicationContext(),ViewPassActivity.class);
                            in.putExtra("id",gpId);
                            startActivity(in);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Field can't be empty", Toast.LENGTH_LONG).show();
                        }
                    }

                });

            }
        });
    }

    @Override
    public void goToViewPageActivity() {
        Intent in= new Intent(getApplicationContext(),ViewPassActivity.class);
        in.putExtra("id",gpId1);
        startActivity(in);
        finish();
    }

    @Override
    public void showGroupEditDialog(GroupModel gm) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent in = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(in);
                break;

            case R.id.suggesion:
                Intent in1= new Intent(getApplicationContext(),SugessionsActivity.class);
                startActivity(in1);
                break;

            case R.id.logout:
                Intent in2 = new Intent(getApplicationContext(),AskPassword.class);
                startActivity(in2);
                finish();
                break;

            case R.id.about:
                Intent in3 = new Intent(getApplicationContext(),AboutUs.class);
                startActivity(in3);
                finish();
                break;
        }
        return true;
    }

    public void logoutThis(){
        finish();
    }

}