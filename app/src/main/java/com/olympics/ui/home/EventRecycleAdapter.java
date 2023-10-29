package com.olympics.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.olympics.Event;
import com.olympics.R;

import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.MyViewHolder> {
    Context mContext;
    List<Event> mData;
    ItemClickListener mListener;
    public EventRecycleAdapter(Context mContext, List<Event> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_date.setText(mData.get(position).getDay()+"/"+mData.get(position).getMonth()+"/"+mData.get(position).getYear());
        holder.tv_sport.setText(mData.get(position).getSport());
        holder.tv_discipline.setText(mData.get(position).getDiscipline());
        holder.tv_category.setText(mData.get(position).getCategory());
        holder.tv_venue.setText("At : "+mData.get(position).getVenue());
        holder.tv_start_time.setText("Start time : "+mData.get(position).getStartTime());
        holder.img.setImageResource(mData.get(position).getPhoto_icon());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setItemClickListener(ItemClickListener listener){
        this.mListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_date,tv_sport,tv_discipline,tv_category,tv_venue,tv_start_time;
        private ImageView img;
        private LinearLayout item_Event;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.event_date);
            tv_sport = (TextView) itemView.findViewById(R.id.event_sport);
            tv_discipline = (TextView) itemView.findViewById(R.id.event_discipline);
            tv_category = (TextView) itemView.findViewById(R.id.event_category);
            tv_venue = (TextView) itemView.findViewById(R.id.event_venue);
            tv_start_time = (TextView) itemView.findViewById(R.id.event_start_time);
            img = (ImageView) itemView.findViewById(R.id.event_img);
            item_Event = (LinearLayout) itemView.findViewById(R.id.event_item);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            if(mListener!=null)
                mListener.onItemClick(getAdapterPosition());
        }
    }
    public interface ItemClickListener{
        void onItemClick(int position);
    }
}

