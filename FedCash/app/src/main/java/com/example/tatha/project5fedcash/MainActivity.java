package com.example.tatha.project5fedcash;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tatha.Project5Common.IMyAidlInterface;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends Activity implements View.OnClickListener {

    Spinner sp;
    int position =0;
    int a;
    IMyAidlInterface mIRemoteService;
    EditText year_1, date_2, wdays_2, year_3;
    TextView tv;
    Button submit1;
    ArrayList<String> list = new ArrayList<String>();
    private boolean mIsBound;
    private int[] ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // connect to the service
        

        sp = findViewById(R.id.spinner);
        year_1 = findViewById(R.id.etYear1);
        year_3 = findViewById(R.id.etYear3);
        date_2 = findViewById(R.id.etDate2);
        wdays_2 = findViewById(R.id.etNumber2);
        submit1 = findViewById(R.id.bSubmit1);
        tv = findViewById(R.id.test);
        list.add("Monthly Cash");
        list.add("Daily Cash");
        list.add("Yearly Average");

        year_1.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        wdays_2.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        year_3.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });

        submit1.setOnClickListener(this);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {

                position = pos;
                switch (position) {


                    case 0:
                        year_1.setVisibility(View.VISIBLE);
                        year_3.setVisibility(View.GONE);
                        date_2.setVisibility(View.INVISIBLE);
                        wdays_2.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                        year_1.setVisibility(View.INVISIBLE);
                        year_3.setVisibility(View.GONE);
                        date_2.setVisibility(View.VISIBLE);
                        wdays_2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        year_1.setVisibility(View.INVISIBLE);
                        year_3.setVisibility(View.VISIBLE);
                        date_2.setVisibility(View.INVISIBLE);
                        wdays_2.setVisibility(View.INVISIBLE);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(IMyAidlInterface.class.getName());

        ResolveInfo info= getPackageManager().resolveService(intent,Context.BIND_AUTO_CREATE);
        intent.setComponent(new ComponentName(info.serviceInfo.packageName,info.serviceInfo.name));

            /*intent.setComponent(new ComponentName("com.example.tatha.project5treasuryserv",
                    " com.example.tatha.project5treasuryserv.MyTreasuryService"));*/
        mIsBound= bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsBound) {

            Intent intent = new Intent(IMyAidlInterface.class.getName());

            ResolveInfo info= getPackageManager().resolveService(intent,Context.BIND_AUTO_CREATE);
            intent.setComponent(new ComponentName(info.serviceInfo.packageName,info.serviceInfo.name));

            /*intent.setComponent(new ComponentName("com.example.tatha.project5treasuryserv",
                    " com.example.tatha.project5treasuryserv.MyTreasuryService"));*/
            mIsBound= bindService(intent, this.mConnection, Context.BIND_AUTO_CREATE);

            if(mIsBound){
                Log.d("TJ","TJ");
            }
            else {
                Log.d("TJ","fail");
            }
        }
        //boolean b = false;


        //mConnection = new DataConnection();
        //Intent intent = new Intent(IMyAidlInterface.class.getName());


        //intent.setPackage("com.example.tatha.Project5treasutyserv");

        //intent.setComponent(new ComponentName("com.example.tatha.project5treasuryserv", " com.example.tatha.project5treasuryserv.MyTreasuryService"));
        //b = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        /*if (b) {
            Log.i("TJ in onResume", "bind service success");
        } else {
            Log.i("TJ in onResume", "bind service failure");
        }*/
    }

    void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public void onClick(View view) {


        // Call KeyGenerator and get a new ID
        if (mIsBound) {



            Thread t1 = new Thread(new run1(position));
            t1.start();


        }
        else if(!mIsBound){

        }

    }

    /**
     * Inner class used to connect to UserDataService
     */
    class DataConnection implements ServiceConnection {

        /**
         * is called once the bind succeeds
         */
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIRemoteService = IMyAidlInterface.Stub.asInterface(service);
            //if(mIRemoteService!=null)
            mIsBound = true;


        }

        /*** is called once the remote service is no longer available */
        public void onServiceDisconnected(ComponentName name) { //
            mIRemoteService = null;
            mIsBound = false;
        }

    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {

            mIRemoteService = IMyAidlInterface.Stub.asInterface(iservice);

            mIsBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {

            mIRemoteService = null;

            mIsBound = false;

        }
    };


    class run1 implements Runnable {
        int p;
        run1(int pos){
            p=pos;
        }

        @Override
        public void run() {

            if(mIsBound){
                try {


                    switch (p) {
                        case 0:
                            String result1="";
                            
                            final int s1 =Integer.parseInt(year_1.getText().toString());
                            ab = mIRemoteService.monthlyCash(s1);
                            for(int i = 0;i<ab.length;i++){
                                result1 += ab[i] + " ";

                            }
                            final String s = String.valueOf(ab[0]);
                            final String finalResult = result1;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    tv.setText(finalResult);


                                    try {
                                        sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                    Intent i = new Intent(MainActivity.this,ResultActivity.class);
                                    i.putExtra("input","MonthlyCash "+s1);
                                    i.putExtra("out","MonthlyCashResult "+ finalResult);
                                    startActivity(i);
                                }
                            });
                            break;
                        case 1:
                            final int[] b;
                            final String[] date;
                            String result2 = "";
                            final String s2 = date_2.getText().toString();
                            final String s3 = wdays_2.getText().toString();
                            date=s2.split("/");
                            b = mIRemoteService.dailyCash(Integer.parseInt(date[1]), Integer.parseInt(date[0]), Integer.parseInt(date[2]), Integer.parseInt(s3));
                            for(int i = 0;i<b.length;i++){
                                result2 += b[i] + " ";

                            }
                            final String finalResult2 = result2;
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    tv.setText(String.valueOf(finalResult2));


                                    try {
                                        sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }


                                    Intent i = new Intent(MainActivity.this,ResultActivity.class);
                                    i.putExtra("input","DailyCash  "+s2+" "+s3);
                                    i.putExtra("out","MonthlyCashResult "+finalResult2);

                                    startActivity(i);
                                }
                            });
                            break;
                        case 2:
                            final int c;
                            final String s4= year_3.getText().toString();
                            c = mIRemoteService.yearlyAvg(Integer.parseInt(s4));
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    tv.setText(String.valueOf(c));

                                    try {
                                        sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    Intent i = new Intent(MainActivity.this,ResultActivity.class);
                                    i.putExtra("input","YearlyAverage  "+s4);
                                    i.putExtra("out","YearlyAverageResult "+c);
                                    startActivity(i);
                                }
                            });
                            break;

                    }
                }catch (RemoteException re){}
            }

            else{
                Log.i("TJ in onClick", "bind service failure");
            }

        }
    }



}
