package tw.kauhiant.testsqlite;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kauhia on 2018/5/26.
 */

public class Account {
    public int id;
    public String name;
    public int tag;
    public Date date;
    public int money;
    public boolean isIncome;

    public Account(int id, String name, int tag, int money, Date date, boolean isIncome){
        this.id     = id;
        this.name  = name;
        this.tag    = tag;
        this.money = money;
        this.date = date;
        this.isIncome = isIncome;
    }

    public Account(int id, String name, int tag, int money){
        this.id     = id;
        this.name  = name;
        this.tag    = tag;
        this.money = money;
        this.isIncome  = false;
        this.date = Calendar.getInstance().getTime();
    }

    public Account copy(){
        return new Account(this.id,this.name,this.tag,this.money,this.date,this.isIncome);
    }
}
