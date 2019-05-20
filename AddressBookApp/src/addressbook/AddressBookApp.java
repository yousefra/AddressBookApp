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

import java.util.Scanner;

/**
 *
 * @author yousef
 */
public class AddressBookApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Scanner input2 = new Scanner(System.in);
        AddressBook addressBook = new AddressBook();
        int choice = 0;
        while(choice != 9) {
            System.out.println(
                  "1. Add Person\n"
                + "2. Add Business\n"
                + "3. List of Addresses\n"
                + "4. Search for address\n"
                + "5. Number of addresses\n"
                + "6. Remove address\n"
                + "9. Exit");
            System.out.print("Enter Choice: ");
            input = new Scanner(System.in);
            try {
                choice = Integer.parseInt(input.next());
            }
            catch(NumberFormatException ex) {
                System.err.println("Invalid input.");
                continue;
            }
            catch(Exception ex) {
                System.err.println("Something went wrong.");
                continue;
            }
            
            Date birthDate = null;
            String firstName = "", lastName = "", title = "", genre = "", contactPerson = "", website = "", country = "", city = "", postalCode = "", telephoneNo = "", email = "";
            if(choice == 1 || choice == 2) {
                if(choice == 1) {
                    boolean correctDate = false;
                    System.out.print("First Name: ");
                    firstName = input2.nextLine();
                    System.out.print("Last Name: ");
                    lastName = input2.nextLine();
                    while(!correctDate) {
                        int year, month, day;
                        try {
                            System.out.print("Birth Date year: ");
                            year = Integer.parseInt(input.next());
                            System.out.print("Birth Date month: ");
                            month = Integer.parseInt(input.next());
                            System.out.print("Birth Date day: ");
                            day = Integer.parseInt(input.next());
                            birthDate = new Date(day, month, year);
                            correctDate = true;
                        }
                        catch(NumberFormatException ex) {
                            System.err.println("Invalid Input.");
                        }
                    }
                }
                else {
                    System.out.print("Title: ");
                    title = input2.nextLine();
                    System.out.print("Genre: ");
                    genre = input2.nextLine();
                    System.out.print("Contact Person: ");
                    contactPerson = input2.nextLine();
                    System.out.print("Website: ");
                    website = input2.nextLine();
                }
                System.out.print("Country: ");
                country = input2.nextLine();
                System.out.print("City: ");
                city = input2.nextLine();
                System.out.print("Postal Code: ");
                postalCode = input2.nextLine();
                System.out.print("Telephone Number: ");
                telephoneNo = input2.nextLine();
                System.out.print("Email: ");
                email = input2.nextLine();
                
                Address address;
                if(choice == 1) {
                    address = new Person(firstName, lastName, birthDate, country, city, postalCode, telephoneNo, email);
                    System.out.println("\n=> Person added successfully.\n");
                }
                else {
                    address = new Business(title, genre, contactPerson, website, country, city, postalCode, telephoneNo, email);
                    System.out.println("\n=> Business added successfully.\n");
                }
                addressBook.addAddress(address);
                
            }
            else if(choice == 3) {
                if(addressBook.isEmpty())
                    System.out.println("\n=> No addresses found!\n");
                else 
                    System.out.println(addressBook.getInfo());
            }
            else if(choice == 4) {
                System.out.print("Enter a text: ");
                String str = input2.nextLine();
                System.out.println(Address.getSelectedInfo(addressBook.compareBy(str)));
            }
            else if(choice == 5) {
                if(addressBook.isEmpty())
                    System.out.println("\n=> No addresses found!\n");
                else
                    System.out.println("\n=> There is " + addressBook.getNumberOfAddresses() + " addresses.\n");
            }
            else if(choice == 6) {
                System.out.print("Enter address index: ");
                int index = input.nextInt();
                if(addressBook.removeAddress(index))
                    System.out.println("\n=> Addresses removed successfully!\n");
                else 
                    System.out.println("\n=> There was an error!\n");
            }
        }
    }
    
}
