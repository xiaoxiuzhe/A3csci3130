package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>CreateContactAcitivity</h1>
 * the activity start an object
 * some detail is required for databse
 * <b>Note:</b> forked from prof
 *
 * @author  Xiuzhe Xiao
 */
public class CreateContactAcitivity extends Activity {

    private EditText nameField, addressField, business_numberField;
    private MyApplicationData appState;
    private Spinner Primary_business, Province;

    /**
     * this method create the activity
     * @param savedInstanceState
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

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

        nameField = (EditText) findViewById(R.id.name);
        addressField = (EditText) findViewById(R.id.Address);
        business_numberField = (EditText) findViewById(R.id.Business_number);
    }

    /**
     * This method submit the object to firebase
     * @param v
     * @return void.
     */
    public void submitInfoButton(View v) {
        //each entry needs a unique ID
        String personID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();
        String business_number = business_numberField.getText().toString();
        String primary_business = Primary_business.getSelectedItem().toString();
        String province = Province.getSelectedItem().toString();

        Contact person = new Contact(personID, name, business_number, primary_business, address, province);

        appState.firebaseReference.child(personID).setValue(person);

        finish();

//        String name = Province.getSelectedItem().toString();
//        Toast.makeText(this, name,
//                Toast.LENGTH_LONG).show();

    }
}
