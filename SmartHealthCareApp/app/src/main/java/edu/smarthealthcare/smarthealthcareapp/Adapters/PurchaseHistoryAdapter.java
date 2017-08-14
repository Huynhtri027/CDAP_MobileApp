package edu.smarthealthcare.smarthealthcareapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.PurchaseHistoryModel;
import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * Created by RG User on 08/12/17.
 */

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.RecyclerViewItemHolder> {

    private List<PurchaseHistoryModel> purchaseHistoryModelList;

    public PurchaseHistoryAdapter(List<PurchaseHistoryModel> purchaseHistoryModelList) {
        this.purchaseHistoryModelList = purchaseHistoryModelList;
    }

    public void setPurchaseHistoryList(List<PurchaseHistoryModel> purchaseHistoryModelList) {
        this.purchaseHistoryModelList = purchaseHistoryModelList;
    }

    public class RecyclerViewItemHolder extends RecyclerView.ViewHolder {

        private TextView txtAidHistory;
        private TextView txtPriceHistory;
        private TextView txtDateHistory;
        private TextView txtKioskHistory;
        private TextView txtInvoiceHistory;
        private ImageView imageViewHistory;
        private PurchaseHistoryModel item;

        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            txtAidHistory = (TextView)itemView.findViewById(R.id.txtAidHistory);
            txtPriceHistory = (TextView)itemView.findViewById(R.id.txtPriceHistory);
            txtDateHistory = (TextView)itemView.findViewById(R.id.txtDateHistory);
            txtKioskHistory = (TextView)itemView.findViewById(R.id.txtKioskHistory);
            txtInvoiceHistory = (TextView)itemView.findViewById(R.id.txtInvoiceHistory);

            imageViewHistory = (ImageView)itemView.findViewById(R.id.imageViewHistory);

        }

        public void bindItem(PurchaseHistoryModel items) {

            item = items;
            txtAidHistory.setText(item.getDrugPackName());
            txtPriceHistory.setText("Rs. "+item.getTotalAmount());
            txtDateHistory.setText(item.getDate());
            txtKioskHistory.setText("Kiosk: " +item.getKioskId());
            txtInvoiceHistory.setText("Invoice: " +item.getInvoiceNo());

            if (!items.getImage().isEmpty() || !(items.getImage() == "") || !(items.getImage() == null)){
                Picasso.with(imageViewHistory.getContext())
                        .load("http://smarthealthcaresystem.000webhostapp.com/assets/img/"+items.getImage().toString())
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(imageViewHistory);
            }

        }
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_history_item, parent, false);
        PurchaseHistoryAdapter.RecyclerViewItemHolder recyclerViewItemHolder = new PurchaseHistoryAdapter.RecyclerViewItemHolder(itemView);

        return recyclerViewItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder holder, int position) {

        PurchaseHistoryModel item =  purchaseHistoryModelList.get(position);

        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return this.purchaseHistoryModelList.size();
    }


}
