/*
 * Copyright (C) 2019 yousef
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package addressbook;

import java.util.ArrayList;

/**
 *
 * @author yousef
 */
public class Address {
    private String country;
    private String city;
    private String postalCode;
    private ArrayList<String> telephoneNumbers;
    private String email;
    private int id;

    public Address(String country, String city, String postalCode, String telephoneNo, String email) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.telephoneNumbers = new ArrayList<>();
        this.telephoneNumbers.add(telephoneNo);
        this.email = email;
    }
    
    public Address(String country, String city, String postalCode, ArrayList<String> telephoneNos, String email) {
        this.country = country;
        this.city = city;
        this.postalCode = postalCode;
        this.telephoneNumbers = telephoneNos;
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public ArrayList<String> getTelephoneNumbers() {
        return telephoneNumbers;
    }
    
    public String getTelephoneNumbersInfo() {
        return this.telephoneNumbers.toString().replace("[", "").replace("]", "");
    }

    public void setTelephoneNumbers(ArrayList<String> telephoneNumbers) {
        this.telephoneNumbers = telephoneNumbers;
    }
    
    public boolean addTelephoneNo(String telephoneNo) {
        this.telephoneNumbers.add(telephoneNo);
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getInfo() {
        String result = "ID: "          + this.id + "\n" +
                        "Counrty: "     + this.country + "\n" +
                        "City: "        + this.city + "\n" +
                        "Postal Code: " + this.postalCode + "\n" +
                        "Email: "       + this.email + "\n" +
                        "Telephone Numbers: " + this.getTelephoneNumbersInfo();
        return result;
    }
    
    public static String getSelectedInfo(ArrayList<Address> addresses) {
        String result = "";
        for (Address address : addresses) {
            result += address.getInfo() + "\n";
        }
        return result;
    }
    
}
