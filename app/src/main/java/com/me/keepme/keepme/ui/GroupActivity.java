package com.me.keepme.keepme.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.me.keepme.keepme.Communicate;
import com.me.keepme.keepme.R;
import com.me.keepme.keepme.TimerClass;
import com.me.keepme.keepme.adapter.GroupAdapter;
import com.me.keepme.keepme.model.GroupModel;
import com.me.keepme.keepme.util.Appdb;
import com.me.keepme.keepme.util.ItemClickSupport;
import com.me.keepme.keepme.util.Utils;
import java.util.ArrayList;
import java.util.List;

public class GroupActivity extends AppCompatActivity implements Communicate{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager lm;
    GroupAdapter adapter ;
    List<GroupModel>list;
    Appdb db;
    private static int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        setRecycler();

        Utils.flagPlus();

        Log.e("intent",(getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)+"");
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY )!=0){
            Log.e("flag INtent", "Called from history");
            //clear flag from history
            Intent intent = getIntent().setFlags( getIntent().getFlags() & (~ Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY));
            setIntent(intent);
        }

        db = new Appdb(this);
        db.setName();
        Toast.makeText(getApplicationContext(),db.getName(),Toast.LENGTH_LONG).show();
        list = db.getAllGroup();
        adapter = new GroupAdapter(this,list);
        recyclerView.setAdapter(adapter);


        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(final RecyclerView recyclerView, final int position, View v) {
                if(position == 0){
                    final MaterialDialog m = new MaterialDialog.Builder(GroupActivity.this)
                            .customView(R.layout.dialog_addgroup,true)
                            .show();

                    final EditText grpName = (EditText) m.findViewById(R.id.di_grpName);
                    Button add = (Button) m.findViewById(R.id.di_add);
                    final ImageView clipSel = (ImageView) m.findViewById(R.id.clipArtSelect);
                    final RecyclerView clipArt = (RecyclerView) m.findViewById(R.id.di_clipArt);

                    LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
                    lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    clipArt.setItemAnimator(new DefaultItemAnimator());
                    clipArt.setLayoutManager(lm);
                    final ClipArtAdapter ad = new ClipArtAdapter(getApplicationContext(), Utils.getImageResourse());
                    clipArt.setAdapter(ad);

                    ItemClickSupport.addTo(clipArt).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                        @Override
                        public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                            pos = position;
                            clipSel.setVisibility(View.VISIBLE);
                            clipArt.setVisibility(View.GONE);
                            Drawable d = getResources().getDrawable(Utils.getImageResourse(position));
                            clipSel.setImageDrawable(d);
                        }
                    });

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(grpName.getText().toString().isEmpty()){
                                grpName.setError("This fiels can't be empty");
                            }
                            else {
                                db.insertGroup(new GroupModel(pos,grpName.getText().toString()));
                                Intent in = new Intent(getApplicationContext(),GroupActivity.class);
                                startActivity(in);
                            }
                        }
                    });
                }
                else {

                    TextView t = (TextView) v.findViewById(R.id.grpId);
                    Intent in = new Intent(getApplicationContext(),ViewPassActivity.class);
                    in.putExtra("id",t.getText().toString());
                    startActivity(in);
                }
            }
        });
    }

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

    void setRecycler(){
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        lm = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(lm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        list = new ArrayList<>();
        adapter = new GroupAdapter(this,list);
        recyclerView.setAdapter(adapter);

        showDb();
    }
    void showDb(){
        Appdb db = new Appdb(this);
        List<String[]>list = db.getSugessions();
        Log.e("suggesSize",list.size()+"");
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

    @Override
    public void goToViewPageActivity() {
    }

    @Override
    public void showGroupEditDialog(GroupModel gm) {
        editGroupDialog(gm);
    }



    public class ClipArtAdapter extends RecyclerView.Adapter<ClipArtAdapter.AppViewholder>{
        private Context ctx;
        private int[]img;
        public ClipArtAdapter(Context ctx,int[] img){
            this.img = img;
            this.ctx = ctx;
        }

        @Override
        public AppViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_clipart,parent,false);
            return new AppViewholder(v);
        }

        @Override
        public void onBindViewHolder(AppViewholder holder, int position) {
            Drawable d = ctx.getResources().getDrawable(img[position]);
            holder.imgClipart.setImageDrawable(d);
        }

        @Override
        public int getItemCount() {
            return img.length;
        }


        public class AppViewholder extends RecyclerView.ViewHolder{
            ImageView imgClipart;
            public AppViewholder(View itemView) {
                super(itemView);
                imgClipart = (ImageView) itemView.findViewById(R.id.imgClipart);
            }
        }

    }
    public void editGroupDialog(final GroupModel gm){
        final MaterialDialog m = new MaterialDialog.Builder(GroupActivity.this)
                .customView(R.layout.dialog_addgroup,true)
                .show();

        final EditText grpName = (EditText) m.findViewById(R.id.di_grpName);
        grpName.setText(gm.getName());
        Button add = (Button) m.findViewById(R.id.di_add);
        add.setText("UPDATE");
        final ImageView clipSel = (ImageView) m.findViewById(R.id.clipArtSelect);
        final RecyclerView clipArt = (RecyclerView) m.findViewById(R.id.di_clipArt);

        clipSel.setVisibility(View.VISIBLE);
        Drawable d = getResources().getDrawable(Utils.getImageResourse(gm.getIcon()));
        clipSel.setImageDrawable(d);

        clipArt.setVisibility(View.GONE);

        clipSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clipSel.setVisibility(View.GONE);
                clipArt.setVisibility(View.VISIBLE);

                LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
                lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                clipArt.setItemAnimator(new DefaultItemAnimator());
                clipArt.setLayoutManager(lm);
                final ClipArtAdapter ad = new ClipArtAdapter(getApplicationContext(), Utils.getImageResourse());
                clipArt.setAdapter(ad);
            }
        });

        ItemClickSupport.addTo(clipArt).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                pos = position;
                clipSel.setVisibility(View.VISIBLE);
                clipArt.setVisibility(View.GONE);
                Drawable d = getResources().getDrawable(Utils.getImageResourse(position));
                clipSel.setImageDrawable(d);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grpName.getText().toString().isEmpty()){
                    grpName.setError("This fiels can't be empty");
                }
                else {
                    db.editGroup(new GroupModel(pos,grpName.getText().toString(),gm.getId()));
                    Intent in = new Intent(getApplicationContext(),GroupActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });
    }

    public void logoutThis(){
        finish();
    }
}
