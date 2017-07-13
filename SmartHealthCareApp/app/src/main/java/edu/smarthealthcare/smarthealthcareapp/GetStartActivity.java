package edu.smarthealthcare.smarthealthcareapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.gigamole.slideimageview.lib.SlideImageView;

public class GetStartActivity extends Activity implements View.OnClickListener{

    private Button btnHaveAcc;
    private Button btnNewAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_get_start);

        btnHaveAcc = (Button)findViewById(R.id.btnHaveAccount);
        btnNewAcc = (Button)findViewById(R.id.btnNewAccount);

        btnNewAcc.setOnClickListener(this);
        btnHaveAcc.setOnClickListener(this);

        final SlideImageView slideImageView = (SlideImageView) findViewById(R.id.img_horizontal_slide);
        slideImageView.setSource(R.mipmap.backgroud);
        slideImageView.setRate(0.3f);
        slideImageView.setAxis(SlideImageView.Axis.HORIZONTAL);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNewAccount:
                Intent i = new Intent(this, SignUpActivity.class);
                startActivity(i);
                break;
            case R.id.btnHaveAccount:
                Intent ii = new Intent(this, LoginActivity.class);
                startActivity(ii);
                break;
            default:
                break;
        }

    }
}
