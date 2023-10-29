package com.olympics.ui.bookSeat;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.olympics.Account;
import com.olympics.Bus;
import com.olympics.Event;
import com.olympics.GlobalClass;
import com.olympics.R;

import java.util.ArrayList;

public class BookSeatFragment extends Fragment {
    private Bus bus;
    private Event event;
    private Account accountLoggedIn;
    private TextView eventDetailBook,dia_busType,dia_destination,dia_event,dia_discipline,dia_category,dia_price,dia_time,dia_seatPosition;
    private String seatPosition = "";
    private ArrayList<ImageView> seatList = new ArrayList<>();
    private int[] seatStatus;
    private int bookCount;
    private Dialog bookDialog;
    private Button confirm,cancel,book;
    private GlobalClass globalClass;
    private EditText dia_confirm_csv;
    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_bookseat,container,false);

        eventDetailBook = (TextView)root.findViewById(R.id.event_detail_book);
        eventDetailBook.setText(bus.toString());

        book = (Button)root.findViewById(R.id.bookBtn);


        addSeatList(bus.getBusType());

        onClickSeat();

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookCount==0){
                    toastCenter("Please book the seat");
                }
                else {
                    if (globalClass.isLogIn() && accountLoggedIn.hasCard()){
                        if (bookCount==0){
                            toastCenter("Please book the seat");
                        }
                        else if (accountLoggedIn.canBook(event,bus)){
                            confirmDialog();
                        }
                        else {
                            toastCenter("cannot book more than 2 seat in 1 bus");
                        }
                        eventDetailBook.setText(bus.toString());
                    }
                    else if (!globalClass.isLogIn()){
                        toastCenter("Please Login");
                        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                        navView.setSelectedItemId(R.id.navigation_profile);
                    }
                    else {
                        toastCenter("Please add the card");
                        dialogAddCard();
                    }
                }

        }
        });

        return root;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass)getContext().getApplicationContext();
        event = globalClass.getEvents(getArguments().getInt("eventID"));
        for (Bus b :globalClass.getBusToEvent(event.getEventID())) {
            if (b.getBusID() == getArguments().getInt("busID")){
                bus = b;
            }
        }
        accountLoggedIn = globalClass.getAccountLogIn();
        seatStatus = bus.getSeatStatus();
        if (globalClass.isLogIn()){bookCount = accountLoggedIn.getBookCount(event,bus);}
    }

    public void confirmDialog(){
        bookDialog = new Dialog(getContext());
        bookDialog.setContentView(R.layout.dialog_confirm_book);
        dia_busType = (TextView)bookDialog.findViewById(R.id.dia_busType);
        dia_destination = (TextView)bookDialog.findViewById(R.id.dia_destination);
        dia_event = (TextView)bookDialog.findViewById(R.id.dia_event);
        dia_discipline = (TextView)bookDialog.findViewById(R.id.dia_discipline);
        dia_category = (TextView)bookDialog.findViewById(R.id.dia_category);
        dia_time = (TextView)bookDialog.findViewById(R.id.dia_time_travel);
        dia_seatPosition = (TextView)bookDialog.findViewById(R.id.dia_seat_position);
        dia_price = (TextView)bookDialog.findViewById(R.id.dia_price);
        dia_confirm_csv = (EditText)bookDialog.findViewById(R.id.dia_confirm_csv);
        cancel = (Button) bookDialog.findViewById(R.id.cancel);
        confirm = (Button)bookDialog.findViewById(R.id.confirm);
        dia_busType.setText(bus.getBusType());
        dia_destination.setText(bus.getDestination());
        dia_event.setText(event.getSport());
        dia_discipline.setText(event.getDiscipline());
        dia_category.setText(event.getCategory());
        dia_time.setText(bus.getTimeTravel());

        dia_price.setText(bus.getCost()*bookCount+" Baht");

        for (ImageView seat:seatList) {
            //check if seat booking (green color)

            if (seatStatus[seatList.indexOf(seat)] == 1 ){
                seatPosition += bus.getSeatPosition(seatList.indexOf(seat)) + " ";
            }
            dia_seatPosition.setText("Selected seat : "+seatPosition.trim());
        }
        seatPosition = "";

        //confirm book
        bookDialog.show();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dia_confirm_csv.getText().toString().equals(accountLoggedIn.getSelectedCardCsv())){
                    for (ImageView seat:seatList) {
                        //check if seat booking (green color)
                        if (seatStatus[seatList.indexOf(seat)] == 1 ){
                            seat.setImageResource(R.drawable.seat_booked);
                            bus.setSeatStatus(seatList.indexOf(seat),2);
                            bus.bookSeat();
                            seatPosition += bus.getSeatPosition(seatList.indexOf(seat)) + " ";
                            eventDetailBook.setText(bus.toString());
                            bookDialog.cancel();
                        }
                    }
                    //check if this account has book this bus
                    if (accountLoggedIn.isInBookList(event,bus)){
                        accountLoggedIn.setBookingList(event,bus,seatPosition);
                    }
                    else {
                        accountLoggedIn.addBookingList(0,event.getEventID(),bus.getBusID(),seatPosition,bookCount);
                    }
                    globalClass.fromBooking();
                    BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                    navView.setSelectedItemId(R.id.navigation_booking);
                }
                else {
                    toastCenter("Incorrect Card CSV");}
            }
        });
        //cancel book
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookDialog.cancel();
            }
        });

    }

    public void onClickSeat(){

        for(final ImageView seat:seatList) {
            seat.setImageResource(R.drawable.seat_notbook);
            if (seatStatus[seatList.indexOf(seat)] == 2 ){
                seat.setImageResource(R.drawable.seat_booked);
                seat.setClickable(false);
            }
            else if (seatStatus[seatList.indexOf(seat)] != 2 ){
                seat.setClickable(true);
                seat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!globalClass.isLogIn()){
                            toastCenter("Please Login");
                            BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);
                            navView.setSelectedItemId(R.id.navigation_profile);
                        }
                        else {
                            if (!accountLoggedIn.canBook(event,bus)){
                                toastCenter("Cannot book more than 2 seat in 1 bus");
                            }
                            else if (seatStatus[seatList.indexOf(seat)] == 0 && bookCount<2){
                                seat.setImageResource(R.drawable.seat_booking);
                                bus.setSeatStatus(seatList.indexOf(seat),1);
                                bookCount++;
                            }
                            else if (seatStatus[seatList.indexOf(seat)] == 1){
                                seat.setImageResource(R.drawable.seat_notbook);
                                bus.setSeatStatus(seatList.indexOf(seat),0);
                                bookCount--;
                            }
                            else if (bookCount==2){
                                toastCenter("You cannot book mare than 2 seat");
                            }
                        }

                    }
                });
            }
        }

    }

    private void addSeatList(String busType) {

        if (busType.equals("Type A")){
            seatList.add((ImageView) root.findViewById(R.id.seat1));
            seatList.add((ImageView) root.findViewById(R.id.seat2));
            seatList.add((ImageView) root.findViewById(R.id.seat3));
            seatList.add((ImageView) root.findViewById(R.id.seat4));
            seatList.add((ImageView) root.findViewById(R.id.seat5));
            seatList.add((ImageView) root.findViewById(R.id.seat6));
            seatList.add((ImageView) root.findViewById(R.id.seat7));
            seatList.add((ImageView) root.findViewById(R.id.seat8));
            seatList.add((ImageView) root.findViewById(R.id.seat9));
            seatList.add((ImageView) root.findViewById(R.id.seat10));
            seatList.add((ImageView) root.findViewById(R.id.seat11));
            seatList.add((ImageView) root.findViewById(R.id.seat12));
            seatList.add((ImageView) root.findViewById(R.id.seat13));
            seatList.add((ImageView) root.findViewById(R.id.seat14));
            seatList.add((ImageView) root.findViewById(R.id.seat15));
            seatList.add((ImageView) root.findViewById(R.id.seat16));
            seatList.add((ImageView) root.findViewById(R.id.seat17));
            seatList.add((ImageView) root.findViewById(R.id.seat18));
            seatList.add((ImageView) root.findViewById(R.id.seat19));
            seatList.add((ImageView) root.findViewById(R.id.seat20));
        }
        else if (busType.equals("Type B")){
            seatList.add((ImageView) root.findViewById(R.id.seat2));
            seatList.add((ImageView) root.findViewById(R.id.seat3));
            seatList.add((ImageView) root.findViewById(R.id.seat6));
            seatList.add((ImageView) root.findViewById(R.id.seat7));
            seatList.add((ImageView) root.findViewById(R.id.seat10));
            seatList.add((ImageView) root.findViewById(R.id.seat11));
            seatList.add((ImageView) root.findViewById(R.id.seat14));
            seatList.add((ImageView) root.findViewById(R.id.seat15));
            seatList.add((ImageView) root.findViewById(R.id.seat18));
            seatList.add((ImageView) root.findViewById(R.id.seat19));
        }

    }

    public void toastCenter(String text){

        Toast toast = Toast.makeText(getContext().getApplicationContext(),text,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }

    public void dialogAddCard(){

        final Dialog addCard = new Dialog(getContext());
        addCard.setContentView(R.layout.dialog_add_card);
        final EditText cardNoInput = (EditText)addCard.findViewById(R.id.dia_cardNO);
        final EditText csvInput = (EditText)addCard.findViewById(R.id.dia_card_csv);
        Button add = (Button)addCard.findViewById(R.id.dia_add_card_confirm);
        Button cancelAdd = (Button)addCard.findViewById(R.id.dia_add_card_cancel);
        addCard.show();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardNoInput.getText().toString().length() != 16 ){
                    Toast.makeText(getContext().getApplicationContext(),"Incorrect Card Number",Toast.LENGTH_SHORT).show();
                }
                else if (csvInput.getText().toString().length() != 3){
                    Toast.makeText(getContext().getApplicationContext(),"Incorrect Card csv",Toast.LENGTH_SHORT).show();
                }
                else {
                    accountLoggedIn.addCard(cardNoInput.getText().toString(),csvInput.getText().toString());
                    confirmDialog();
                    addCard.cancel();
                }

            }
        });
        cancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard.cancel();
            }
        });

    }
}
