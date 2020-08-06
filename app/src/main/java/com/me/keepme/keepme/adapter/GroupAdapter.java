package com.me.keepme.keepme.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.me.keepme.keepme.Communicate;
import com.me.keepme.keepme.R;
import com.me.keepme.keepme.model.GroupModel;
import com.me.keepme.keepme.ui.GroupActivity;
import com.me.keepme.keepme.util.Appdb;
import com.me.keepme.keepme.util.ItemClickSupport;
import com.me.keepme.keepme.util.Utils;

import java.util.List;

/**
 * Created by JiginVp on 6/29/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.AppViewholder> {
    Context context;
    List<GroupModel>list;
    public GroupAdapter(Context context, List<GroupModel>list){
        this.context = context;
        this.list = list;
    }

    @Override
    public AppViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_group,parent,false);
        return new AppViewholder(view);
    }

    @Override
    public void onBindViewHolder(final AppViewholder holder, final int position) {
        if(position == 0){
            holder.edit.setVisibility(View.GONE);
            Drawable d = context.getResources().getDrawable(R.drawable.add);
            holder.img.setImageDrawable(d);
            return;
        }
        holder.id.setText(list.get(position).getId()+"");
        holder.tv.setText(list.get(position).getName());
        Drawable d = context.getResources().getDrawable(Utils.getImageResourse(list.get(position).getIcon()));
        holder.img.setImageDrawable(d);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(context,holder.edit);

                menu.getMenuInflater().inflate(R.menu.popup_menu, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.editPopup:

                                Communicate c = (Communicate)context;
                                c.showGroupEditDialog(list.get(position));

                                break;
                            case R.id.delPopup:
                                Appdb db = new Appdb(context);
                                db.deleteGroup(holder.id.getText().toString());
                                list.remove(position);
                                notifyDataSetChanged();
                                break;
                        }
                        return true;
                    }
                });

                menu.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AppViewholder extends RecyclerView.ViewHolder {
        ImageView img,edit;
        TextView tv;
        TextView id;
        public AppViewholder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.grpId);
            img = (ImageView) itemView.findViewById(R.id.img);
            tv = (TextView) itemView.findViewById(R.id.txt);
            edit = (ImageView) itemView.findViewById(R.id.edit);
        }
    }

    public class ClipArtAdapter extends RecyclerView.Adapter<AppViewholder>{
        private Context ctx;
        private int[]img;
        public ClipArtAdapter(Context ctx,int[] img){
            this.img = img;
            this.ctx = ctx;
        }

        @Override
        public GroupAdapter.AppViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_clipart,parent,false);
            return new GroupAdapter.AppViewholder(v);
        }

        @Override
        public void onBindViewHolder(GroupAdapter.AppViewholder holder, int position) {
            Drawable d = ctx.getResources().getDrawable(img[position]);
            //holder.imgClipart.setImageDrawable(d);

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
}
