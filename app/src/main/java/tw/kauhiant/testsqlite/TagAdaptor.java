package tw.kauhiant.testsqlite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kauhia on 2018/5/28.
 */

public class TagAdaptor extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Tag> tagList;


    public TagAdaptor(Context context,ArrayList<Tag> tagList){
        this.context = context;
        this.tagList = tagList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return tagList.size();
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
        View v = inflater.inflate(R.layout.tag, viewGroup,false);
        TextView tv = v.findViewById(R.id.text);

        final Tag t = tagList.get(i);
        tv.setText(t.name);
        tv.setTextColor(t.color);

        return v;
    }

    public int getPositionById(long id){
        for(int i=0; i<tagList.size(); ++i){
            if(tagList.get(i).id == id)
                return i;
        }
        return 0;
    }
}
