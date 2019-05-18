package tw.kauhiant.testsqlite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by kauhia on 2018/5/29.
 */

public class FixTagDialogFrame extends DialogFragment {

    public static FixTagDialogFrame newInstance(String name, int color){
        FixTagDialogFrame frame = new FixTagDialogFrame();
        Bundle args = new Bundle();
        args.putString("name",name);
        args.putInt("color",color);
        frame.setArguments(args);
        return frame;
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fixtag, container, false);
        EditText name = v.findViewById(R.id.tagname);
        EditText color= v.findViewById(R.id.tagcolor);
        Bundle args = getArguments();
        name.setText(args.getString("name"));
        color.setBackgroundColor(args.getInt("color"));
        return v;
    }

    public Dialog onCreateDialog(Context context, Bundle bundle){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder.setView(inflater.inflate(R.layout.fixtag, null));
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("取消", null);
        return builder.create();
    }

}
