package com.me.keepme.keepme.util;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.me.keepme.keepme.model.DataModel;
import com.me.keepme.keepme.model.GroupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiginVp on 6/29/2017.
 */

public class Appdb extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KEEPME_DB";
    private static final int DATABASE_VERSION = 10;

    private static final String GROUP_TB = "GroupTable";
    private static final String G_ID = "id";
    private static final String G_NAME = "Name";
    private static final String G_ICON = "Icon";

    private static final String DATA_TB = "DataTable";
    private static final String D_ID = "id";
    private static final String D_NAME = "Name";
    private static final String D_UNAME = "Username";
    private static final String D_PASS = "Password";
    private static final String D_RECOVERY = "Recovery";
    private static final String D_NOTES = "Notes";
    private static final String D_GROUPID = "GroupId";

    private static final String SUG_TB = "suggessionTable";
    private static final String S_ID = "id";
    private static final String S_TXT = "text";



    public Appdb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createGroupTable = "CREATE TABLE "+GROUP_TB+"("+G_ID+" INTEGER PRIMARY KEY,"+G_NAME+" TEXT,"+G_ICON+" INTEGER DEFAULT 0 "+")";
        db.execSQL(createGroupTable);

        String createDataTable = "CREATE TABLE "+DATA_TB+"("+D_ID+" INTEGER PRIMARY KEY,"+D_NAME+" TEXT,"+D_UNAME+" TEXT,"+D_PASS+" TEXT,"+D_RECOVERY+" TEXT,"+D_NOTES+" TEXT,"+D_GROUPID+" INTEGER"+")";
        db.execSQL(createDataTable);

        String sugTable = "CREATE TABLE "+SUG_TB+"("+S_ID+" INTEGER PRIMARY KEY,"+S_TXT+" TEXT"+")";
        db.execSQL(sugTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Find any bugs occured. changed EXITS to EXISTS

        String Q = "CREATE TABLE HELLO(HID INTEGER PRIMARY KEY ,NAME TEXT)";
        db.execSQL(Q);

        /*String createGroupTable = "CREATE TABLE "+GROUP_TB+"("+G_ID+" INTEGER PRIMARY KEY,"+G_NAME+" TEXT,"+G_ICON+" INTEGER DEFAULT 0 "+")";
        db.execSQL(createGroupTable);

        String createDataTable = "CREATE TABLE "+DATA_TB+"("+D_ID+" INTEGER PRIMARY KEY,"+D_NAME+" TEXT,"+D_UNAME+" TEXT,"+D_PASS+" TEXT,"+D_RECOVERY+" TEXT,"+D_NOTES+" TEXT,"+D_GROUPID+" INTEGER"+")";
        db.execSQL(createDataTable);

        String sugTable = "CREATE TABLE "+SUG_TB+"("+S_ID+" INTEGER PRIMARY KEY,"+S_TXT+" TEXT"+")";
        db.execSQL(sugTable);*/
        /*db.execSQL("DROP TABLE IF EXISTS "+GROUP_TB);
        db.execSQL("DROP TABLE IF EXISTS "+DATA_TB);
        db.execSQL("DROP TABLE IF EXISTS "+SUG_TB);*/
        //db.execSQL("DROP TABLE IF EXISTS HELLO");
    }

    //version 4
    public long setName(){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(G_NAME,"Hello table name");

        return (db.insert("HELLO",null,values));
    }
    public String getName(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("select NAME from HELLO where NAME='Hello table name'",null);
        if(c.moveToFirst()){
            return c.getString(0);
        }
        return "";
    }


    /* Group details table method starts here . */

    //get all group details.
    public List<GroupModel> getAllGroup(){
        List<GroupModel>list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM "+GROUP_TB;
        Cursor c = db.rawQuery(Query,null);
        if(c.moveToFirst()){
            do {
                GroupModel gm = new GroupModel();
                gm.setId(c.getInt(0));
                gm.setName(c.getString(1));
                gm.setIcon(c.getInt(2));

                Log.e("value",c.getInt(0)+"");
                Log.e("value",c.getString(1));
                list.add(gm);
            }while (c.moveToNext());
        }
        return list;
   }

   //insert data to group
    public long insertGroup(GroupModel gm){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(G_NAME,gm.getName());
        values.put(G_ICON,gm.getIcon());

        return (db.insert(GROUP_TB,null,values));
    }

    //delete data from group
    public void deleteGroup(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "DELETE FROM "+GROUP_TB+" WHERE "+G_ID+" ='"+id+"'";
        db.execSQL(Query);
    }

    //edit data
    public void editGroup(GroupModel gm){
        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("UPDATE "+GROUP_TB+" SET "+G_NAME+" ='"+gm.getName()+"',"+G_ICON+"='"+gm.getIcon()+"'"+" WHERE "+G_ID+"='"+gm.getId()+"'");
    }

    /* method for groups ends here */

    /* Data table method starts here */

    //get all data
    public List<DataModel> getAllDatas(String grpId){
        List<DataModel>list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String Query = "SELECT * FROM "+DATA_TB+" WHERE "+D_GROUPID+" ='"+grpId+"'";
        Cursor c = db.rawQuery(Query,null);
        if(c.moveToFirst()){
            do {
                DataModel dm = new DataModel();
                dm.setId(c.getInt(0));
                dm.setName(c.getString(1));
                dm.setUsername(c.getString(2));
                dm.setPassword(c.getString(3));
                dm.setRecovery(c.getString(4));
                dm.setNotes(c.getString(5));
                dm.setGroupId(String.valueOf(c.getInt(6)));

                list.add(dm);
            }while (c.moveToNext());
        }
        return list;
    }

    //insert data
    public long insertData(DataModel dm){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(D_NAME,dm.getName());
        values.put(D_UNAME,dm.getUsername());
        values.put(D_PASS,dm.getPassword());
        values.put(D_RECOVERY,dm.getRecovery());
        values.put(D_NOTES,dm.getNotes());
        values.put(D_GROUPID,dm.getGroupId());

        return (db.insert(DATA_TB,null,values));
    }

    //delete data
    public void deleteData(String  id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+DATA_TB+" WHERE "+D_ID+" ='"+id+"'");
        //return (db.delete(DATA_TB,D_ID,new String[]{id}));
    }

    //edit data
    public void editData(DataModel dm){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(D_NAME,dm.getName());
        values.put(D_UNAME,dm.getName());
        values.put(D_PASS,dm.getPassword());
        values.put(D_RECOVERY,dm.getRecovery());
        values.put(D_NOTES,dm.getNotes());
        values.put(D_GROUPID,dm.getGroupId());

        db.execSQL("UPDATE "+DATA_TB+" SET "+D_NAME+"='"+dm.getName()+"' , "+D_UNAME+"='"+dm.getUsername()+"', "+D_PASS+"='"+dm.getPassword()+"' , "+D_RECOVERY+"='"+dm.getRecovery()+"' , "+D_NOTES+"='"+dm.getNotes()+"'"+" WHERE "+D_ID+" ='"+dm.getId()+"'" );
        Log.e("query","UPDATE "+DATA_TB+" SET "+D_NAME+"='"+dm.getName()+"' , "+D_UNAME+"='"+dm.getUsername()+"' , "+D_PASS+"='"+dm.getPassword()+"' , "+D_RECOVERY+"='"+dm.getRecovery()+"' , "+D_NOTES+"='"+dm.getNotes()+"'"+" WHERE "+D_ID+" ='"+dm.getId()+"'");
    }

    /* methods for data ends here */

    /* mehtods for suggesions table*/

    //to add suggesion
    public long addsugessions(String txt){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(S_TXT,txt);

        return (db.insert(SUG_TB,null,values));
    }

    //to get all suggesions
    public List<String[]> getSugessions(){
        List<String[]>list = new ArrayList<>();

        String Query = "SELECT * FROM "+SUG_TB;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(Query,null);

        if(c.moveToFirst()){
            do{
                String [] data = new String[]{String.valueOf(c.getInt(0)),c.getString(1)};
                list.add(data);
            }while (c.moveToNext());
        }

        return list;
    }

    //delete suggessions
    public void deleteSugession(String  id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+DATA_TB+" WHERE "+D_ID+" ='"+id+"'");
    }
}