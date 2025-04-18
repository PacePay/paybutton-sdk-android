package io.paysky.ui.activity.payment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.pacepay.paybutton.BuildConfig;
import com.pacepay.paybutton.R;

import io.paysky.creditCardNfcReader.CardNfcAsyncTask;
import io.paysky.data.model.PaymentData;
import io.paysky.ui.base.BaseActivity;
import io.paysky.ui.fragment.contactless.ContactlessFragment;
import io.paysky.ui.fragment.listcards.ListCardsFragment;
import io.paysky.ui.fragment.manualpayment.ManualPaymentFragment;
import io.paysky.ui.fragment.paymentfail.PaymentFailedFragment;
import io.paysky.ui.fragment.paymentsuccess.PaymentApprovedFragment;
import io.paysky.ui.fragment.qr.QrCodePaymentFragment;
import io.paysky.util.AllURLsStatus;
import io.paysky.util.AppConstant;
import io.paysky.util.AppUtils;
import io.paysky.util.ContaclessInterface;
import io.paysky.util.DialogUtils;
import io.paysky.util.LocaleHelper;
import io.paysky.util.PrefsUtils;
import io.paysky.util.TransactionManager;
import me.grantland.widget.AutofitHelper;

public class PaymentActivity extends BaseActivity implements View.OnClickListener {

    //GUI.
    private ImageView headerBackImage;
    private LinearLayout cardPaymentLayout;
    private LinearLayout contactlessPaymentLayout;
    private LinearLayout qrPaymentLayout;
    private TextView currencyTextView;
    private TextView amountTextView;
    private TextView merchantNameTextView;
    private ImageView poweredByImageView;
    //Objects,
    public static Bitmap qrBitmap;
    public View paymentInfoView, paymentOptionsLayout;
    private PaymentData paymentData;

    private AllURLsStatus allURLsStatus;
    private int urlStatus;

    //Variables.
    private static boolean NORMAL_CLOSE = true;
    private NfcAdapter mNfcAdapter;
    ContaclessInterface contaclessInterface;


    private CardNfcAsyncTask mCardNfcAsyncTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrefsUtils.initialize(this);
        LocaleHelper.setLocale(this, LocaleHelper.getLocale());
        makeActivityFullScreen();
        if(!BuildConfig.DEBUG) {
            AppUtils.preventScreenshot(this);
        }
        setContentView(R.layout.activity_pay);
        hideActionBar();
        initView();
        paymentData = getIntent().getExtras().getParcelable(AppConstant.BundleKeys.PAYMENT_DATA);
        urlStatus = getIntent().getExtras().getInt(AppConstant.BundleKeys.URL_ENUM_KEY);
        allURLsStatus = AllURLsStatus.values()[urlStatus];

        // show activity layout.
        AutofitHelper.create(merchantNameTextView);
        merchantNameTextView.setText(paymentData.merchantName);
        String amount = AppUtils.currencyFormat(paymentData.amountFormatted);
        paymentData.executedTransactionAmount = amount;
        amountTextView.setText(amount);
        currencyTextView.setText(paymentData.currencyName);

