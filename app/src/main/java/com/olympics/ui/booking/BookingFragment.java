package com.olympics.ui.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.olympics.Account;
import com.olympics.Bus;
import com.olympics.Event;
import com.olympics.GlobalClass;
import com.olympics.R;

import java.util.ArrayList;

public class BookingFragment extends Fragment {
    private RecyclerView bookingRecycleView;
    private ArrayList<Integer[]> bookingLst = new ArrayList<>();
    private Account accountLoggedIn;
    private GlobalClass globalClass;
    private View root;
    private Spinner spinnerStatus,spinnerTime;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_booking, container, false);
        globalClass = (GlobalClass) getContext().getApplicationContext();

        spinnerStatus();
        spinnerTime();

        return root;
    }
    public void sortStatus(String type){
        if (globalClass.isLogIn()){
            if (type.equals("All")){
                this.bookingLst = accountLoggedIn.getBookingList();
                accountLoggedIn.sortBookingListByStatus();
            }
            else if (type.equals("Normal")){
                this.bookingLst = accountLoggedIn.getBookingListNormal();
            }
            else if (type.equals("Cancel")){
                this.bookingLst = accountLoggedIn.getBookingListCancel();
            }
        }

    }

    public void sortTime(String type){
        if (globalClass.isLogIn()){
            if (type.equals("Booked Time")){
                accountLoggedIn.sortBookingListByLast();
            }
            else if (type.equals("Depart Time")){
                accountLoggedIn.sortBookingListByDepartTime();
            }
            else if (type.equals("Arrive Time")){
                accountLoggedIn.sortBookingListByArriveTime();
            }
        }

    }

    public void spinnerStatus(){

        //sort status
        spinnerStatus = root.findViewById(R.id.booking_sort_status);
        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(getContext().getApplicationContext(),R.array.ticketSortStatus,android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountLoggedIn = globalClass.getAccountLogIn();
                sortStatus(spinnerStatus.getSelectedItem().toString());
                BookingRecycleAdapter recycleAdapter = new BookingRecycleAdapter(getContext(),bookingLst);
                bookingRecycleView = (RecyclerView)root.findViewById(R.id.booking_recycle);
                bookingRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
                bookingRecycleView.setAdapter(recycleAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public void spinnerTime(){

        //sort time
        spinnerTime = root.findViewById(R.id.booking_sort_time);
        ArrayAdapter<CharSequence> adapterTime = ArrayAdapter.createFromResource(getContext().getApplicationContext(),R.array.ticketSortTime,android.R.layout.simple_spinner_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(adapterTime);
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                accountLoggedIn = globalClass.getAccountLogIn();
                sortTime(spinnerTime.getSelectedItem().toString());
                sortStatus(spinnerStatus.getSelectedItem().toString());
                BookingRecycleAdapter recycleAdapter = new BookingRecycleAdapter(getContext(),bookingLst);
                bookingRecycleView = (RecyclerView)root.findViewById(R.id.booking_recycle);
                bookingRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
                bookingRecycleView.setAdapter(recycleAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
