package com.olympics;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.olympics.ui.booking.BookingFragment;
import com.olympics.ui.home.EventFragment;
import com.olympics.ui.profile.LogInFragment;
import com.olympics.ui.profile.ProfileFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String allLine = "";
    private GlobalClass globalClass;
    private ArrayList<Event> eventList = new ArrayList<>();;
    private ArrayList<Account> accountList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalClass = (GlobalClass) getApplicationContext();
        readFile();
        listCreated();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navListener);
        EventFragment eventFragment = new EventFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,eventFragment).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new EventFragment();
                            break;
                        case R.id.navigation_booking:
                            selectedFragment = new BookingFragment();
                            break;
                        case R.id.navigation_profile:
                            if (globalClass.isLogIn()){
                                selectedFragment = new ProfileFragment();
                            }
                            else {
                                selectedFragment = new LogInFragment();
                            }

                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,selectedFragment).commit();
                    return true;
                }
            };


    @Override
    protected void onPause() {
        super.onPause();
        String textOutput = "";
        for (Account account:globalClass.getAccounts()) {
            textOutput+= account.toString()+"\n";
            for (Integer[] index:account.getBookingList()) {
                if (index[0] == 0){
                    textOutput+="\t"+globalClass.getEvents(index[1]).toString();
                    textOutput+="\t"+globalClass.getBusToEvent((index[1])).get(globalClass.getBusToEvent((index[1])).indexOf(new Bus(index[2]))).toString();
                }
            }

        }
        writeOutput(textOutput);

        new FileManager().removeAll();

    }

    public void readFile(){
        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        InputStream is;
        try {
            if (firstStart){
                is = getApplicationContext().getResources().openRawResource(R.raw.input);
                this.allLine = "";
            }
            else {
                is = openFileInput("input.txt");
                this.allLine = "";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while (reader.ready()) {
                line = reader.readLine() + "\n";
                this.allLine+=line;
                if (line.contains("//")){continue;}
                if (line.contains("Event")){
                    line = line.replace("Event","");
                    line = line.replace(":",",");
                    String[] lineArr = line.split(",",9);
                    for (int i = 0; i < lineArr.length; i++) {
                        lineArr[i] = lineArr[i].trim();
                    }

                    new FileManager().addEvents(lineArr);
                }
                else if (line.contains("Bus")){
                    line = line.replace("Bus","");
                    line = line.replace(":",",");
                    String[] lineArr = line.split(",",8);
                    for (int i = 0; i < lineArr.length; i++) {
                        lineArr[i] = lineArr[i].trim();
                    }
                    new FileManager().addBuses(lineArr);
                }
                else if (line.contains("Account")){
                    line = line.replace("Account","");
                    line = line.replace(":",",");
                    String[] lineArr = line.split(",",3);
                    for (int i = 0; i < lineArr.length; i++) {
                        lineArr[i] = lineArr[i].trim();
                    }
                    new FileManager().addAccounts(lineArr);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (firstStart){
            try {
                String data = this.allLine;
                FileOutputStream fileOutputStream = openFileOutput("input.txt", Context.MODE_PRIVATE);
                fileOutputStream.write(data.getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            prefs = getSharedPreferences("prefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }

    }

    public void listCreated(){
        for (int i = 0; i < new FileManager().getEvents().size() ; i++) {
            eventList.add(new Event(i));
        }
        for (int i = 0; i < new FileManager().getAccounts().size() ; i++) {
            accountList.add(new Account(i));
        }

        globalClass.setEvents(eventList);
        globalClass.setAccounts(accountList);
        globalClass.setBusToEvent1(globalClass.getEvents(1).getBusToEvent());
        globalClass.setBusToEvent2(globalClass.getEvents(2).getBusToEvent());
        globalClass.setBusToEvent3(globalClass.getEvents(3).getBusToEvent());
        globalClass.setBusToEvent4(globalClass.getEvents(4).getBusToEvent());
        globalClass.setBusToEvent5(globalClass.getEvents(5).getBusToEvent());
        globalClass.setBusToEvent6(globalClass.getEvents(6).getBusToEvent());
        globalClass.setBusToEvent7(globalClass.getEvents(7).getBusToEvent());
        globalClass.setBusToEvent8(globalClass.getEvents(8).getBusToEvent());
        globalClass.setBusToEvent9(globalClass.getEvents(9).getBusToEvent());
        globalClass.setBusToEvent10(globalClass.getEvents(10).getBusToEvent());
        globalClass.setBusToEvent11(globalClass.getEvents(11).getBusToEvent());
        globalClass.addAllLine(allLine);
    }

    public void writeOutput(String text){
            try {
                FileOutputStream fileOutputStream = openFileOutput("output.txt", Context.MODE_PRIVATE);
                fileOutputStream.write(text.getBytes());
                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


    }

}


