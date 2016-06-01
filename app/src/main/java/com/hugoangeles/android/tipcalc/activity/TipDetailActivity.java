package com.hugoangeles.android.tipcalc.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.hugoangeles.android.tipcalc.R;

import butterknife.Bind;

public class TipDetailActivity extends AppCompatActivity {

    @Bind(R.id.txtBillTotal)
    TextView txtBillTotal;
    @Bind(R.id.txtTip)
    TextView txtTip;
    @Bind(R.id.txtTimeStamp)
    TextView txtTimeStamp;

    public final static String TIP_KEY = "tip";
    public final static String DATE_KEY = "timestamp";
    public final static String BILL_TOTAL_KEY = "total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);

        Intent intent = getIntent();

        String strTotal = String.format(
                getString(R.string.tipdetail_message_bill),
                intent.getDoubleExtra(BILL_TOTAL_KEY, 0d));

        String strTip = String.format(
                getString(R.string.tipdetail_message_bill),
                intent.getDoubleExtra(TIP_KEY, 0d));


        txtTimeStamp.setText(intent.getStringExtra(DATE_KEY));
        txtBillTotal.setText(strTotal);
        txtTip.setText(intent.getStringExtra(TIP_KEY));
    }
}
