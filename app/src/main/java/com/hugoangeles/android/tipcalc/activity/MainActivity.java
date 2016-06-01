package com.hugoangeles.android.tipcalc.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hugoangeles.android.tipcalc.R;
import com.hugoangeles.android.tipcalc.TipCalcApp;
import com.hugoangeles.android.tipcalc.fragment.TipHistoryListFragment;
import com.hugoangeles.android.tipcalc.fragment.TipHistoryListFragmentListener;
import com.hugoangeles.android.tipcalc.model.TipRecord;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.inputPercentage)
    EditText inputPercentage;
    @Bind(R.id.txtTip)
    TextView txtTip;

    private final static int TIP_STEP_CHANGE = 1;
    private final static int DEFAULT_TIP_PERCENTAGE = 10;

    private TipHistoryListFragmentListener fragmentListener;

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TipHistoryListFragment fragment = (TipHistoryListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentList);

        fragment.setRetainInstance(true);
        fragmentListener = fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            about();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit() {
        hideKeyboard();
        String strInputTotal = inputBill.getText().toString().trim();
        if (strInputTotal.isEmpty()) return;

        double total = Double.parseDouble(strInputTotal);
        int tipPercentage = getTipPercentage();
        TipRecord tipRecord = new TipRecord();

        tipRecord.setBill(total);
        tipRecord.setTipPercentage(tipPercentage);
        tipRecord.setTimestamp(new Date());

        String strTip = String.format(getString(R.string.global_message_tip), tipRecord.getTip());

        fragmentListener.addToList(tipRecord);
        txtTip.setText(strTip);
        txtTip.setVisibility(View.VISIBLE);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease() {
        hideKeyboard();
        handleTipChange(TIP_STEP_CHANGE);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease() {
        hideKeyboard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btnClear)
    public void handleClickClear() {
        hideKeyboard();
        fragmentListener.clearList();
    }

    private void handleTipChange(int tipStepChange) {
        int tipPercentage = getTipPercentage();
        tipPercentage += tipStepChange;
        if (tipPercentage > 0) {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);

        try {
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(TAG, Log.getStackTraceString(npe));
        }
    }

    private void about() {
        TipCalcApp app = (TipCalcApp) getApplication();
        String strUtl = app.getAboutUrl();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(strUtl));
        startActivity(intent);
    }

    public int getTipPercentage() {
        int tipPercentage = DEFAULT_TIP_PERCENTAGE;
        String strTipPercentage = inputPercentage.getText().toString().trim();
        if (!strTipPercentage.isEmpty()) {
            tipPercentage = Integer.parseInt(strTipPercentage);
        } else {
            inputPercentage.setText(String.valueOf(tipPercentage));
        }
        return tipPercentage;
    }
}
