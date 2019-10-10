package com.mad.android.parkingtracker.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mad.android.parkingtracker.Model.Receipt;
import com.mad.android.parkingtracker.R;

import java.util.ArrayList;

public class ParkingReportAdapter extends RecyclerView.Adapter<ParkingReportAdapter.ParkingReportViewHolder> {

    private ArrayList<Receipt> listOfReceipts = new ArrayList<Receipt>();
    final private ListItemClickListener mOnClickListener;


    public ParkingReportAdapter(ArrayList<Receipt> receipts, ListItemClickListener listener){
        listOfReceipts = receipts;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ParkingReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parking_report_list_layout, parent, false);

        return new ParkingReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingReportViewHolder holder, int position) {

        String carNumber = listOfReceipts.get(position).getCarNo();
        String dateTime = listOfReceipts.get(position).getDateTime();
        String noOfHours = listOfReceipts.get(position).getNoOfHours();

        holder.noOfHours.setText(noOfHours);
        holder.dateTime.setText(dateTime);
        holder.carNumber.setText(carNumber);

    }

    @Override
    public int getItemCount() {
        return listOfReceipts.size();
    }

    public class ParkingReportViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView carNumber, dateTime, noOfHours;

        public ParkingReportViewHolder(View itemView) {
            super(itemView);
            carNumber = itemView.findViewById(R.id.list_car_number);
            dateTime = itemView.findViewById(R.id.list_date_time);
            noOfHours = itemView.findViewById(R.id.list_no_of_hours);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
