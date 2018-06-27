package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Contact implements Serializable {

    public  String uid;
    public  String name,primary_business, address, province, business_number;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    /**
     * this method is a constructor for Contact object
     * @param uid
     * @param name
     * @param business_number
     * @param primary_business
     * @param address
     * @param province
     */
    public Contact(String uid, String name, String business_number, String primary_business, String address, String province){
        this.uid = uid;
        this.name = name;
        this.business_number = business_number;
        this.primary_business = primary_business;
        this.address = address;
        this.province = province;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("business_number", business_number);
        result.put("primary_business", primary_business);
        result.put("address", address);
        result.put("province", province);

        return result;
    }
}
