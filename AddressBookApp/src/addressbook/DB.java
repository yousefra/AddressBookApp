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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yousef
 */
public class DB {
    
    private static final Connection CONN = getConnection();
    private static PreparedStatement ps;
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost/addressbook", "root", "");
            return con;
        }
        catch(ClassNotFoundException | SQLException ex) {
            return null;
        }
    }
    
    public static boolean isConnected() {
        return CONN != null;
    }
    
    public static int insertAddress(String country, String city, String postalCode, String telephoneNos, String email) {
        int addressID = -1;
        try {    
            String query = "INSERT INTO addresses(country, city, postalCode, telephoneNos, email, createdDate, hidden) VALUES(?,?,?,?,?, NOW(), 0)";
            ps = CONN.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, country);
            ps.setString(2, city);
            ps.setString(3, postalCode);
            ps.setString(4, telephoneNos);
            ps.setString(5, email);
            ps.executeUpdate();
            ResultSet res = ps.getGeneratedKeys();
            if(res.next()) {
                addressID = res.getInt(1);
            }
            ps.close();
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addressID;
    }
    
    public static boolean insertPerson(int addressID, String firstName, String lastName, Date birthDate) {
        try {
            String query = "INSERT INTO persons(addressID, firstName, lastName, birthDate) VALUES("+addressID+", ?, ?, ?)";
            ps = CONN.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, birthDate.getInfo());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean insertBusiness(int addressID, String title, String genre, String contactPerson, String website) {
        try {
            String query = "INSERT INTO businesses(addressID, title, genre, contactPerson, website) VALUES("+addressID+", ?, ?, ?, ?)";
            ps = CONN.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setString(3, contactPerson);
            ps.setString(4, website);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ArrayList<Address> getAddresses() {
        ArrayList<Address> result = new ArrayList<>();
        try {
            Statement st = CONN.createStatement();
            String query = "SELECT IF(p.firstName IS NULL, 2, 1) as type, a.id, a.country, a.city, a.postalCode, a.telephoneNos, a.email, p.firstName, p.lastName, p.birthDate, b.title, b.genre, b.contactPerson, b.website FROM `addresses` a LEFT JOIN `persons` p ON a.id = p.addressID LEFT JOIN `businesses` b ON a.id = b.addressID WHERE a.hidden != 1";
            ResultSet res = st.executeQuery(query);
            while (res.next()) {
                int type = res.getInt("type");
                Address newAddress = null;
                int addressID = res.getInt("id");
                String country = res.getString("country");
                String city = res.getString("city");
                String postalCode = res.getString("postalCode");
                ArrayList<String> telephoneNos = new ArrayList(Arrays.asList(res.getString("telephoneNos").split(", ")));
                String email = res.getString("email");
                if(type == 1) {
                    String firstName = res.getString("firstName");
                    String lastName = res.getString("lastName");
                    Date birthDate = new Date(res.getString("birthDate"));
                    newAddress = new Person(firstName, lastName, birthDate, country, city, postalCode, telephoneNos, email);
                } else if (type == 2) {
                    String title = res.getString("title");
                    String genre = res.getString("genre");
                    String contactPerson = res.getString("contactPerson");
                    String website = res.getString("website");
                    newAddress = new Business(title, genre, contactPerson, website, country, city, postalCode, telephoneNos, email);
                }
                newAddress.setId(addressID);
                result.add(newAddress);
            }
            st.close();
            res.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddressBook.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static boolean deleteAddresses(ArrayList<Integer> ids) {
        try {
            String query = "UPDATE addresses SET hidden = 1 WHERE ";
            for (int i = 0; i < ids.size(); i++) {
                if(i!=0) query += " OR ";
                query += "id = " + ids.get(i);
            }
            Statement st = CONN.createStatement();
            st.executeUpdate(query);
            st.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void updateAddresses(Address address) {
        int addressID = -1;
        try {
            String query = "UPDATE addresses SET country = ?, city = ?, postalCode = ?, telephoneNos = ?, email = ? WHERE id = " + address.getId();
            ps = CONN.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address.getCountry());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getPostalCode());
            ps.setString(4, address.getTelephoneNumbersInfo());
            ps.setString(5, address.getEmail());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public static boolean updatePerson(Address address) {
        try {
            updateAddresses(address);
            String query = "UPDATE persons SET firstName = ?, lastName = ?, birthDate = ? WHERE addressID = " + address.getId();
            ps = CONN.prepareStatement(query);
            ps.setString(1, ((Person)address).getFirstName());
            ps.setString(2, ((Person)address).getLastName());
            ps.setString(3, ((Person)address).getBirthDate().getInfo());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean updateBusiness(Address address) {
        try {
            updateAddresses(address);
            String query = "UPDATE businesses SET title = ?, genre = ?, contactPerson = ?, website = ? WHERE addressID = " + address.getId();
            ps = CONN.prepareStatement(query);
            ps.setString(1, ((Business)address).getTitle());
            ps.setString(2, ((Business)address).getGenre());
            ps.setString(3, ((Business)address).getContactPerson());
            ps.setString(4, ((Business)address).getWebsite());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static boolean restoreDeletedAddresses() {
        String query = "UPDATE addresses SET hidden = 0 WHERE hidden = 1";
        Statement st;
        try {
            st = CONN.createStatement();
            st.executeUpdate(query);
            st.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
