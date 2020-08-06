package com.me.keepme.keepme.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.me.keepme.keepme.Communicate;
import com.me.keepme.keepme.R;
import com.me.keepme.keepme.model.DataModel;
import com.me.keepme.keepme.util.Appdb;

import java.util.List;

/**
 * Created by JiginVp on 7/28/2017.
 */

public class PassAdapter extends RecyclerView.Adapter<PassAdapter.AppViewHolder>
{
    static int i = 1;
    static String grpId;
    Context context;
    List<DataModel>list;
    public PassAdapter(Context context,List<DataModel>list,String grpId){
        this.context = context;
        this.grpId = grpId;
        this.list = list;
    }
    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view_pass,parent,false);
        return new AppViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final AppViewHolder holder, final int position) {
        //Adding a button on top of the list.
       /*if(position == 0){

        }*/

        holder.m.setText(list.get(position).getName());
        holder.p.setText(list.get(position).getPassword());

        holder.i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context,holder.i);

                menu.getMenuInflater().inflate(R.menu.popup_menu_2, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.showPopup:
                                showDt(list.get(position));
                                break;
                            case R.id.editPopup:
                                editDialog(list.get(position));
                                break;
                            case R.id.delPopup:
                                AlertDialog.Builder b = new AlertDialog.Builder(context);
                                b.setTitle("Delete?");
                                b.setMessage("Are you sure you want to delete.?");
                                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Appdb db = new Appdb(context);
                                        db.deleteData(list.get(position).getId()+"");
                                        list.remove(position);
                                        notifyDataSetChanged();
                                    }
                                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                b.show();
                                break;
                        }
                        return true;
                    }
                });

                menu.show();
            }
        });

        //to change the password and name viseversa.
        holder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i % 2 != 0 ){
                    i++;
                    holder.l1.setVisibility(View.VISIBLE);
                    holder.l2.setVisibility(View.GONE);
                }else{
                    i++;
                    holder.l2.setVisibility(View.VISIBLE);
                    holder.l1.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {
        LinearLayout l1,l2,l;
        ImageView i;
        TextView g;
        TextView m;
        TextView p;
        public AppViewHolder(View itemView) {
            super(itemView);
            l = (LinearLayout) itemView.findViewById(R.id.lay);
            l1 = (LinearLayout) itemView.findViewById(R.id.layOne);
            l2 = (LinearLayout) itemView.findViewById(R.id.layTwo);
            i = (ImageView) itemView.findViewById(R.id.mailIcon);
            g = (TextView) itemView.findViewById(R.id.grpId);
            m = (TextView) itemView.findViewById(R.id.mailId);
            p = (TextView) itemView.findViewById(R.id.pass);
        }
    }

    private void editDialog(DataModel dm){
        final MaterialDialog m = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_add_pass, true)
                .show();

        final int id = dm.getId();

        final EditText n, u, p, r, no;
        Button b;

        n = (EditText) m.findViewById(R.id.name);
        u = (EditText) m.findViewById(R.id.username);
        p = (EditText) m.findViewById(R.id.password);
        r = (EditText) m.findViewById(R.id.recoveryMail);
        no = (EditText) m.findViewById(R.id.notes);

        n.setText(dm.getName());
        u.setText(dm.getUsername());
        p.setText(dm.getPassword());
        r.setText(dm.getRecovery());
        no.setText(dm.getNotes());


        b = (Button) m.findViewById(R.id.addBt);

        b.setText("Update");

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
                    dm.setGroupId(grpId);
                    dm.setId(id);

                    Appdb db = new Appdb(context);
                    db.editData(dm);

                    m.dismiss();

                    Communicate m = (Communicate) context;
                    m.goToViewPageActivity();
                } else {
                    Toast.makeText(context, "Field can't be empty", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void showDt(DataModel dm){
        MaterialDialog m = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_show_dt, true)
                .show();
        TextView n, u, p, r, no;

        n = (TextView) m.findViewById(R.id.name);
        u = (TextView) m.findViewById(R.id.username);
        p = (TextView) m.findViewById(R.id.password);
        r = (TextView) m.findViewById(R.id.recoveryMail);
        no = (TextView) m.findViewById(R.id.notes);

        n.setText(dm.getName());
        u.setText(dm.getUsername());
        p.setText(dm.getPassword());
        r.setText(dm.getRecovery());
        no.setText(dm.getNotes());
    }
}