        showPaymentBasedOnPaymentOptions(paymentData.paymentMethod);



    }


    public void makeActivityFullScreen() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public void hideActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }


    //GUI Methods.
    private void initView() {
        headerBackImage = findViewById(R.id.header_back_imageView);
        headerBackImage.setOnClickListener(this);
        merchantNameTextView = findViewById(R.id.pb_merchant_name_textView);
        currencyTextView = findViewById(R.id.currency_textView);
        amountTextView = findViewById(R.id.amount_textView);
        TextView languageTextView = findViewById(R.id.language_textView);
        poweredByImageView = findViewById(R.id.iv_powered_by);
        languageTextView.setOnClickListener(this);
        TextView termsTextView = findViewById(R.id.terms_conditions_textView);
        termsTextView.setOnClickListener(this);
        //termsTextView.setOnClickListener(this);
        cardPaymentLayout = findViewById(R.id.card_payment_layout);
        contactlessPaymentLayout = findViewById(R.id.contactless_payment_layout);
        qrPaymentLayout = findViewById(R.id.qr_payment_layout);
        cardPaymentLayout.setOnClickListener(this);
        contactlessPaymentLayout.setOnClickListener(this);
        qrPaymentLayout.setOnClickListener(this);
        paymentInfoView = findViewById(R.id.payment_info_layout);
        paymentOptionsLayout = findViewById(R.id.payment_options_layout);
    }

    public void showPaymentInfoAndOptions() {
        paymentInfoView.setVisibility(View.VISIBLE);
        paymentOptionsLayout.setVisibility(View.VISIBLE);
    }

    public void hidePaymentInfoAndOptions() {
        paymentInfoView.setVisibility(View.GONE);
        paymentOptionsLayout.setVisibility(View.GONE);
    }

    public void showPaymentBasedOnPaymentOptions(int paymentOptions) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.BundleKeys.PAYMENT_DATA, paymentData);
        switch (paymentOptions) {
            case 0:
                // card payment enabled.
                cardPaymentLayout.setVisibility(View.VISIBLE);
                cardPaymentLayout.setOnClickListener(this);

                if (checkNFCSupported()){
                    contactlessPaymentLayout.setVisibility(View.VISIBLE);
                    contactlessPaymentLayout.setOnClickListener(this);
                    showContactlessFragment(bundle);
                    changePaymentOptionButton(3);

                }else{
                    contactlessPaymentLayout.setVisibility(View.GONE);
                    showCardPaymentFragment(bundle);
                    changePaymentOptionButton(1);
                }
                break;


            case 1:
                // wallet payment enabled.
                qrPaymentLayout.setVisibility(View.VISIBLE);
                qrPaymentLayout.setOnClickListener(this);
                showQrPaymentFragment(bundle);
                changePaymentOptionButton(2);
                break;
            case 2:
                // both payments enabled.
                cardPaymentLayout.setVisibility(View.VISIBLE);
                cardPaymentLayout.setOnClickListener(this);
                qrPaymentLayout.setVisibility(View.VISIBLE);
                qrPaymentLayout.setOnClickListener(this);
                if (checkNFCSupported()){
                    contactlessPaymentLayout.setVisibility(View.VISIBLE);
                    contactlessPaymentLayout.setOnClickListener(this);
                    showContactlessFragment(bundle);
                    changePaymentOptionButton(3);

                }else{
                    contactlessPaymentLayout.setVisibility(View.GONE);
                    showCardPaymentFragment(bundle);
                    changePaymentOptionButton(1);
                }
                break;

        }
    }


    public void showCardPaymentFragment(Bundle bundle) {
        if (paymentData.customerId != null) {
            replaceFragmentAndRemoveOldFragment(ListCardsFragment.class, bundle);
        } else {
            replaceFragmentAndRemoveOldFragment(ManualPaymentFragment.class, bundle);
        }
    }


    public void showQrPaymentFragment(Bundle bundle) {
        replaceFragmentAndRemoveOldFragment(QrCodePaymentFragment.class, bundle);
    }

    public void showContactlessFragment(Bundle bundle) {
        replaceFragmentAndRemoveOldFragment(ContactlessFragment.class, bundle);
    }


    public Context getContext() {
        return this;
    }

    public void setHeaderIcon(int icon) {
        headerBackImage.setImageResource(icon);
    }


    public void setHeaderIconClickListener(View.OnClickListener clickListener) {
        headerBackImage.setOnClickListener(clickListener);
    }


    public void replaceFragmentAndRemoveOldFragment(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        replaceFragment(fragmentClass, bundle, false);
    }


    public void replaceFragmentAndAddOldToBackStack(Class<? extends Fragment> fragmentClass, Bundle bundle) {
        replaceFragment(fragmentClass, bundle, true);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.header_back_imageView) {
            onBackPressed();
        } else if (i == R.id.language_textView) {
            // change app language.
            LocaleHelper.changeAppLanguage(this);
            NORMAL_CLOSE = false;
            recreate();
        } else if (i == R.id.terms_conditions_textView) {
            // show terms dialog.
            DialogUtils.showTermsAndConditionsDialog(this);
        } else if (i == R.id.card_payment_layout) {
            changePaymentOptionButton(1);
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstant.BundleKeys.PAYMENT_DATA, paymentData);
            showCardPaymentFragment(bundle);
        } else if (i == R.id.qr_payment_layout) {
            changePaymentOptionButton(2);
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstant.BundleKeys.PAYMENT_DATA, paymentData);
            showQrPaymentFragment(bundle);
        }
        else if (i == R.id.contactless_payment_layout) {
            changePaymentOptionButton(3);
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstant.BundleKeys.PAYMENT_DATA, paymentData);
            showContactlessFragment (bundle)  ;
        }
    }

    private void changePaymentOptionButton(int type) {
        if (type == 1) {
            // manual payment.
            cardPaymentLayout.setBackgroundResource(R.drawable.payment_option_selected);
            TextView manualTextView = cardPaymentLayout.findViewById(R.id.card_payment_textView);
            manualTextView.setTextColor(getResources().getColor(android.R.color.white));
            if (LocaleHelper.getLocale().equals("ar")) {
                manualTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_white, 0);
            } else {
                manualTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_white, 0, 0, 0);
            }
            qrPaymentLayout.setBackgroundResource(R.drawable.payment_option_unselected);
            TextView qrTextView = qrPaymentLayout.findViewById(R.id.qr_payment_textView);
            qrTextView.setTextColor(getResources().getColor(R.color.font_gray_color3));
            if (LocaleHelper.getLocale().equals("ar")) {
                qrTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wallet_gray, 0);
            } else {
                qrTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet_gray, 0, 0, 0);
            }

            contactlessPaymentLayout.setBackgroundResource(R.drawable.payment_option_unselected);
            TextView contactlessTextView = contactlessPaymentLayout.findViewById(R.id.contactless_payment_textView);
            contactlessTextView.setTextColor(getResources().getColor(R.color.font_gray_color3));
            if (LocaleHelper.getLocale().equals("ar")) {
                contactlessTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_black, 0);
            } else {
                contactlessTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_black, 0, 0, 0);
            }

        }

        else if (type == 2) {
            // qr payment.
            cardPaymentLayout.setBackgroundResource(R.drawable.payment_option_unselected);
            TextView manualTextView = cardPaymentLayout.findViewById(R.id.card_payment_textView);
            manualTextView.setTextColor(getResources().getColor(R.color.font_gray_color3));
            if (LocaleHelper.getLocale().equals("ar")) {
                manualTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_black, 0);
            } else {
                manualTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_black, 0, 0, 0);
            }
            qrPaymentLayout.setBackgroundResource(R.drawable.payment_option_selected);
            TextView qrTextView = qrPaymentLayout.findViewById(R.id.qr_payment_textView);
            qrTextView.setTextColor(getResources().getColor(android.R.color.white));
            if (LocaleHelper.getLocale().equals("ar")) {
                qrTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wallet_white, 0);
            } else {
                qrTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet_white, 0, 0, 0);
            }

            contactlessPaymentLayout.setBackgroundResource(R.drawable.payment_option_unselected);
            TextView contactlessTextView = contactlessPaymentLayout.findViewById(R.id.contactless_payment_textView);
            contactlessTextView.setTextColor(getResources().getColor(R.color.font_gray_color3));
            if (LocaleHelper.getLocale().equals("ar")) {
                contactlessTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_black, 0);
            } else {
                contactlessTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_black, 0, 0, 0);
            }
        }

        else if (type == 3) {
            // contactless payment.
            cardPaymentLayout.setBackgroundResource(R.drawable.payment_option_unselected);
            TextView manualTextView = cardPaymentLayout.findViewById(R.id.card_payment_textView);
            manualTextView.setTextColor(getResources().getColor(R.color.font_gray_color3));
            if (LocaleHelper.getLocale().equals("ar")) {
                manualTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_black, 0);
            } else {
                manualTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_black, 0, 0, 0);
            }
            qrPaymentLayout.setBackgroundResource(R.drawable.payment_option_unselected);
            TextView qrTextView = qrPaymentLayout.findViewById(R.id.qr_payment_textView);
            qrTextView.setTextColor(getResources().getColor(R.color.font_gray_color3));
            if (LocaleHelper.getLocale().equals("ar")) {
                qrTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_wallet_gray, 0);
            } else {
                qrTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wallet_gray, 0, 0, 0);
            }
            contactlessPaymentLayout.setBackgroundResource(R.drawable.payment_option_selected);
            TextView contactlessTextView = contactlessPaymentLayout.findViewById(R.id.contactless_payment_textView);
            contactlessTextView.setTextColor(getResources().getColor(android.R.color.white));
            if (LocaleHelper.getLocale().equals("ar")) {
                contactlessTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_card_white, 0);
            } else {
                contactlessTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_card_white, 0, 0, 0);
            }
        }
    }


    @Override
    protected void onDestroy() {
        if (NORMAL_CLOSE) {
            qrBitmap = null;
            TransactionManager.sendTransactionEvent();
        } else {
            PaymentActivity.NORMAL_CLOSE = true;
        }
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

            Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_frame);

            if (f instanceof PaymentApprovedFragment) {
                finish();
            } else if (f instanceof PaymentFailedFragment) {
                finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }


    public void showManualPayment() {
        cardPaymentLayout.performClick();
    }

    public void hidePaymentOptions() {
        qrPaymentLayout.setVisibility(View.GONE);
        cardPaymentLayout.setVisibility(View.GONE);
    }

    public boolean checkNFCSupported(){
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
           return false;
        } else {

              return true;
        }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        contaclessInterface.getIntentFromNewIntent(intent);
    }

    public void initContactlessInterface(ContaclessInterface contaclessInterface){
        this.contaclessInterface=contaclessInterface;

    }



}
