package sg.edu.rp.c346.id22017139.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Switch switchDropdown;
    RadioButton paynow;
    LinearLayout dropdownLayout;
    LinearLayout dropdownLayout1;
    Button splitbtn;
    EditText etBill;
    EditText paxInput;
    ToggleButton gstbtn;
    ToggleButton svsbtn;
    TextView ttlbill;
    TextView eachpay;
    EditText discount;
    Button resetbtn;
    EditText phoneno;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchDropdown = findViewById(R.id.switchDropdown);
        dropdownLayout = findViewById(R.id.dropdownLayout);

        paynow = findViewById(R.id.paynow);
        dropdownLayout1 = findViewById(R.id.dropdownLayout1);

        splitbtn = findViewById(R.id.splitbtn);
        etBill = findViewById(R.id.etBill);
        paxInput = findViewById(R.id.paxInput);
        gstbtn = findViewById(R.id.gstbtn);
        svsbtn = findViewById(R.id.svsbtn);
        discount = findViewById(R.id.discount);
        resetbtn = findViewById(R.id.resetbtn);
        ttlbill = findViewById(R.id.ttlbill);
        eachpay = findViewById(R.id.eachpay);
        phoneno = findViewById(R.id.phoneno);


        // Discount dropdown
        switchDropdown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dropdownLayout.setVisibility(View.VISIBLE);
                } else {
                    dropdownLayout.setVisibility(View.GONE);
                }
            }
        });

        // Paynow dropdown
        paynow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dropdownLayout1.setVisibility(View.VISIBLE);
                } else {
                    dropdownLayout1.setVisibility(View.GONE);
                }
            }
        });

        // Reset Button
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etBill.setText("");
                paxInput.setText("");
                svsbtn.setChecked(false);
                gstbtn.setChecked(false);
                discount.setText("");
                ttlbill.setText("");
                eachpay.setText("");
            }
        });

        // Split bill button
        splitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amtString = etBill.getText().toString().trim();
                String paxString = paxInput.getText().toString().trim();

                if (amtString.isEmpty() || paxString.isEmpty()) {
                    // Display an error message or handle the case when input is empty
                    return;
                }

                double amtInput = Double.parseDouble(amtString);
                int paxInput = Integer.parseInt(paxString);
                String discountstring = discount.getText().toString();
                double totalAmt = 0.0;
                // Apply discount
                Double discountAmt = Double.parseDouble(discountstring);
                if (!discountstring.isEmpty()) {
                    totalAmt = amtInput * ((100-discountAmt)/100);


                }

                // Calculate total amount with GST and SVS charges
                if (svsbtn.isChecked() && gstbtn.isChecked()) {
                    totalAmt += totalAmt * 0.19;

                } else if (svsbtn.isChecked() && !gstbtn.isChecked()) {
                    totalAmt += totalAmt * 0.1;

                } else if (!svsbtn.isChecked() && gstbtn.isChecked()) {
                    totalAmt += totalAmt * 0.09;

                } else {
                    totalAmt += amtInput;
                }

                // Calculate split amount
                double splitAmt = totalAmt / paxInput;

                // Format the values
                String totalFinal = String.format("%.2f", totalAmt);
                String splitFinal = String.format("%.2f", splitAmt);

                // Prepare display strings
                String totalDisplay = "Total Bill: $" + totalFinal;
                String eachDisplay = "";

                // Check if PayNow is selected
                if (paynow.isChecked()) {
                    String phonestring = phoneno.getText().toString();
                    eachDisplay = "Each Pays: $" + splitFinal + " via PayNow to " + phonestring;
                } else {
                    eachDisplay = "Each Pays: $" + splitFinal + " in cash";
                }

                // Update the TextViews
                ttlbill.setText(totalDisplay);
                eachpay.setText(eachDisplay);
            }
        });


    }}