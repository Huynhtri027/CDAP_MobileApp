package edu.smarthealthcare.smarthealthcareapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.ExpiryModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.R;


public class ExpiryDetailsAdapter extends RecyclerView.Adapter<ExpiryDetailsAdapter.RecyclerViewItemHolder> {

    private List<ExpiryModel> expiryModelList;

    public ExpiryDetailsAdapter(List<ExpiryModel> expiryModelList) {
        this.expiryModelList = expiryModelList;
    }

    public void setOrderModelList(List<ExpiryModel> expiryModelList) {
        this.expiryModelList = expiryModelList;
    }

    public class RecyclerViewItemHolder extends RecyclerView.ViewHolder {

        private TextView txtDrugName;
        private TextView txtExpiryDate;
        private TextView txtDaysRemaining;
        private TextView txtBarcode;
        private ExpiryModel item;

        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            txtDrugName = (TextView)itemView.findViewById(R.id.txtDrugName);
            txtExpiryDate = (TextView)itemView.findViewById(R.id.txtExpiryDate);
            txtDaysRemaining = (TextView)itemView.findViewById(R.id.txtDaysRemaining);
            txtBarcode = (TextView)itemView.findViewById(R.id.txtBarcode);


        }

        public void bindItem(ExpiryModel items) {

            item = items;
            txtDrugName.setText(item.getDrug_name());
            txtExpiryDate.setText("Expiry : "+item.getExpiry_date());
            txtDaysRemaining.setText(item.getDays_remaining() + " Days Remaining");
            txtBarcode.setText("Barcode : "+item.getBarcode());
//            Toast.makeText(itemView.getContext(),items.getImage().toString() , Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_expiry_item, parent, false);
        ExpiryDetailsAdapter.RecyclerViewItemHolder recyclerViewItemHolder = new ExpiryDetailsAdapter.RecyclerViewItemHolder(itemView);

        return recyclerViewItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder holder, int position) {

        ExpiryModel item =  expiryModelList.get(position);

        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return this.expiryModelList.size();
    }


}
