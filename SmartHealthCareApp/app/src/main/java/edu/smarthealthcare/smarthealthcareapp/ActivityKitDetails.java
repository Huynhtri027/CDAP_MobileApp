package edu.smarthealthcare.smarthealthcareapp;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.internal.MDButton;
import com.gigamole.slideimageview.lib.SlideImageView;

import edu.smarthealthcare.smarthealthcareapp.Classes.SectionsPagerAdaper;

public class ActivityKitDetails extends AppCompatActivity {

    private ViewPager viewPager;
    private SectionsPagerAdaper mSectionsPagerAdaper;

    private TabLayout mTabLayout;
    private MaterialDialog.Builder dialog;

    private Button btnOder;
    private TextView prodName;
    private TextView prodID;
    private Spinner prodSize;
    private Spinner prodQty;

    private String ProductName;
    private int ProductID;
    private int size;
    private int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kit_details);
        //Tabs
//

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_new);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        btnOder = (Button)findViewById(R.id.btn_order_now);
        btnOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });





    }


    public void createDialog(){

        View mView = getLayoutInflater().inflate(R.layout.fragment_order_product, null);

        dialog = new MaterialDialog.Builder(ActivityKitDetails.this);
        dialog.title(R.string.Confirm_Order);

        prodQty = (Spinner) mView.findViewById(R.id.sel_prod_quantity);
        prodSize = (Spinner)mView.findViewById(R.id.cmb_prod_size);
        prodName = (TextView) mView.findViewById(R.id.txtOrderProdName);
        prodID = (TextView)mView.findViewById(R.id.txtOrderProdID);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityKitDetails.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.qty_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prodQty.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ActivityKitDetails.this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.size_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prodSize.setAdapter(adapter1);

        dialog.customView(mView, true);
        dialog.positiveText(R.string.confirm);
        dialog.negativeText(android.R.string.cancel);
        dialog.cancelable(false);
        dialog.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                ProductName = prodName.getText().toString();
                ProductID = Integer.parseInt(prodID.getText().toString());
                quantity = Integer.parseInt(prodQty.getSelectedItem().toString());
                size = prodSize.getSelectedItemPosition()+1;

                Toast.makeText(ActivityKitDetails.this, "Product : " + ProductName + " Qty : " + quantity + " Size : " + size, Toast.LENGTH_LONG).show();
            }
        });
        dialog.build();


        dialog.show();



    }

    private void orderNow() {
//        ProductName = prodName.getText().toString();
//        ProductID = Integer.parseInt(prodID.getText().toString());
////        quantity =





    }

}
