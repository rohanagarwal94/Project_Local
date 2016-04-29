package com.dbms.rohanpc.bankingsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView user,balance;
    Button deposit,withdraw,logout;
    BankDb db;
    Intent i;
    Double num1,num2;
    String username;
    int flag;
    Random r;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user=(TextView)findViewById(R.id.textView2);
        balance=(TextView)findViewById(R.id.textView4);
        deposit=(Button)findViewById(R.id.button);
        withdraw=(Button)findViewById(R.id.button2);
        db=new BankDb(getApplicationContext());
        logout=(Button)findViewById(R.id.button3);
        r=new Random();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(p);
            }
        });

        i=getIntent();
        username=i.getStringExtra("user");
        flag=i.getIntExtra("flag",0);
        user.setText(username);
        balance.setText(db.getBalance(username));



        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = r.nextInt(2);
                if (flag == 1)
                    Toast.makeText(getApplicationContext(), "Reading in Progress!Try Again.", Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    View subView = inflater.inflate(R.layout.depositdialog, null);
                    final EditText dep = (EditText) subView.findViewById(R.id.dialogEditText);


                    builder.setTitle("Deposit Amount");
                    builder.setView(subView);


                    builder.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            num1 = Double.parseDouble(dep.getText().toString());
                            num2 = Double.parseDouble(balance.getText().toString());
                            num2 = num1 + num2;
                            balance.setText(num2.toString());
                            db.update(username, num2.intValue());
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=r.nextInt(2);
                if (flag == 1)
                    Toast.makeText(getApplicationContext(), "Reading in Progress!Try Again.", Toast.LENGTH_SHORT).show();
                else

                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View subView = inflater.inflate(R.layout.withdrawdialog, null);
                final EditText dep = (EditText) subView.findViewById(R.id.dialogEditText2);


                builder.setTitle("Withdraw Amount");
                builder.setView(subView);


                builder.setPositiveButton("ENTER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        num1 = Double.parseDouble(dep.getText().toString());
                        num2 = Double.parseDouble(balance.getText().toString());
                        if (num2 > num1) {
                            num2 = num2 - num1;
                            balance.setText(num2.toString());
                            db.update(username, num2.intValue());
                        } else
                            Toast.makeText(getApplicationContext(), "Insufficient balance", Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        }
        });



    }
}
