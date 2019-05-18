package tw.kauhiant.testsqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kauhia on 2018/5/27.
 */

public class DataBaseHealper extends SQLiteOpenHelper {

    public SQLiteDatabase editer;

    public DataBaseHealper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        editer =  context.openOrCreateDatabase(name, Context.MODE_PRIVATE, null);
        //editer.execSQL("drop table Account");
        //editer.execSQL("drop table Tag");
        onCreate(editer);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // id Account
        sqLiteDatabase.execSQL("CREATE  TABLE IF NOT exists Account " +
                "(id integer primary key autoincrement," +
                "name TEXT,"+
                "date TEXT NOT NULL , " +
                "tag INTEGER, " +
                "money INTEGER)");

        sqLiteDatabase.execSQL("create table if not exists Tag "+
                "(id integer primary key autoincrement," +
                "name text," +
                "color integer)");

        final Cursor c = sqLiteDatabase.rawQuery("select id from Tag where id = 0",null);
        if(c.getCount() == 0)
            sqLiteDatabase.execSQL(String.format("insert into Tag values ( 0 , 'default' , %d )",Color.BLACK));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addAcount(String name, Date date, int tag, int money){
        String dateStr = DateFormat.format("yyyy-MM-dd",date).toString();
        if(editer != null)
            editer.execSQL(String.format("insert into Account values( null, '%s' , '%s' , %d , %d )",name,dateStr,tag,money));
    }

    public void addAcount(Account account){
        if(!account.isIncome)
            account.money *= -1;
        addAcount(account.name,account.date,account.tag,account.money);
    }

    public  void  addTag(String name, int color){
        if(editer != null)
            editer.execSQL(String.format("insert into Tag values(null , '%s' , %d)",name, color));
    }

    public void query2List(ArrayList<Account> list, String where){
        //where = DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()).toString();
        Cursor cursor = editer.rawQuery("select * from Account  order by date",null);

        list.clear();
        while(cursor.moveToNext()){
            int    id   = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            int tag   = cursor.getInt(3);
            int money = cursor.getInt(4);
            boolean isIncome = money >= 0;
            if(!isIncome)
                money *= -1;

            try {
                list.add(new Account(id,name,tag,money,new SimpleDateFormat("yyyy-MM-dd").parse(date),isIncome));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateTagList(ArrayList<Tag> tagList){
        Cursor cursor = editer.rawQuery("select * from Tag",null);

        tagList.clear();

        while(cursor.moveToNext()){
            final int     id   = cursor.getInt(0);
            final String name = cursor.getString(1);
            final int     color = cursor.getInt(2);

            tagList.add(new Tag(id,name,color));
        }
    }

    public void deleteAccount(int accountId){
        editer.execSQL(String.format("delete from Account where id = %d",accountId));
    }

    public void deleteTag(long tagId){
        if(tagId == 0)return;
        editer.execSQL(String.format("update Account set tag = 0 where id in ( select id from Account where tag = %d )",tagId));
        editer.execSQL(String.format("delete from Tag where id = %d",tagId));
    }

    public void updateAccount(int accountId, Account data){
        String dateStr = DateFormat.format("yyyy-MM-dd",data.date).toString();
        int money = data.money;
        if(!data.isIncome)
            money *= -1;
        editer.execSQL(String.format("update Account  set name = '%s' , date = '%s' , tag = %d , money = %d   where id = %d",
                data.name,dateStr,data.tag,money,accountId));
    }

    public void updateTag(int tagId, String tagName, int tagColor){
        editer.execSQL(String.format("update Tag set name = '%s' , color = %d where id = %d " , tagName, tagColor, tagId));
    }

    public Account findAccount(int accountId){
        Cursor cursor = editer.rawQuery(String.format("select * from Account where id = %d",accountId),null);

        if(!cursor.moveToNext())return null;

        int    id   = cursor.getInt(0);
        String name = cursor.getString(1);
        String date = cursor.getString(2);
        int tag   = cursor.getInt(3);
        int money = cursor.getInt(4);
        boolean isIncome = (money >= 0);
        if(!isIncome)
            money *= -1;

        try {
            return new Account(id,name,tag,money,new SimpleDateFormat("yyyy-MM-dd").parse(date),isIncome);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Tag findTag(int tagId){
        Cursor cursor = editer.rawQuery(String.format("select * from Tag where id = %d",tagId),null);

        if(!cursor.moveToNext())return new Tag(0,"default", Color.BLACK);

        final int     id   = cursor.getInt(0);
        final String name = cursor.getString(1);
        final int     color = cursor.getInt(2);

        return new Tag(id,name,color);
    }
}
