package com.olympics;

import android.app.Application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GlobalClass extends Application {
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Account> accounts = new ArrayList<>();
    private Account accountLogIn;
    private String allLine;
    private boolean isFromBooking,isLogIn;
    private ArrayList<Bus> busToEvent1,busToEvent2,busToEvent3,busToEvent4,busToEvent5,busToEvent6,busToEvent7,busToEvent8,busToEvent9,busToEvent10,busToEvent11;


    public ArrayList<Event> getEvents() {
        return events;
    }

    public Event getEvents(int eventID) {
        for (Event event:this.events) {
            if (event.getEventID() == eventID){
                return event;
            }
        }
        return this.events.get(eventID);
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public void addEvents(Event event) {
        this.events.add(event);
    }

    public void sortEventByTimeStart(){
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        });
    }

    public void sortEventByDate(){
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getDay().compareTo(o2.getDay());
            }
        });
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getMonth().compareTo(o2.getMonth());
            }
        });
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Account getAccounts(int accountNO) {
        for (Account account:accounts) {
            if (account.getAccNo() == accountNO){
                return account;
            }
        }
        return accounts.get(accountNO-1);
    }

    public Account getAccountLogIn(){
        for (Account acc:accounts) {
            if (acc.getAccountStatus() == 1){
                this.accountLogIn = acc;
            }
        }
        return this.accountLogIn;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public boolean isLogIn(){
        return isLogIn;
    }

    public void logIn(){
        this.isLogIn = true;
    }

    public void logOut(){
        this.isLogIn = false;
    }

    public boolean IsFromBook(){
        if (isFromBooking){
            this.isFromBooking = false;
            return true;}
        else {return false;}
    }

    public void fromBooking(){
        isFromBooking = true;
    }

    public String getAllLine() {
        return allLine;
    }

    public void addAllLine(String allLine) {
        this.allLine += allLine;
    }

    public ArrayList<Bus> getBusToEvent(int eventID) {
        if (eventID == 1){return busToEvent1;}
        else if (eventID == 2){return busToEvent2;}
        else if (eventID == 3){return busToEvent3;}
        else if (eventID == 4){return busToEvent4;}
        else if (eventID == 5){return busToEvent5;}
        else if (eventID == 6){return busToEvent6;}
        else if (eventID == 7){return busToEvent7;}
        else if (eventID == 8){return busToEvent8;}
        else if (eventID == 9){return busToEvent9;}
        else if (eventID == 10){return busToEvent10;}
        else if (eventID == 11){return busToEvent11;}
        else {return busToEvent11;}
    }

    public void setBusToEvent1(ArrayList<Bus> busToEvent1) {
        this.busToEvent1 = busToEvent1;
    }

    public void setBusToEvent2(ArrayList<Bus> busToEvent2) {
        this.busToEvent2 = busToEvent2;
    }

    public void setBusToEvent3(ArrayList<Bus> busToEvent3) {
        this.busToEvent3 = busToEvent3;
    }

    public void setBusToEvent4(ArrayList<Bus> busToEvent4) {
        this.busToEvent4 = busToEvent4;
    }

    public void setBusToEvent5(ArrayList<Bus> busToEvent5) {
        this.busToEvent5 = busToEvent5;
    }

    public void setBusToEvent6(ArrayList<Bus> busToEvent6) {
        this.busToEvent6 = busToEvent6;
    }

    public void setBusToEvent7(ArrayList<Bus> busToEvent7) {
        this.busToEvent7 = busToEvent7;
    }

    public void setBusToEvent8(ArrayList<Bus> busToEvent8) {
        this.busToEvent8 = busToEvent8;
    }

    public void setBusToEvent9(ArrayList<Bus> busToEvent9) {
        this.busToEvent9 = busToEvent9;
    }

    public void setBusToEvent10(ArrayList<Bus> busToEvent10) {
        this.busToEvent10 = busToEvent10;
    }

    public void setBusToEvent11(ArrayList<Bus> busToEvent11) {
        this.busToEvent11 = busToEvent11;
    }

}
