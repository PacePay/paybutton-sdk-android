package io.paysky.paybutton.example;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amrel.paybuttonexample.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;
import io.paysky.paybutton.example.util.DateTimeDailogUtil;
import io.paysky.ui.mvp.paylinkfragment.PayLinkFragmentPresenter;
import io.paysky.ui.mvp.paylinkfragment.PayLinkFragmentView;
import io.paysky.util.ValidateUtil;

public class PayLinkActivity extends AppCompatActivity implements PayLinkFragmentView,TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
        public int selectedTypePayLink;
        Spinner  currencyTypeSpinner, typeSpinner, notiTypeSpinner;
        EditText payerNameEditText, numberPaymentEditText, refNumberEditText, notificationEditText,terminalEditText,merchantEditText,currencyCode;
        Button expirationDateButton;
        TableRow typeLL;
        LinearLayout genratePayLink,line;
        CurrencyEditText amountEditText;
        public String NotificationMethod = "";
        String linkExpireDate;
    private PayLinkFragmentPresenter presenter = new PayLinkFragmentPresenter();
    private ProgressBar progressBar;

    private int dayOfMonth = 0;
    private int month = 0;
    private int year = 0;
        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_paylink); // Your XML should be renamed from fragment to activity layout if needed
            presenter.attachView(this);
            // Initialize views

            typeSpinner = findViewById(R.id.TypeSpinner);
            currencyCode = findViewById(R.id.currency_code);
            notiTypeSpinner = findViewById(R.id.NotiType);
            terminalEditText = findViewById(R.id.terminal_editText);
            merchantEditText = findViewById(R.id.merchant_editText);
            genratePayLink = findViewById(R.id.genratePayLink);
            line = findViewById(R.id.line);
            typeLL = findViewById(R.id.typeLL);


            progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);


            progressBar.setVisibility(View.GONE); // Hidden by default
            LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            progressParams.gravity = Gravity.CENTER_HORIZONTAL;
            line.addView(progressBar, progressParams);


            payerNameEditText = findViewById(R.id.PayerName);
            numberPaymentEditText = findViewById(R.id.NumberPayment);
            refNumberEditText = findViewById(R.id.refnumber);
            notificationEditText = findViewById(R.id.notification);
            amountEditText = findViewById(R.id.amount_editText); // Assuming custom view has EditText behavior

            expirationDateButton = findViewById(R.id.dataExp);
            setDefaultLinkExpireDate();
            // Example: handle click on expiration date
            expirationDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DateTimeDailogUtil.createExpirationDateDialog(linkExpireDate,getContext(), PayLinkActivity.this).show();
                }
            });

            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item_paylink, getResources().getStringArray(R.array.notitype)); //selected item will look like a spinner set from XML
            spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            notiTypeSpinner.setAdapter(spinnerArrayAdapter2);
            notiTypeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.red100), PorterDuff.Mode.SRC_ATOP);

            notiTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if (selectedItemView != null) {
                        ((TextView) selectedItemView).setTextColor(getResources().getColor(R.color.black));
                    }
                    if (position == 0) {
                        notificationEditText.setHint(getResources().getString(R.string.upg_new_merchant_label_email));
                        notificationEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(160)});
                        notificationEditText.setInputType(1);
                        notificationEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
                        NotificationMethod = "0";
                    } else {
                        notificationEditText.setHint(getResources().getString(R.string.upg_new_merchant_phone_hint));
                        notificationEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        notificationEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});

                        NotificationMethod = "1";

                    }

                    notificationEditText.setText("");
                    //StringExtKt.removeErrorMessageOnPresenceOfText(notificationEditText);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {

                }
            });


            ArrayAdapter<String> spinnerArrayAdapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item_paylink, getResources().getStringArray(R.array.pay_link_type));
            spinnerArrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(spinnerArrayAdapter4);
            typeSpinner.getBackground().setColorFilter(getResources().getColor(R.color.red100), PorterDuff.Mode.SRC_ATOP);
            typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    if (selectedItemView != null) {
                        ((TextView) selectedItemView).setTextColor(getResources().getColor(R.color.black));
                    }
                    selectedTypePayLink = position;
                    if (position == 1) {
                        typeLL.setVisibility(View.VISIBLE);
                    } else {
                        typeLL.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    ((TextView) parentView.getItemAtPosition(0)).setTextColor(getResources().getColor(R.color.black));

                    selectedTypePayLink = 0;
                }
            });

            genratePayLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        RatepayerLinkClicked();
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    public void RatepayerLinkClicked() throws ParseException {
        boolean hasError = false;

        try {
            if (amountEditText.getText().toString().isEmpty() || amountEditText.getText().toString().equals("0") || amountEditText.getText().toString().equals("0.00") || amountEditText.getText().toString().equals("0.000")) {
                amountEditText.setError(getResources().getString(R.string.upg_dialog_amount_title));
                hasError = true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        Integer amountFormatted = 0;
        try {
            amountFormatted = NumberUtil.formatPaymentAmountToServer(amountEditText.getCurrencyText());
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (notificationEditText.getText().toString().isEmpty()) {
            notificationEditText.setError(getResources().getString(R.string.error_field_required));
            hasError = true;
        } else {
            if (NotificationMethod.equals("0")) {
                if (!ValidateUtil.validMail(notificationEditText.getText().toString())) {
                    notificationEditText.setError(getResources().getString(R.string.invalid_mail));
                    hasError = true;
                }
            }
            if (NotificationMethod.equals("1")) {
                if (notificationEditText.getText().toString().isEmpty()) {
                    notificationEditText.setError(getResources().getString(R.string.invalid_phone));
                    hasError = true;
                }
            }
        }

        if (hasError) {
            return;
        }

        String numberOfPaymentValue = numberPaymentEditText.getText().toString();

        if (selectedTypePayLink == 0) {
            numberOfPaymentValue = "1";
        } else {
            if (numberPaymentEditText.getText().toString().isEmpty() || Double.parseDouble(numberPaymentEditText.getText().toString()) == 0.0) {
                numberPaymentEditText.setError(getResources().getString(R.string.error_field_required));
                return;
            }
        }

        amountFormatted = NumberUtil.formatPaymentAmountToServer(amountEditText.getCurrencyText());
        presenter.initiateOrder(terminalEditText.getText().toString(),merchantEditText.getText().toString(),"17213ec7a0aa2c438d423507684581f2", "EGP", currencyCode.getText().toString(), linkExpireDate, payerNameEditText.getText().toString(), NotificationMethod, numberPaymentEditText.getText().toString(), refNumberEditText.getText().toString(), amountEditText.getCurrencyText(), amountFormatted + "", numberOfPaymentValue, "imageBase64Value", "");

//        if (BuildUtil.isVPOSGroupApp()) {
//            presenter.initiateOrder(selectedTerminal, selectedCurrancy, CurrncyType.getSelectedItem().toString(), linkExpireDate, PayerName.getText().toString(), NotificationMethod, notification.getText().toString(), refnumber.getText().toString(), amountEditText.getCurrencyText(), amountFormatted + "", numberOfPaymentValue, imageBase64Value, messageEditText.getText().toString());
//        } else {
//            if (BuildUtil.isUpgGroupApp() && (NotificationMethod.equals("1"))) {
//                presenter.initiateOrder(selectedTerminal, selectedCurrancy, CurrncyType.getSelectedItem().toString(), linkExpireDate, PayerName.getText().toString(), NotificationMethod, "+" + notification.getText().toString(), refnumber.getText().toString(), amountEditText.getCurrencyText(), amountFormatted + "", numberOfPaymentValue, imageBase64Value, "");
//            } else {
//                presenter.initiateOrder(selectedTerminal, selectedCurrancy, CurrncyType.getSelectedItem().toString(), linkExpireDate, PayerName.getText().toString(), NotificationMethod, notification.getText().toString(), refnumber.getText().toString(), amountEditText.getCurrencyText(), amountFormatted + "", numberOfPaymentValue, imageBase64Value, "");
//            }
//        }
    }


    private void setDefaultLinkExpireDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month = month + 1;

        linkExpireDate = String.format(Locale.ENGLISH, "%d/%d/%d", day, month, year);
        expirationDateButton.setText(linkExpireDate);
       expirationDateButton.setTextColor(getResources().getColor(R.color.black));
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        linkExpireDate = "" + new StringBuilder().append(year).append("").append((month < 10 ? "0" + month : month)).append("").append((day < 10 ? "0" + day : day)).append((hour < 10 ? "0" + hour : hour)).append((minute < 10 ? "0" + minute : minute));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.month = month;
        this.year = year;
        this.dayOfMonth = dayOfMonth;

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        final Context contextThemeWrapper = new ContextThemeWrapper(getContext(), R.style.AppTheme2);
        mTimePicker = new TimePickerDialog(contextThemeWrapper, this, hour, minute, true);

        mTimePicker.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarCheckTimeNow = Calendar.getInstance();
        long mseconds = calendar.getTimeInMillis();
        calendarCheckTimeNow.set(year, month, dayOfMonth, hourOfDay, minute, 0);
        long msecondsNow = calendarCheckTimeNow.getTimeInMillis();
        if (msecondsNow < mseconds) {
            Toast.makeText(this, "Wrong hour", Toast.LENGTH_SHORT).show();
            return;
        }
        month = month + 1;

        linkExpireDate = "" + new StringBuilder().append(year).append("/").append((month < 10 ? "0" + month : month)).append("/").append((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth)).append("  ").append((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)).append(":").append((minute < 10 ? "0" + minute : minute));


            expirationDateButton.setText(linkExpireDate);
        expirationDateButton.setTextColor(getResources().getColor(R.color.black));

        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        linkExpireDate = "" + new StringBuilder().append(year).append("").append((month < 10 ? "0" + month : month)).append("").append((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth)).append((hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)).append((minute < 10 ? "0" + minute : minute));
        expirationDateButton.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void showPayLinkDialog(InitiateOrderRequest initiateOrder, InitiateOrderResponse body) {

    }

    @Override
    public void hideDataForm() {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(int message) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToast(int message) {

    }

    @Override
    public void showNoInternetDialog() {

    }

    @Override
    public boolean isInternetAvailable() {
        return false;
    }
}

