package tw.kauhiant.testsqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.SimpleFormatter;
import java.util.zip.Inflater;

/**
 * Created by kauhia on 2018/5/27.
 */

public class AccountAdaptor extends BaseAdapter {

    ArrayList<Account> accountList;
    LayoutInflater inflater;
    Context context;

    public AccountAdaptor(Context context, ArrayList<Account> accountList){
        this.accountList = accountList;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.accountList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.accountList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.account,viewGroup,false);
        final Account account = accountList.get(i);

        TextView money = v.findViewById(R.id.money);
        TextView name  = v.findViewById(R.id.name);
        TextView tag   = v.findViewById(R.id.tag);
        TextView date  = v.findViewById(R.id.date);

        ImageButton modify = v.findViewById(R.id.modify);
        ImageButton delete = v.findViewById(R.id.delete);

        modify.setBackgroundColor(Color.alpha(0));
        delete.setBackgroundColor(Color.alpha(0));

        if(account.money >= 500){
            int dotNum = account.money/100 % 10;
            money.setText(String.valueOf(account.money/1000) + "." + String.valueOf(dotNum) + "k");
        }
        else
            money.setText(String.valueOf(account.money));

        if(account.isIncome)
            money.setTextColor(Color.BLUE);
        else
            money.setTextColor(Color.RED);

        name.setText(account.name);
        final Tag t = MainActivity.dbh.findTag(account.tag);
        tag.setText(t.name);
        tag.setTextColor(Color.WHITE);
        tag.setBackgroundColor(t.color);
        date.setText(new
                SimpleDateFormat("yyyy-MM-dd")
                .format(account.date));

        final int id = accountList.get(i).id;

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("刪除").setMessage("確定要刪除嗎?");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.dbh.deleteAccount(id);
                        ((MainActivity)context).dataUpdate();
                        Toast.makeText(context,String.format("刪除 %s",account.name),Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.show();
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,FixAccount.class);
                intent.putExtra("accountId",id);
                context.startActivity(intent);
            }
        });

        return v;
    }
}
