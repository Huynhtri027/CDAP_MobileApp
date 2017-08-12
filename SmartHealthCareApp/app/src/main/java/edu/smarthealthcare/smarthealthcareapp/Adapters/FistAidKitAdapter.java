package edu.smarthealthcare.smarthealthcareapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.smarthealthcare.smarthealthcareapp.ActivityKitDetails;
import edu.smarthealthcare.smarthealthcareapp.Classes.FirstAidKitModel;
import edu.smarthealthcare.smarthealthcareapp.R;


public class FistAidKitAdapter extends RecyclerView.Adapter<FistAidKitAdapter.RecyclerViewItemHolder> {

    private List<FirstAidKitModel> firstAidKitModels;
    private Context mContext;

    public FistAidKitAdapter(List<FirstAidKitModel> firstAidKitModelList) {
        this.firstAidKitModels = firstAidKitModelList;
    }


    public void setFirstAidKits(List<FirstAidKitModel> firstAidKitModels) {
        this.firstAidKitModels = firstAidKitModels;
    }


    public class RecyclerViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtAidName;
        private TextView txtAidPrice;
        private ImageView imageViewFirstAidCard;
        private FirstAidKitModel item;

        public RecyclerViewItemHolder(View itemView) {
            super(itemView);
            txtAidName = (TextView)itemView.findViewById(R.id.txtAidName);
            txtAidPrice = (TextView)itemView.findViewById(R.id.txtAidPrice);
            imageViewFirstAidCard = (ImageView)itemView.findViewById(R.id.imageViewFirstAidCard);


            itemView.setOnClickListener(this);
        }

        public void bindItem(FirstAidKitModel items) {

            item = items;
            txtAidName.setText(item.getDrugPackName());
            txtAidPrice.setText("Price : Rs." + item.getUnitPrice());

            if (!items.getImage().isEmpty() || !(items.getImage() == "") || !(items.getImage() == null)){
                Picasso.with(imageViewFirstAidCard.getContext())
                        .load("http://shabeeru19.000webhostapp.com/learnenglish/images/stories/"+items.getImage().toString())
                        .placeholder(R.drawable.default_image)
                        .error(R.drawable.default_image)
                        .into(imageViewFirstAidCard);
            }
        }

        @Override
        public void onClick(View view) {

            Intent i = new Intent(view.getContext().getApplicationContext(), ActivityKitDetails.class);
            i.putExtra("Id", item.getDrugPackId().toString());
            view.getContext().startActivity(i);

        }
    }

    @Override
    public RecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_firstaidkit_details, parent, false);
        FistAidKitAdapter.RecyclerViewItemHolder recyclerViewItemHolder = new FistAidKitAdapter.RecyclerViewItemHolder(itemView);

        return recyclerViewItemHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewItemHolder holder, int position) {

        FirstAidKitModel item =  firstAidKitModels.get(position);

        holder.bindItem(item);
    }

    @Override
    public int getItemCount() {
        return this.firstAidKitModels.size();
    }


}
