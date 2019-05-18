package tw.kauhiant.testsqlite;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static DataBaseHealper dbh;

    TabHost tabHost;

    ImageButton addAccount;
    Vibrator vibrator;
   // ArrayList<Account> test;
    AccountList list;
    AccountAdaptor adaptor;

    TextView sum;
    TextView sumOfIncome;
    TextView sumOfOutgo;

    Button setTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(dbh == null)
            dbh = new DataBaseHealper(MainActivity.this, "DB",null,1);

        tabHost = findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("accountstab").setIndicator("帳目").setContent(R.id.accountstab));
        tabHost.addTab(tabHost.newTabSpec("settingtab").setIndicator("設定").setContent(R.id.settingtab));


        addAccount = findViewById(R.id.addAccount);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

      //  test = new ArrayList<Account>();
        list = new AccountList();

        adaptor = new AccountAdaptor(MainActivity.this,list.list);
        ListView accountList = findViewById(R.id.accountlist);
        accountList.setAdapter(adaptor);

        addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FixAccount.class);
                intent.putExtra("accountId",-1);
                startActivity(intent);
            }
        });

        sum = findViewById(R.id.sum);
        sumOfIncome = findViewById(R.id.income);
        sumOfOutgo  = findViewById(R.id.outgo);

        setTag = findViewById(R.id.settag);
        setTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SetTag.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        dataUpdate();
    }

    public void dataUpdate(){
        dbh.query2List(list.list,"");
        int[] sumOf = list.sum();
        sumOfIncome.setText(String.valueOf(sumOf[0]));
        sumOfOutgo.setText(String.valueOf(sumOf[1]));
        sum.setText(String.valueOf(sumOf[0] - sumOf[1]));
        adaptor.notifyDataSetChanged();
    }
}
