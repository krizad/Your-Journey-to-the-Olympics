package com.olympics.ui.busToEvent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.olympics.Bus;
import com.olympics.R;

import java.util.List;

public class BusToEventRecycleAdapter  extends RecyclerView.Adapter<BusToEventRecycleAdapter.Holder>{
    Context mContext;
    ItemClickListener mListener;
    List<Bus> mData;
    public BusToEventRecycleAdapter(Context mContext, List<Bus> mData){
        this.mContext = mContext;
        this.mData = mData;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bus,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.time.setText(mData.get(position).getTimeTravel());
        holder.type.setText(mData.get(position).getBusType());
        if (mData.get(position).getSeatLeft() == 2){
            holder.seatLeft.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        holder.seatLeft.setText(mData.get(position).getSeatLeft()+" OUT OF "+mData.get(position).getSeatTotal()+" LEFT");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setItemClickListener(ItemClickListener listener){
        this.mListener = listener;
    }

    class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView time,type,seatLeft;
        public Holder(@NonNull View itemView) {
            super(itemView);
            time = (TextView)itemView.findViewById(R.id.bus_time);
            type = (TextView)itemView.findViewById(R.id.bus_type);
            seatLeft = (TextView)itemView.findViewById(R.id.bus_seatLeft);
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
