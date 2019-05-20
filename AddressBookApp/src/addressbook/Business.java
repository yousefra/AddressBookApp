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
public class Business extends Address {
    private String title;
    private String genre;
    private String contactPerson;
    private String website;

    public Business(String title, String genre, String contactPerson, String website, String country, String city, String postalCode, String telephoneNo, String email) {
        super(country, city, postalCode, telephoneNo, email);
        this.title = title;
        this.genre = genre;
        this.contactPerson = contactPerson;
        this.website = website;
    }
    
    public Business(String title, String genre, String contactPerson, String website, String country, String city, String postalCode, ArrayList<String> telephoneNos, String email) {
        super(country, city, postalCode, telephoneNos, email);
        this.title = title;
        this.genre = genre;
        this.contactPerson = contactPerson;
        this.website = website;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
    @Override
    public String getInfo() {
        String result = "=========[Business]=========\n" +
                        "Title: "   + this.title + "\n" +
                        "Genre: "   + this.genre  + "\n" +
                        "Website: " + this.website + "\n" +
                        "Contact Person: " + this.contactPerson + "\n" +
                        super.getInfo();
        return result;
    }
}
