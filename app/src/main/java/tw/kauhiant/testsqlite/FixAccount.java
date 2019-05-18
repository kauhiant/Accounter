package tw.kauhiant.testsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FixAccount extends AppCompatActivity {

    Account tmpData;
    ArrayList<Tag> tagList;
    TagAdaptor adaptor;

    EditText name;
    DatePicker datePicker;
    EditText money;
    RadioGroup isincome;
    Spinner tag;

    Button yes;
    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_account);

        name = findViewById(R.id.accountname);
        datePicker = findViewById(R.id.datepicker);
        money = findViewById(R.id.acounntmoney);
        isincome = findViewById(R.id.isincome);

        tag = findViewById(R.id.accounttag);

        yes = findViewById(R.id.yes);
        close = findViewById(R.id.close);

        final int accountId = getIntent().getIntExtra("accountId", -1);
        if (accountId == -1) // add
        {
            tmpData = new Account(0, "", 0, 0);
        }else    // update
        {
            tmpData = MainActivity.dbh.findAccount(accountId);
        }

        name.setText(tmpData.name);

        Date date = tmpData.date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        datePicker.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));


        money.setText(String.valueOf(tmpData.money));

        tagList = new ArrayList<Tag>();
        MainActivity.dbh.updateTagList(tagList);
        adaptor = new TagAdaptor(this,tagList);
        tag.setAdapter(adaptor);
        tag.setSelection(adaptor.getPositionById(tmpData.tag));

        tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tmpData.tag = (int)l;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tmpData.tag = 0;
            }
        });


        if(tmpData.isIncome)
            isincome.check(R.id.income);
        else
            isincome.check(R.id.notincome);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tmpData.name = name.getText().toString();
                tmpData.money=Integer.valueOf(money.getText().toString());

                String date = String.format("%4d-%2d-%2d",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
                try {
                    tmpData.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(isincome.getCheckedRadioButtonId() == R.id.income)
                    tmpData.isIncome = true;
                else
                    tmpData.isIncome = false;

                if(accountId == -1)
                    MainActivity.dbh.addAcount(tmpData);
                else
                    MainActivity.dbh.updateAccount(accountId,tmpData);

                finish();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
