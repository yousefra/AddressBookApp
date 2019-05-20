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
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author yousef
 */
public class AddressBook implements Compare {
    private ArrayList<Address> addresses;
    
    public AddressBook() {
        addresses = new ArrayList<>();
    }
    
    public void addAddress(Address address) {
        this.addresses.add(address);
    }
    
    public boolean removeAddress(int index) {
        if(index > -1 && index < this.addresses.size()) {
            this.addresses.remove(index);
            return true;
        }
        return false;
    }
    
    public Address getAddress(int index) {
        if(index > -1 && index < this.addresses.size()) {
            return this.addresses.get(index);
        }
        return null;
    }
    
    public int getNumberOfAddresses() {
        return this.addresses.size();
    }
    
    public boolean isEmpty() {
        return this.addresses.isEmpty();
    }
    
    public ArrayList<Address> getAllAddresses() {
        return this.addresses;
    }
    
    public String getInfo() {
        String result = "";
        for (Address address : addresses) {
            result += address.getInfo() + "\n";
        }
        return result;
    }
    
    @Override
    public ArrayList<Address> compareBy(String str) {
        ArrayList<Address> result = new ArrayList<>();
        str = str.toLowerCase();
        for(Address address : this.addresses) {
            if(
                (address instanceof Person &&
                    (((Person)address).getFirstName().toLowerCase().contains(str) || ((Person)address).getLastName().toLowerCase().contains(str))
                )
                ||
                (address instanceof Business &&
                    ((Business)address).getTitle().toLowerCase().contains(str)
                )
            )
            {
               result.add(address);
            }
        }
        return result;
    }
    
    public ArrayList<Person> sortPersonsByLastName(ArrayList<Person> persons) {
        Collections.sort(persons, new Comparator<Person>(){
             @Override
             public int compare(Person p1, Person p2) {
               return p1.getLastName().compareToIgnoreCase(p2.getLastName());
            }
        });
        return persons;
    }
    
    public ArrayList<Business> sortBusinessesByTitle(ArrayList<Business> businesses) {
        Collections.sort(businesses, new Comparator<Business>(){
             @Override
             public int compare(Business b1, Business b2) {
               return b1.getTitle().compareToIgnoreCase(b2.getTitle());
            }
        });
        return businesses;
    }
    
    public ArrayList<Person> sortPersonsById(ArrayList<Person> persons) {
        Collections.sort(persons, new Comparator<Person>(){
             @Override
             public int compare(Person a1, Person a2) {
               return Integer.compare(a1.getId(), a2.getId());
            }
        });
        return persons;
    }
    
    public ArrayList<Business> sortBusinessesById(ArrayList<Business> businesses) {
        Collections.sort(businesses, new Comparator<Business>(){
             public int compare(Business a1, Business a2) {
               return Integer.compare(a1.getId(), a2.getId());
            }
        });
        return businesses;
    }
    
    // Database methods
    public ArrayList<Address> compareByDB(String str) {
        ArrayList<Address> result = DB.getAddresses();
        str = str.toUpperCase();
        for (int i = 0; i < result.size(); i++) {
            if(result.get(i) instanceof Person && !((Person)result.get(i)).getFirstName().toUpperCase().contains(str) && !((Person)result.get(i)).getLastName().toUpperCase().contains(str)
               || result.get(i) instanceof Business && !((Business)result.get(i)).getTitle().toUpperCase().contains(str)) {
                result.remove(i--);
            }
        }
        return result;
    }
    public boolean addAddressDB(Address address) {
        int addressID = DB.insertAddress(address.getCountry(), address.getCity(), address.getPostalCode(), address.getTelephoneNumbersInfo(), address.getEmail());
        if(addressID != -1) {
            boolean insertRes;
            if(address instanceof Person) {
                insertRes = DB.insertPerson(addressID, ((Person) address).getFirstName(), ((Person) address).getLastName(), ((Person) address).getBirthDate());
            }
            else {
                insertRes = DB.insertBusiness(addressID, ((Business) address).getTitle(), ((Business) address).getGenre(), ((Business) address).getContactPerson(), ((Business) address).getWebsite());
            }
            return insertRes;
        } else {
            return false;
        }
    }
    
    public ArrayList<Address> getAddressesFromDB() {
        return DB.getAddresses();
    }
    
    public boolean deleteAddressesDB(ArrayList<Integer> ids) {
        return DB.deleteAddresses(ids);
    }
    
    public boolean updateAddressDB(Address address) {
        if(address instanceof Person) {
            return DB.updatePerson(address);
        }
        else if(address instanceof Business) {
            return DB.updateBusiness(address);
        }
        return false;
    }
    
    public void restoreDeletedAddresses() {
        DB.restoreDeletedAddresses();
    }
}
