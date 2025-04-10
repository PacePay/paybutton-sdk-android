package com.paysky.upg.extensions

import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import io.paysky.upg.util.SessionManager
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun String.changeDateToArabicDate(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    var date: Date? = null
    try {
        date = sdf.parse(this)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val finalDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("ar"))
    return finalDateFormat.format(date)
}

fun EditText.isEmptyText(): Boolean {
    return this.toString().trim { it <= ' ' }.isEmpty()
}

fun EditText.strictMaximumLengthForEditText(maxLength: Int) {
    val filter =
        InputFilter { source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int ->
            val blockCharacterSet = "~#^|$%&*!."
            if (source != null && blockCharacterSet.contains("" + source)) {
                ""
            }
            null
        }
    this.filters = arrayOf(LengthFilter(maxLength), filter)


}

fun String.splitStringToOrderIdAndAmount(): MutableList<String> {
    val pairsStringsFromQrCode = this.replaceIngSpaceValuesWith()
    var amountText = ""
    try {
        /**
         * here we remove ( "," ) as to be formatted to two decimal points in the TryCatch Block
         */
        val solution: Double = pairsStringsFromQrCode.second.replace(",", "").toDouble()
        amountText = String.format(
            "%.${SessionManager.getInstance().empData.decimalPlace}f",
            solution
        )

    } catch (e: Exception) {
        Log.e("error", e.message.toString())

    }
    return mutableListOf<String>(pairsStringsFromQrCode.first, amountText)
}

fun String.replaceIngSpaceValuesWith(): Pair<String, String> {
    /**
     * here we replace [, ] with any Special Symbol as to : split the 3 values from QRCode according to that Special Symbol  ,
     * then we return the first two values from the Splitted list :
     * - first : OrderNumber
     * - Second : Amount
     */
    val emptyChars = ", "
    val anySymbol = "~"
    return this.replace(emptyChars, anySymbol).split(anySymbol)[0] to this.replace(
        emptyChars,
        anySymbol
    ).split(anySymbol)[1]
}


fun emojiFilter(): InputFilter =
    InputFilter { source, start, end, dest, dstart, dend ->
        if (source == "") { // for backspace
            return@InputFilter source
        }
        return@InputFilter if (source.toString().matches(Regex("[\\x00-\\x7F]+"))) {
            source
        } else ""
    }

fun isCharAllowed(c: Char): Boolean {
    return Character.isLetterOrDigit(c) || Character.isSpaceChar(c)
}

fun Double.formatDoubleToDecimalPlace(): String? {
    val formatter: DecimalFormat = NumberFormat.getInstance(Locale.US) as DecimalFormat
    formatter.applyPattern("#,###,##0.00")
    return formatter.format(this)
}

fun EditText.clearText() {
    this.setText("")
}

fun EditText.removeErrorMessageOnPresenceOfText(){
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(edt: Editable) {
            if (this@removeErrorMessageOnPresenceOfText.toString().isNotEmpty()) {
                this@removeErrorMessageOnPresenceOfText.error = null
            }
        }
    })
}

