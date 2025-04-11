package io.paysky.paybutton.example;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.amrel.paybuttonexample.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import io.paysky.data.model.request.InitiateOrderRequest;
import io.paysky.data.model.response.InitiateOrderResponse;
import io.paysky.data.network.ApiConnection;
import io.paysky.paybutton.example.util.DateTimeDailogUtil;
import io.paysky.ui.paylink.PayLinkCallback;
import io.paysky.ui.paylink.PayLinkSdk;
import io.paysky.ui.paylink.mvp.PayLinkFragmentPresenter;
import io.paysky.ui.paylink.mvp.PayLinkFragmentView;
import io.paysky.util.ValidateUtil;

public class PayLinkActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private int selectedTypePayLink;
    private String notificationMethod = "";
    private String linkExpireDate;

    private Spinner currencyTypeSpinner, typeSpinner, notificationTypeSpinner;
    private EditText payerNameEditText, numberOfPaymentsEditText, refNumberEditText, notificationEditText, terminalEditText, merchantEditText, currencyCodeEditText,secureHashEditText,currencyName;
    private Button expirationDateButton;
    private CurrencyEditText amountEditText;
    private TableRow typeLayout;
    private LinearLayout generatePayLinkButton, lineLayout;
    private ProgressBar progressBar;


    private int dayOfMonth, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylink);


        initViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setupSpinners();
        }
        setupListeners();
        setDefaultLinkExpireDate();
    }

    private void initViews() {
        typeSpinner = findViewById(R.id.TypeSpinner);
        currencyCodeEditText = findViewById(R.id.currency_code);
        notificationTypeSpinner = findViewById(R.id.NotiType);
        terminalEditText = findViewById(R.id.terminal_editText);
        merchantEditText = findViewById(R.id.merchant_editText);
        generatePayLinkButton = findViewById(R.id.genratePayLink);
        lineLayout = findViewById(R.id.line);
        typeLayout = findViewById(R.id.typeLL);
        payerNameEditText = findViewById(R.id.PayerName);
        secureHashEditText = findViewById(R.id.secureHash_editText);
        numberOfPaymentsEditText = findViewById(R.id.NumberPayment);
        refNumberEditText = findViewById(R.id.refnumber);
        notificationEditText = findViewById(R.id.notification);
        amountEditText = findViewById(R.id.amount_editText);
        expirationDateButton = findViewById(R.id.dataExp);
        currencyName = findViewById(R.id.currency_name);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        lineLayout.addView(progressBar, params);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupSpinners() {
        ArrayAdapter<String> notiAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_paylink, getResources().getStringArray(R.array.notitype));
        notiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notificationTypeSpinner.setAdapter(notiAdapter);
        notificationTypeSpinner.getBackground().setColorFilter(getColor(R.color.red100), PorterDuff.Mode.SRC_ATOP);

        notificationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                handleNotificationTypeChange(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item_paylink, getResources().getStringArray(R.array.pay_link_type));
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.getBackground().setColorFilter(getColor(R.color.red100), PorterDuff.Mode.SRC_ATOP);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTypePayLink = position;
                typeLayout.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupListeners() {
        expirationDateButton.setOnClickListener(v -> DateTimeDailogUtil.createExpirationDateDialog(linkExpireDate, this, PayLinkActivity.this).show());

        generatePayLinkButton.setOnClickListener(v -> {
            try {
                onGenerateLinkClicked();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleNotificationTypeChange(int position) {
        int maxLength = position == 0 ? 160 : 16;
        int inputType = position == 0 ? InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT : InputType.TYPE_CLASS_NUMBER;

        String hint = getString(position == 0 ? R.string.upg_new_merchant_label_email : R.string.upg_new_merchant_phone_hint);

        notificationEditText.setHint(hint);
        notificationEditText.setInputType(inputType);
        notificationEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        notificationEditText.setText("");
        notificationMethod = String.valueOf(position);
    }

    private void onGenerateLinkClicked() throws ParseException {
        if (!validateInputs()) return;

        String amountStr = amountEditText.getCurrencyText();
        int formattedAmount = NumberUtil.formatPaymentAmountToServer(amountStr);
        String numberOfPayments = selectedTypePayLink == 0 ? "1" : numberOfPaymentsEditText.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        PayLinkSdk.builder()
                .setSelectedTerminal(terminalEditText.getText().toString())
                .setMerchantId(merchantEditText.getText().toString())
                .setMerchantSecureHash(secureHashEditText.getText().toString())
                .setSelectedCurrency(currencyCodeEditText.getText().toString())
                .setCurrencyName(currencyName.getText().toString())
                .setDateExpire(linkExpireDate)
                .setPayerName(payerNameEditText.getText().toString())
                .setNotificationMethod(notificationMethod)
                .setNotification(numberOfPayments)
                .setReferenceNumber(refNumberEditText.getText().toString())
                .setAmount(amountStr)
                .setAmountTransaction(String.valueOf(formattedAmount))
                .setNumberOfPayment(numberOfPayments)
                .setImage(null)
                .setMessage("Thanks for your payment")
                .setCallback(new PayLinkCallback() {
            @Override
            public void onSuccess(InitiateOrderRequest request, InitiateOrderResponse response) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onSuccess:onSuccessonSuccessonSuccess " + response.isSuccess());
            }

            @Override
            public void onError(String message) {
                progressBar.setVisibility(View.GONE);
                Log.d("TAG", "onError:messagemessagemessage  " + message);
            }
        }).initiateOrder();


    }

    private boolean validateInputs() {
        boolean hasError = false;

        String amount = amountEditText.getText().toString().trim();
        if (amount.isEmpty() || amount.equals("0") || amount.equals("0.00") || amount.equals("0.000")) {
            amountEditText.setError(getString(R.string.upg_dialog_amount_title));
            hasError = true;
        }

        String notification = notificationEditText.getText().toString().trim();
        if (notification.isEmpty()) {
            notificationEditText.setError(getString(R.string.error_field_required));
            hasError = true;
        } else if (notificationMethod.equals("0") && !ValidateUtil.validMail(notification)) {
            notificationEditText.setError(getString(R.string.invalid_mail));
            hasError = true;
        } else if (notificationMethod.equals("1") && notification.length() < 6) {
            notificationEditText.setError(getString(R.string.invalid_phone));
            hasError = true;
        }

        if (selectedTypePayLink == 1) {
            String numberOfPayments = numberOfPaymentsEditText.getText().toString().trim();
            if (numberOfPayments.isEmpty() || Double.parseDouble(numberOfPayments) == 0.0) {
                numberOfPaymentsEditText.setError(getString(R.string.error_field_required));
                hasError = true;
            }
        }

        return !hasError;
    }

    private void setDefaultLinkExpireDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        expirationDateButton.setText(String.format(Locale.ENGLISH, "%02d/%02d/%d", dayOfMonth, month, year));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            expirationDateButton.setTextColor(getColor(R.color.black));
        }

        linkExpireDate = String.format(Locale.ENGLISH, "%04d%02d%02d%02d%02d", year, month, dayOfMonth, hour, minute);
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
        final Context contextThemeWrapper = new ContextThemeWrapper(this, R.style.AppTheme2);
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


}
