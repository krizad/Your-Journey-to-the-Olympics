package com.olympics.ui.booking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.olympics.Account;
import com.olympics.Bus;
import com.olympics.Event;
import com.olympics.GlobalClass;
import com.olympics.R;

import java.util.ArrayList;

public class BookingRecycleAdapter extends RecyclerView.Adapter<BookingRecycleAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Integer[]> mData; //bookingList.add(new Integer[]{status, eventID, busID,bookCount,timeCnt++});
    private Event event;
    private Bus bus;
    private Account accountLoggedIn;
    private GlobalClass globalClass;
    public BookingRecycleAdapter(Context mContext, ArrayList<Integer[]> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_booking, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        globalClass = (GlobalClass)mContext.getApplicationContext();
        event = globalClass.getEvents(mData.get(position)[1]);
        for (Bus b :globalClass.getBusToEvent(event.getEventID())) {
            if (b.getBusID() == mData.get(position)[2]){
                bus = b;
            }
        }
        accountLoggedIn = globalClass.getAccountLogIn();
        final String seatBooked = accountLoggedIn.getSeatBookedPosition(bus);
        //from bookseat to booking new ticket is difference color
        if (globalClass.IsFromBook() && position == 0){
            holder.item.setBackgroundColor(mContext.getResources().getColor(R.color.colorNewItem));
        }
        //if status = 1 cancel ticket is gray
        if (mData.get(position)[0] == 1){
            holder.item.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            holder.status.setText("Cancel");
            holder.status.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.arrive.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.depart.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.event.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.discipline.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.category.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
            holder.busType.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        }
        else{
            holder.status.setText("Normal");
        }
        holder.arrive.setText("Arrive Time :"+bus.getArrive());
        holder.depart.setText("Depart Time : "+bus.getDepart());
        holder.event.setText("Event : "+event.getSport());
        holder.discipline.setText(event.getDiscipline());
        holder.category.setText(event.getCategory());
        holder.busType.setText("Bus Type : "+bus.getBusType());
        holder.seatNO.setText("Seat : "+seatBooked);
        if (mData.get(position)[0] == 0){
            holder.cancelBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event = globalClass.getEvents(mData.get(position)[1]);
                    for (Bus b :globalClass.getBusToEvent(event.getEventID())) {
                        if (b.getBusID() == mData.get(position)[2]){
                            bus = b;
                        }
                    }
                    accountLoggedIn.cancelBook(position);
                    for (String s:seatBooked.split(" ")) {
                        Toast.makeText(mContext.getApplicationContext(),bus.getSeatIndex(s)+"",Toast.LENGTH_SHORT).show();
                        bus.setSeatStatus(bus.getSeatIndex(s),0);
                        bus.cancelBook();
                    }
                    holder.item.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
                    holder.status.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    holder.arrive.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    holder.depart.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    holder.event.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    holder.discipline.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    holder.category.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    holder.busType.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                    //holder.item.removeAllViews();
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView status,arrive,depart,event,discipline,category,busType,seatNO;
        private ImageView cancelBook;
        private ConstraintLayout item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            status = (TextView)itemView.findViewById(R.id.booking_status);
            arrive = (TextView) itemView.findViewById(R.id.booking_arrive);
            depart = (TextView) itemView.findViewById(R.id.booking_depart);
            event = (TextView) itemView.findViewById(R.id.booking_event);
            discipline = (TextView) itemView.findViewById(R.id.booking_discipline);
            category = (TextView) itemView.findViewById(R.id.booking_category);
            busType = (TextView) itemView.findViewById(R.id.booking_busType);
            seatNO = (TextView) itemView.findViewById(R.id.booking_seatNO);
            item = (ConstraintLayout) itemView.findViewById(R.id.cardView);
            cancelBook = (ImageView) itemView.findViewById(R.id.booking_clear);
        }
    }


}


