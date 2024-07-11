package com.example.carmecrazy.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.carmecrazy.R;
import com.example.carmecrazy.model.Booking;

import java.util.List;
public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    /**
     * Create ViewHolder class to bind list item view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView tvName;
        public TextView tvState;
        public TextView tvTotal_Price;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvState = itemView.findViewById(R.id.tvState);
            tvTotal_Price = itemView.findViewById(R.id.tvTotalPrice);

            itemView.setOnLongClickListener(this);  //register long click action to this viewholder instance
        }

        @Override
        public boolean onLongClick(View v) {
            currentPos = getAdapterPosition(); //key point, record the position here
            return false;
        }

    } // close ViewHolder class

    //////////////////////////////////////////////////////////////////////
    // adapter class definitions

    private List<Booking> bookingListData;   // list of booking objects
    private Context mContext;       // activity context
    private int currentPos;         // currently selected item (long press)
    public BookingAdapter(Context context, List<Booking> listData) {
        bookingListData = listData;
        mContext = context;
    }

    private Context getmContext() {
        return mContext;
    }

    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate layout using the single item layout
        View view = inflater.inflate(R.layout.booking_list_item, parent, false);
        // Return a new holder instance
        BookingAdapter.ViewHolder viewHolder = new BookingAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookingAdapter.ViewHolder holder, int position) {
        // bind data to the view holder instance
        Booking m = bookingListData.get(position);
        holder.tvName.setText(m.getCar().getCar_Name());
        holder.tvState.setText(m.getState());
        holder.tvTotal_Price.setText("Price: RM" + m.getTotal_price());
    }

    @Override
    public int getItemCount() {
        return bookingListData.size();
    }

    /**
     * return booking object for currently selected cbooking (index already set by long press in viewholder)
     * @return
     */
    public Booking getSelectedItem() {
        // return the booking record if the current selected position/index is valid
        if(currentPos>=0 && bookingListData !=null && currentPos<bookingListData.size()) {
            return bookingListData.get(currentPos);
        }
        return null;
    }
}
