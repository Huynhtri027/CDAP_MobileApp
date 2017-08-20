package edu.smarthealthcare.smarthealthcareapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.DrugLocationModel;
import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * Created by RG User on 08/12/17.
 */

public class DrugLocationAdapter extends RecyclerView.Adapter<DrugLocationAdapter.RecyclerViewItemHolder> {

    private List<DrugLocationModel> DrugLocationModelList;

    public DrugLocationAdapter(List<DrugLocationModel> DrugLocationModelList) {
        this.DrugLocationModelList = DrugLocationModelList;
    }

    public void setDrugLocationModelList(List<DrugLocationModel> DrugLocationModelList) {
        this.DrugLocationModelList = DrugLocationModelList;
    }

    public class RecyclerViewItemHolder extends RecyclerView.ViewHolder {

        private TextView txt_location_name_for_drug;
        private TextView txt_kiosk_id_for_drug;
        private TextView txt_qty_for_drug;
        private DrugLocationModel item;

        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            txt_location_name_for_drug = (TextView) itemView.findViewById(R.id.txt_location_name_for_drug);
            txt_kiosk_id_for_drug = (TextView) itemView.findViewById(R.id.txt_kiosk_id_for_drug);
            txt_qty_for_drug = (TextView) itemView.findViewById(R.id.txt_qty_for_drug);
        }

        public void bindItem(DrugLocationModel items) {

            item = items;
            txt_location_name_for_drug.setText(item.getAddress());
            txt_kiosk_id_for_drug.setText(item.getKioskId());
            txt_qty_for_drug.setText(item.getAvailQuantity());

        }
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_loction_items, parent, false);
        DrugLocationAdapter.RecyclerViewItemHolder recyclerViewItemHolder = new DrugLocationAdapter.RecyclerViewItemHolder(itemView);

        return recyclerViewItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder holder, int position) {

        DrugLocationModel item =  DrugLocationModelList.get(position);

        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return this.DrugLocationModelList.size();
    }


}
