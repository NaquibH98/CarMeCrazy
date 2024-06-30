package com.example.carmecrazy.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.carmecrazy.R;
import com.example.carmecrazy.model.Car;

import java.util.List;
public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    /**
     * Create ViewHolder class to bind list item view
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView tvName;
        public TextView tvBrand;
        public TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvPrice = itemView.findViewById(R.id.tvPrice);

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

    private List<Car> carListData;   // list of car objects
    private Context mContext;       // activity context
    private int currentPos;         // currently selected item (long press)
    public CarAdapter(Context context, List<Car> listData) {
        carListData = listData;
        mContext = context;
    }

    private Context getmContext() {
        return mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate layout using the single item layout
        View view = inflater.inflate(R.layout.car_list_item, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // bind data to the view holder instance
        Car m = carListData.get(position);
        holder.tvName.setText(m.getCar_Name());
        holder.tvBrand.setText(m.getCar_Brand());
        holder.tvPrice.setText(m.getCar_Price());
    }

    @Override
    public int getItemCount() {
        return carListData.size();
    }

    /**
     * return car object for currently selected car (index already set by long press in viewholder)
     * @return
     */
    public Car getSelectedItem() {
        // return the car record if the current selected position/index is valid
        if(currentPos>=0 && carListData !=null && currentPos<carListData.size()) {
            return carListData.get(currentPos);
        }
        return null;
    }
}
