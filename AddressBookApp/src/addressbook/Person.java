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
public class Person extends Address {
    private String firstName;
    private String lastName;
    private Date birthDate;

    public Person(String firstName, String lastName, Date birthDate, String country, String city, String postalCode, String telephoneNo, String email) {
        super(country, city, postalCode, telephoneNo, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
    
    public Person(String firstName, String lastName, Date birthDate, String country, String city, String postalCode, ArrayList<String> telephoneNos, String email) {
        super(country, city, postalCode, telephoneNos, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    @Override
    public String getInfo() {
        String result = "==========[Person]==========\n" +
                        "First Name: "  + this.firstName + "\n" +
                        "Last Name: "   + this.lastName  + "\n" +
                        "Birth Date: "  + this.birthDate.getInfo() + "\n" +
                        super.getInfo();
        return result;
    }
}
