package com.olympics;

import android.app.Application;

import java.util.ArrayList;

public class FileManager extends MainActivity {
    private static ArrayList<String[]> events = new ArrayList();
    private static ArrayList<String[]> buses = new ArrayList();
    private static ArrayList<String[]> accounts = new ArrayList();

    public FileManager(){}

    public ArrayList<String[]> getEvents() {
        return events;
    }

    public ArrayList<String[]> getBuses() {
        return buses;
    }

    public ArrayList<String[]> getAccounts() {
        return accounts;
    }

    public void addEvents(String[] arr) {
            this.events.add(arr);
    }

    public void addBuses(String[] arr) {
        this.buses.add(arr);

    }

    public void addAccounts(String[] arr) {
        this.accounts.add(arr);

    }
    public void removeAll(){
        events.clear();
        buses.clear();
        accounts.clear();
    }
}
