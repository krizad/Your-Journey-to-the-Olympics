package com.olympics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Account extends FileManager {
    private String accNo,userID,password;
    private static int  timeCnt;
    private int cardCnt = 0,accountStatus,bookCount = 0;
    private ArrayList<Integer[]> bookingList = new ArrayList<>();
    private ArrayList<Integer> busBookedID= new ArrayList<>();
    private ArrayList<String[]> card = new ArrayList<>();
    private ArrayList<String[]> busBookedSeat = new ArrayList<>();
    private String[] selectedCard = new String[2];

    public Account(int index) {
        this.accNo = super.getAccounts().get(index)[0];
        this.userID = super.getAccounts().get(index)[1];
        this.password = super.getAccounts().get(index)[2];
        this.accountStatus = 0;
    }

    public Account(String userID,String password){
        this.accNo = getAccounts().size()+1+"";
        this.userID = userID;
        this.password = password;
    }

    public Account(String userID,String password,String card,String csvCode){
        this.accNo = getAccounts().size()+1+"";
        this.userID = userID;
        this.password = password;
        addCard(card,csvCode);
    }

    public ArrayList<Integer[]> getBookingList() {
        return bookingList;
    }

    public ArrayList<Integer[]> getBookingListNormal() {
        ArrayList<Integer[]> bookingListNormal = new ArrayList<>();
        for (Integer[] a:getBookingList()) {
            if (a[0] == 0){
                bookingListNormal.add(a);
            }
        }

        return bookingListNormal;
    }

    public ArrayList<Integer[]> getBookingListCancel() {
        ArrayList<Integer[]> bookingListCancel = new ArrayList<>();
        for (Integer[] a:getBookingList()) {
            if (a[0] == 1){
                bookingListCancel.add(a);
            }
        }

        return bookingListCancel;
    }

    public void sortBookingListByFirst(){
        Collections.sort(getBookingList(), new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[4].compareTo(o2[4]);
            }
        });
    }

    public void sortBookingListByStatus(){
        Collections.sort(getBookingList(), new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o1[0].compareTo(o2[0]);
            }
        });
    }

    public void sortBookingListByLast(){
        Collections.sort(getBookingList(), new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o2[4].compareTo(o1[4]);
            }
        });
    }

    public void sortBookingListByDepartTime(){
        Collections.sort(getBookingList(), new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return new Bus(o1[2]-1).getDepart().compareTo(new Bus(o2[2]-1).getDepart());
            }
        });
    }

    public void sortBookingListByArriveTime(){
        Collections.sort(getBookingList(), new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return new Bus(o1[2]-1).getArrive().compareTo(new Bus(o2[2]-1).getArrive());
            }
        });
    }

    public String getSelectedCardNO() {
        if (hasCard()){
            return "***********"+selectedCard[0].substring(12);
        }
        else {
            return "";
        }

    }

    public String getSelectedCardCsv(){
        return selectedCard[1];
    }

    public void setSelectedCard(String cardNO,String csv) {this.selectedCard = new String[]{cardNO,csv}; }

    public void addBookingList(int status, int eventID, int busID, String seatPositiion, int bookCount) {
        this.bookCount = bookCount;
        busBookedID.add(busID);
        //0 normal 1 cancel
        bookingList.add(new Integer[]{status, eventID, busID,bookCount,timeCnt++});
        busBookedSeat.add(new String[]{busID+"",seatPositiion});
    }

    public void setBookingList(Event event,Bus bus,String seatPosition){
        for (String[] a:busBookedSeat) {
            if (a[0].equals(bus.getBusID()+"")){
                a[1] +=  seatPosition;
            }
        }
    }

    public int getBookCount(Event event,Bus bus) {
        int bookCount = 0;
        for (Integer[] a:bookingList) {
            if (event.getEventID() == a[1] && bus.getBusID() == a[2]){
                bookCount = a[3];
            }
        }
        return bookCount;}

    public boolean canBook(Event event,Bus bus){
        boolean isCanBook = true;
        int bookCount = getBookCount(event,bus);
        for (int busID:busBookedID) {
            if (bus.getBusID() == busID && bookCount>=2){
                isCanBook = false;
            }
            else if (bookCount<2){
                isCanBook = true;
            }
            else {isCanBook = true;}
        }
        return isCanBook;
    }

    public boolean isInBookList(Event event, Bus bus){
        for (Integer[] a:bookingList) {
            if (event.getEventID() == a[1] && bus.getBusID() == a[2]){
                return true;
            }
            else {return false;}
        }
        return false;
    }

    public String getSeatBookedPosition(Bus bus){
        String seatPosition = "";
        String busId = bus.getBusID()+"";
        for (String[] a:busBookedSeat) {
            if (a[0].equals(busId)){
                seatPosition = a[1];
            }
        }
        return seatPosition;
    }

    public void cancelBook(int position){
        this.bookingList.get(position)[0] = 1;
        this.bookingList.get(position)[3] = 0;

        //this.bookingList.get(booklistIndex)[0] = 1;
        //this.bookingList.get(booklistIndex)[3] = 0;

    }

    public void addCard(String card,String csvCode){
        cardCnt++;
        String cnt = ""+cardCnt;
        String[] c = {cnt,card,csvCode};
        this.card.add(c);
        setSelectedCard(card,csvCode);
    }

    public void removeCard(String[] card){
        this.card.remove(card);
    }

    public void setPassword(String newPassword){
        this.password = newPassword;
    }

    public boolean hasCard(){
        return cardCnt >0;
    }

    public int getAccNo() { return Integer.parseInt(accNo); }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getCard() {
        String cardDetail = "";
        for (String[] s:card) {
            String card = "************"+s[1].substring(12);
            cardDetail += "Card "+s[0]+" : "+card+"\n";
        }
        return cardDetail;
    }

    public void logIn(){
        this.accountStatus = 1;
    }

    public void logOut(){
        this.accountStatus = 0;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    @Override
    public String toString(){
        return "Account "+getAccNo()+" : "+getUserID()+",\t"+getPassword()+"\n";
    }
}
