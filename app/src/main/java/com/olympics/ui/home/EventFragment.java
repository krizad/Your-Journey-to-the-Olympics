package com.olympics.ui.home;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.olympics.Event;
import com.olympics.GlobalClass;
import com.olympics.R;
import com.olympics.ui.busToEvent.BusToEventFragment;


import static com.olympics.R.id.book_button;

public class EventFragment extends Fragment {
    private RecyclerView eventRecycleView;
    private Dialog eventDialog;
    private GlobalClass globalClass;
    private Event eventSelected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        globalClass = (GlobalClass)getContext().getApplicationContext();
        globalClass.sortEventByTimeStart();
        globalClass.sortEventByDate();

        final EventRecycleAdapter recycleAdapter = new EventRecycleAdapter(getContext(),globalClass.getEvents());
        eventRecycleView = (RecyclerView)root.findViewById(R.id.home_recycle);
        eventRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecycleView.setAdapter(recycleAdapter);

        recycleAdapter.setItemClickListener(new EventRecycleAdapter.ItemClickListener() {
            @Override
            public void onItemClick(final int position) {
                eventSelected = recycleAdapter.mData.get(position);
                showEventDialog();
            }
        });
    return root;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showEventDialog(){
        eventDialog = new Dialog(getContext());
        eventDialog.setContentView(R.layout.dialog_event);
        //setDialog
        ImageView dia_photo = (ImageView) eventDialog.findViewById(R.id.dia_photo);
        TextView dia_sport = (TextView) eventDialog.findViewById(R.id.dia_sport);
        TextView dialog_discipline = (TextView) eventDialog.findViewById(R.id.dia_discipline);
        TextView dia_category = (TextView) eventDialog.findViewById(R.id.dia_category);
        TextView dia_venue = (TextView) eventDialog.findViewById(R.id.dia_venue);
        TextView dia_date = (TextView) eventDialog.findViewById(R.id.dia_date);
        TextView dia_startTime = (TextView) eventDialog.findViewById(R.id.dia_startTime);
        TextView dia_duration = (TextView) eventDialog.findViewById(R.id.dia_duration);
        TextView dia_busTravelTime = (TextView) eventDialog.findViewById(R.id.dia_busTravelTime);
        Button book = (Button)eventDialog.findViewById(book_button);
        dia_photo.setImageResource(eventSelected.getPhoto_title());
        dia_sport.setText(eventSelected.getSport());
        dialog_discipline.setText(eventSelected.getDiscipline());
        dia_category.setText(eventSelected.getCategory());
        dia_venue.setText("Venue : " + eventSelected.getVenue());
        dia_date.setText("Date : " + eventSelected.getDate());
        dia_startTime.setText("Start Time : " + eventSelected.getStartTime());
        dia_duration.setText("Duration : " + eventSelected.getDurationHours()+" hours "+eventSelected.getDurationMin()+" minutes");
        dia_busTravelTime.setText("Travel By Bus : " + eventSelected.getBusTravelTime() + " minutes");
        eventDialog.show();
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusToEventFragment busToEventFragment = new BusToEventFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("eventID",eventSelected.getEventID());
                busToEventFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, busToEventFragment).addToBackStack(null).commit();
                eventDialog.cancel();

            }
        });

    }
}
