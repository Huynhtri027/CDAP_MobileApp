package edu.smarthealthcare.smarthealthcareapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.Classes.OrderModel;
import edu.smarthealthcare.smarthealthcareapp.R;

/**
 * Created by RG User on 08/12/17.
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.RecyclerViewItemHolder> {

    private List<OrderModel> orderModelList;

    public OrderDetailsAdapter(List<OrderModel> orderModelList) {
        this.orderModelList = orderModelList;
    }

    public void setOrderModelList(List<OrderModel> orderModelList) {
        this.orderModelList = orderModelList;
    }

    public class RecyclerViewItemHolder extends RecyclerView.ViewHolder {

        private TextView txtAidOrders;
        private TextView txtPriceOrders;
        private TextView txtDateOrders;
        private TextView txtStatusCardOrders;
        private ImageView imageViewOrders;
        private OrderModel item;

        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            txtAidOrders = (TextView)itemView.findViewById(R.id.txtAidOrders);
            txtPriceOrders = (TextView)itemView.findViewById(R.id.txtPriceOrders);
            txtDateOrders = (TextView)itemView.findViewById(R.id.txtDateOrders);
            txtStatusCardOrders = (TextView)itemView.findViewById(R.id.txtStatusCardOrders);

            imageViewOrders = (ImageView)itemView.findViewById(R.id.imageViewOrders);

        }

        public void bindItem(OrderModel items) {

            item = items;
            txtAidOrders.setText(item.getDrugPackName());
            txtPriceOrders.setText("Rs. "+item.getTotalAmount());
            txtDateOrders.setText(item.getAddedDate());
            txtStatusCardOrders.setText(item.getDeliveryStatus());
//            Toast.makeText(itemView.getContext(),items.getImage().toString() , Toast.LENGTH_LONG).show();
            if (!items.getImage().isEmpty() || !(items.getImage() == "") || !(items.getImage() == null)){
                Picasso.with(imageViewOrders.getContext())
                        .load("http://smarthealthcaresystem.000webhostapp.com/assets/img/"+items.getImage().toString())
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(imageViewOrders);
            }

        }
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_order_item, parent, false);
        OrderDetailsAdapter.RecyclerViewItemHolder recyclerViewItemHolder = new OrderDetailsAdapter.RecyclerViewItemHolder(itemView);

        return recyclerViewItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder holder, int position) {

        OrderModel item =  orderModelList.get(position);

        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return this.orderModelList.size();
    }


}
