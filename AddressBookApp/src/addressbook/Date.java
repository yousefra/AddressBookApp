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

import java.util.stream.IntStream;

/**
 *
 * @author yousef
 */
public final class Date {
    private int day;
    private int month;
    private int year;
    private final int[] months31 = {1,3,5,7,8,10,12};
    private final int[] months30 = {4,6,9,11};
    private final String[] monthsNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    
    public Date() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }
    
    public Date(int day, int month, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    
    public Date(String str) { // "yyyy-MM-dd"
        String[] date = str.split("-");
        this.year = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1]);
        this.day = Integer.parseInt(date[2]);
    }

    // Setters
    public void setYear(int year) {
        if(year < 1 || year > 9999) {
            System.err.println("Invalid Year.");
            return;
        }
        this.year = year;
    }

    public void setMonth(int month) {
        if(month < 1 || month > 12) {
            System.err.println("Invalid Month.");
            return;
        }
        this.month = month;
    }

    public void setDay(int day) {
        boolean is31Month = IntStream.of(this.months31).anyMatch(x -> x == this.month);
        boolean is30Month = IntStream.of(this.months30).anyMatch(x -> x == this.month);
        boolean is28Month = this.month == 2;
        int daysInFeb = 28;
        if ((this.year % 4 == 0) && ((this.year % 100 != 0) || (this.year % 400 == 0))) daysInFeb = 29;
        
        if((day < 1) || (is31Month && day > 31) || (is30Month && day > 30) || (is28Month && day > daysInFeb)) {
            System.err.println("Invalid Day.");
            return;
        }
        this.day = day;
    }
    
    // Getters
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getInfo() {
        return this.year + "-" + this.month + "-" + this.day;
    }
    
    // Functions
    public int convertToDays() {
        return (this.year * 365) + (this.month * 31) + this.day;
    }
    
    public String getFormated() {
        String modifiedDay = "0" + this.day;
        modifiedDay = modifiedDay.substring(modifiedDay.length() - 2);
        return this.monthsNames[this.month - 1] + " " + modifiedDay + ", " + this.year;
    }
    
    public void setFormated(String date) {
        String[] splitted = date.split(" ");
        this.day = Integer.parseInt(splitted[2]);
        this.month = java.util.Arrays.asList(monthsNames).indexOf(splitted[1]) + 1;
        this.year = Integer.parseInt(splitted[5]);
    }
    
    public String customFormat(String format) {
        
        // yyyy => year  (1999)
        // MM   => month (06)
        // M    => month (June)
        // dd   => day   (13)
        
        if(format.contains("yyyy"))
            format = format.replace("yyyy", Integer.toString(this.year));
        
        if(format.contains("MM")) {
            String modifiedMonth = "0" + this.month;
            modifiedMonth = modifiedMonth.substring(modifiedMonth.length() - 2);
            format = format.replace("MM", modifiedMonth);
        }
        else if(format.contains("M"))
            format = format.replace("M", this.monthsNames[this.month - 1]);
        
        if(format.contains("dd")) {
            String modifiedDay = "0" + this.day;
            modifiedDay = modifiedDay.substring(modifiedDay.length() - 2);
            format = format.replace("dd", modifiedDay);
        }
        return format;
    }
}
