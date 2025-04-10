package io.paysky.paybutton.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.amrel.paybuttonexample.R;

class PayLinkActivity extends AppCompatActivity {

        Spinner terminalSpinner, currencyTypeSpinner, typeSpinner, notiTypeSpinner;
        EditText payerNameEditText, numberPaymentEditText, refNumberEditText, notificationEditText, amountEditText;
        Button expirationDateButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_paylink); // Your XML should be renamed from fragment to activity layout if needed

            // Initialize views
            terminalSpinner = findViewById(R.id.TerminalSpinner);
            currencyTypeSpinner = findViewById(R.id.CurrncyType);
            typeSpinner = findViewById(R.id.TypeSpinner);
            notiTypeSpinner = findViewById(R.id.NotiType);

            payerNameEditText = findViewById(R.id.PayerName);
            numberPaymentEditText = findViewById(R.id.NumberPayment);
            refNumberEditText = findViewById(R.id.refnumber);
            notificationEditText = findViewById(R.id.notification);
            amountEditText = findViewById(R.id.amount_editText); // Assuming custom view has EditText behavior

            expirationDateButton = findViewById(R.id.dataExp);

            // Example: handle click on expiration date
            expirationDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // You can show a date picker dialog here
                }
            });

            // You can continue by setting adapters and listeners for spinners
        }
    }