package tw.kauhiant.testsqlite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kauhia on 2018/5/29.
 */

public class SetTagAdaptor extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Tag> tagList;


    public SetTagAdaptor(Context context,ArrayList<Tag> tagList){
        this.context = context;
        this.tagList = tagList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return tagList.size()-1;
    }

    @Override
    public Object getItem(int i) {
        return tagList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tagList.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.settag, viewGroup,false);
        LinearLayout base = v.findViewById(R.id.base);
        final TextView name = v.findViewById(R.id.tagname);
        ImageButton edit = v.findViewById(R.id.edittag);
        ImageButton del  = v.findViewById(R.id.deltag);

        final Tag t = tagList.get(i+1);

        base.setBackgroundColor(t.color);
        name.setText(t.name);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("標籤設定");
                builder.setView(R.layout.fixtag);
                builder.setPositiveButton("確定",null);
                builder.setNegativeButton("取消",null);
                builder.show();*/
                SetTag parent = (SetTag)context;
                parent.switchButton2Func(t.name,t.color);
                parent.checkedTagId=t.id;
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.dbh.deleteTag(t.id);

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("刪除").setMessage("確定要刪除嗎?");
                dialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.dbh.deleteTag(t.id);
                        MainActivity.dbh.updateTagList(SetTagAdaptor.this.tagList);
                        SetTagAdaptor.this.notifyDataSetChanged();
                    }
                });

                dialog.setNegativeButton("取消", null);

                dialog.show();

            }
        });

        return v;
    }
}
