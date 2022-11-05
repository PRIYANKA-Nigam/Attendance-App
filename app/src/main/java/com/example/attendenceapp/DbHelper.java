package com.example.attendenceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String CLASS_TABLE_NAME="CLASS_TABLE";
   public static final String c_id="c_id";
    public static final String CLASS_NAME_KEY="CLASS_NAME";
    public static final String CLASS_SUBJECT_KEY="SUBJECT";
    public static final String STUDENT_TABLE_NAME="STUDENT_TABLE";
 public static final String s_id="s_id";
    public static final String STUDENT_NAME_KEY="STUDENT_NAME";
    public static final String STUDENT_ROLL_KEY="ROLL";
    public static final String STATUS_TABLE_NAME="STATUS_TABLE";
    public static final String status_id="status_id";
    public static final String STATUS_KEY="STATUS";

    public DbHelper(@Nullable Context context) {
        super(context, "Student.db", null, 1);
    }
    private static final String CREATE_CLASS_TABLE="CREATE TABLE "+ CLASS_TABLE_NAME+"( "+
            c_id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            CLASS_NAME_KEY+" TEXT NOT NULL, "+
            CLASS_SUBJECT_KEY+" INTEGER, "+"UNIQUE ("+CLASS_NAME_KEY+","+CLASS_SUBJECT_KEY+")"+");";

    private static final String CREATE_STUDENT="CREATE TABLE "+ STUDENT_TABLE_NAME+"( "+
            s_id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            c_id+" INTEGER NOT NULL, "+
            STUDENT_NAME_KEY+" TEXT NOT NULL, "+
            STUDENT_ROLL_KEY+" INTEGER, "+
            " FOREIGN KEY ( "+c_id+") REFERENCES "+CLASS_TABLE_NAME+"("+c_id+")"+");";

    private static final String DROP_CLASS_TABLE="DROP TABLE IF EXISTS "+CLASS_TABLE_NAME;
    private static final String SELECT_CLASS_TABLE="SELECT * FROM "+CLASS_TABLE_NAME;
    private static final String DROP_STUDENT_TABLE="DROP TABLE IF EXISTS "+STUDENT_TABLE_NAME;
    private static final String SELECT_STUDENT_TABLE="SELECT * FROM "+STUDENT_TABLE_NAME;

    private static final String CREATE_STATUS_TABLE="CREATE TABLE "+ STATUS_TABLE_NAME+"( "+
            status_id+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
            s_id+" INTEGER NOT NULL, "+
            STATUS_KEY+" TEXT NOT NULL, "+
            " UNIQUE ("+s_id+"),"+
            " FOREIGN KEY ("+s_id+") REFERENCES "+
            STUDENT_TABLE_NAME+"( "+s_id+")"+");";
    private static final String DROP_STATUS_TABLE="DROP TABLE IF EXISTS "+STATUS_TABLE_NAME;
    private static final String SELECT_STATUS_TABLE="SELECT * FROM "+STATUS_TABLE_NAME;
    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL(CREATE_STUDENT);
       db.execSQL(CREATE_STATUS_TABLE);
       db.execSQL(CREATE_CLASS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       try {
            db.execSQL(DROP_STUDENT_TABLE);
            db.execSQL(DROP_STATUS_TABLE);
            db.execSQL(DROP_CLASS_TABLE);
        }catch (SQLException e){
           e.printStackTrace();
       }
    }
    long addClass(String className,String sub){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CLASS_NAME_KEY,className);
        values.put(CLASS_SUBJECT_KEY,sub);
        return database.insert(CLASS_TABLE_NAME,null,values);
    }
    int deleteClass(long cid){
        SQLiteDatabase database=this.getReadableDatabase();
       return  database.delete(CLASS_TABLE_NAME,c_id+"=?",new String[]{String.valueOf(cid)});
    }
    Cursor getClassTable(){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.rawQuery(SELECT_CLASS_TABLE,null);
    }
    long addStudent(long cid,String name,int roll){
        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(c_id,cid);
        values.put(STUDENT_NAME_KEY,name);
        values.put(STUDENT_ROLL_KEY,roll);
        return database.insert(STUDENT_TABLE_NAME,null,values);
    }
    Cursor getStudentTable(long cid){
        SQLiteDatabase database=this.getReadableDatabase();
        return  database.query(STUDENT_TABLE_NAME,null,c_id+"=?",new String[]{String.valueOf(cid)},null,null,STUDENT_ROLL_KEY);
    }
    int deleteStudent(long sid){
        SQLiteDatabase database=this.getReadableDatabase();
        return database.delete(STUDENT_TABLE_NAME,s_id+"=?",new String[]{String.valueOf(sid)});
    }

    long addStatus(long sid,String status){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(s_id,sid);
        values.put(STATUS_KEY,status);
        return database.insert(STATUS_TABLE_NAME,null,values);
    }
    String getStatus(long sid){
        String status=null;
        SQLiteDatabase database=this.getReadableDatabase();
        String whereClause=s_id+"="+sid;
        Cursor cursor=database.query(STATUS_TABLE_NAME,null,whereClause,null,null,null,null);
        if (cursor.moveToFirst())
            status=cursor.getString(cursor.getColumnIndex(STATUS_KEY));
        return status;
    }

}
