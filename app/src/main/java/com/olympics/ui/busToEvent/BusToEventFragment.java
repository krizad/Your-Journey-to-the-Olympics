package com.olympics.ui.busToEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.olympics.Bus;
import com.olympics.Event;
import com.olympics.GlobalClass;
import com.olympics.R;
import com.olympics.ui.bookSeat.BookSeatFragment;

import java.util.ArrayList;

public class BusToEventFragment extends Fragment {
    private RecyclerView busRecycleView;
    private TextView eventDetail;
    private int index = 0;
    private ArrayList<Bus> busList = new ArrayList<>();
    private Event eventSelected;
    private GlobalClass globalClass;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bus_to_event, container, false);
        eventDetail = (TextView)root.findViewById(R.id.event_detail);
        busRecycleView = (RecyclerView)root.findViewById(R.id.bus_recycle);
        final String detail = "Bus to "+eventSelected.getSport()+"\n"+eventSelected.getDiscipline()+"\n"+"Category : "+eventSelected.getCategory()+"\n"+eventSelected.getDay()+"/"+eventSelected.getMonth()+"/"+eventSelected.getYear();
        eventDetail.setText(detail);

        BusToEventRecycleAdapter recycleViewAdapter = new BusToEventRecycleAdapter(getContext(),busList);
        busRecycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplication()));
        busRecycleView.setAdapter(recycleViewAdapter);

        recycleViewAdapter.setItemClickListener(new BusToEventRecycleAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                BookSeatFragment bookSeatFragment = new BookSeatFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("busID",busList.get(position).getBusID());
                bundle.putInt("eventID",eventSelected.getEventID());
                bookSeatFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, bookSeatFragment).addToBackStack(null).commit();
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalClass = (GlobalClass)getContext().getApplicationContext();
        eventSelected = globalClass.getEvents(getArguments().getInt("eventID"));
        busList = globalClass.getBusToEvent(eventSelected.getEventID());
    }
}

