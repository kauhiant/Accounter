package tw.kauhiant.testsqlite;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SetTag extends AppCompatActivity {

    ImageButton addTag;
    Button yes;
    Button close;
    LinearLayout function;
    EditText tagName;
    TextView tagColor;
    RadioGroup colorGroup;

    int checkedTagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_tag);

        addTag = findViewById(R.id.addtag);
        yes = findViewById(R.id.yes);
        close = findViewById(R.id.close);
        function = findViewById(R.id.function);
        tagName = findViewById(R.id.tagname);
        tagColor = findViewById(R.id.tagcolor);
        colorGroup = findViewById(R.id.colorgroup);

        addTag.bringToFront();
        addTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchButton2Func("", Color.BLACK);;
            }
        });

        ListView listView = findViewById(R.id.taglist);
        final ArrayList<Tag> tagList = new ArrayList<Tag>();
        MainActivity.dbh.updateTagList(tagList);
        final SetTagAdaptor adaptor = new SetTagAdaptor(this,tagList);
        listView.setAdapter(adaptor);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFunc2Button();
                int color =  ((ColorDrawable)tagColor.getBackground()).getColor();

                if(checkedTagId == 0)
                    MainActivity.dbh.addTag(tagName.getText().toString(), color);
                else
                    MainActivity.dbh.updateTag(checkedTagId,tagName.getText().toString(), color);

                MainActivity.dbh.updateTagList(tagList);
                adaptor.notifyDataSetChanged();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchFunc2Button();
            }
        });


        colorGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checked = findViewById(radioGroup.getCheckedRadioButtonId());
                tagColor.setBackgroundColor(((ColorDrawable)checked.getBackground()).getColor());
            }
        });
    }

    public void switchFunc2Button(){
        function.setVisibility(View.GONE);
        addTag.setVisibility(View.VISIBLE);
    }

    public void switchButton2Func(String tagName, int tagColor){
        addTag.setVisibility(View.GONE);
        this.tagName.setText(tagName);
        this.tagColor.setBackgroundColor(tagColor);
        checkedTagId = 0;
        function.setVisibility(View.VISIBLE);
    }
}
