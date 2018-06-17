package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DetailViewActivity extends Activity {

    private EditText nameField, addressField, business_numberField;
    private Spinner Primary_business, Province;
    Contact receivedPersonInfo;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData) getApplicationContext());
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        nameField = (EditText) findViewById(R.id.name);
        business_numberField = (EditText) findViewById(R.id.Business_number);
        addressField = (EditText) findViewById(R.id.Address);

        // Spinner element
        Primary_business = (Spinner) findViewById(R.id.Primary_business);
        Province = (Spinner) findViewById(R.id.Province);

        // Spinner Drop down elements
        List<String> Primary_business_types = new ArrayList<String>();
        Primary_business_types.add("Fisher");
        Primary_business_types.add("Distributor");
        Primary_business_types.add("Processor");
        Primary_business_types.add("Fish Monger");

        List<String> Province_names = new ArrayList<String>();
        Province_names.add("AB");
        Province_names.add("BC");
        Province_names.add("MB");
        Province_names.add("NB");
        Province_names.add("NL");
        Province_names.add("NS");
        Province_names.add("NT");
        Province_names.add("NU");
        Province_names.add("ON");
        Province_names.add("PE");
        Province_names.add("QC");
        Province_names.add("SK");
        Province_names.add("YT");
        Province_names.add(" ");

        // Creating adapter for spinner
        ArrayAdapter<String> Adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Primary_business_types);
        ArrayAdapter<String> Adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Province_names);

        // Drop down layout style - list view with radio button
        Adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        Primary_business.setAdapter(Adapter1);
        Province.setAdapter(Adapter2);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            addressField.setText(receivedPersonInfo.address);
            business_numberField.setText(receivedPersonInfo.business_number);
            Primary_business.setSelection(Primary_business_types.indexOf(receivedPersonInfo.primary_business));
            Province.setSelection(Province_names.indexOf(receivedPersonInfo.province));
        }
    }

    public void updateContact(View v){
        String personID = receivedPersonInfo.uid;
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();
        String business_number = business_numberField.getText().toString();
        String primary_business = Primary_business.getSelectedItem().toString();
        String province = Province.getSelectedItem().toString();

        Contact updatePersonInfo = new Contact(personID, name, business_number, primary_business, address, province);

        appState.firebaseReference.child(personID).setValue(updatePersonInfo);

        finish();
    }

    public void eraseContact(View v)
    {
        String personID = receivedPersonInfo.uid;
        appState.firebaseReference.child(personID).removeValue();

        finish();
    }
}
