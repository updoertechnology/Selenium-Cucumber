package com.demo.utils;

import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Period;
import java.util.List;
import java.util.Random;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.NumberFormat;
import java.util.Locale;


public class Base {
    DateTimeFormatter dateWithMonth = DateTimeFormatter.ofPattern("MMM d, yyyy");
    DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    /***
     * get random number from the list size
     * @param element - list of elements
     * @return
     */
    public int getRandomNumber(List<WebElement> element){

        Random rand = new Random();
        return  rand.nextInt(element.size());
    }

    /***
     * get random number from the list size
     * @param //int - list of number
     * @return
     */
    public int getRandomNumber(int i){

        Random rand = new Random();
        return  rand.nextInt(i);
    }

    /***
     * Method getStringValue(String value) returns String value only from string(Seperate Numbers from String)
     * @param value - pass like "test(1s-)"....
     * @return - String
     */
    public String getStringValue(String value){

        String string="";
        for (int i =0; i<value.length(); i++ ){

            char character = value.charAt(i);
            if(Character.isLetter(character) || character == '/'
                    || character == '-' || Character.isSpaceChar(character)) string = string+character;
        }
        return string;
    }

    public String getIntegerValue(String value){

        String string="";
        for (int i =0; i<value.length(); i++ ){

            char character = value.charAt(i);
            if(!Character.isLetter(character)
                    || !Character.isSpaceChar(character)) string = string+character;
        }
        return string;
    }


    public static String setPunctuationMarkWithTitleSize(String title, String punctuationMark){

        String hyphen= "";
        for(int i= 0; i<title.length()+1;++i){

            hyphen += punctuationMark;
        }
        return hyphen;
    }

    public String fetchQueryFromTxtFile(String txtFile){

        String query ="";
        try {
            query = new String(Files.readAllBytes(Paths.get(txtFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return query;
    }

    public String convertBooleanToString(boolean status){
        return String.valueOf(status);
    }

    public String getTodayDate() {
        LocalDate currentDate = LocalDate.now();
        String todayDate = currentDate.format(simpleDateFormat);
        System.out.println("todayDate: "+todayDate);
        return todayDate;
    }

    public String getTomorrowDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrowDate = currentDate.plusDays(1);
        String tomorrowDateString = tomorrowDate.format(simpleDateFormat);
        System.out.println("tomorrowDateString: "+tomorrowDateString);
        return tomorrowDateString;
    }

    public String get6DaysLaterDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate sixDate = currentDate.plusDays(6);
        String sixDateString = sixDate.format(simpleDateFormat);
        System.out.println("get6DaysLaterDate: "+sixDateString);
        return sixDateString;
    }
    public String get5DaysLaterDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate fiveDate = currentDate.plusDays(5);
        String fiveDateString = fiveDate.format(simpleDateFormat);
        System.out.println("get5DaysLaterDate: "+fiveDateString);
        return fiveDateString;
    }

    public String get30DaysLaterDate(){
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDate = currentDate.plusDays(31);
        String thirtyDateString = thirtyDate.format(simpleDateFormat);
        System.out.println("get30DaysLaterDate: "+thirtyDateString);
        return thirtyDateString;
    }


    public String getTodayDateWithMonthText() {
        LocalDate currentDate = LocalDate.now();
        String todayDate = currentDate.format(dateWithMonth);
        System.out.println("getTodayDateWithMonthText: "+todayDate);
        return todayDate;
    }
    public String get6DaysLaterDateWithMonthText() {
        LocalDate currentDate = LocalDate.now();
        LocalDate sixDate = currentDate.plusDays(6);
        String sixDateString = sixDate.format(dateWithMonth);
        System.out.println("get6DaysLaterDateWithMonthText: "+sixDateString);
        System.out.println("getTodayDateWithMonthText: "+currentDate);
        return sixDateString;
    }
    public String get5DaysLaterDateWithMonthText() {
        LocalDate currentDate = LocalDate.now();
        LocalDate fiveDate = currentDate.plusDays(5);
        String fiveDateString = fiveDate.format(dateWithMonth);
        System.out.println("get5DaysLaterDateWithMonthText: "+fiveDateString);
        System.out.println("getTodayDateWithMonthText: "+currentDate);
        return fiveDateString;
    }

    public String getTomorrowDateWithMonthText(){
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrowDate = currentDate.plusDays(1);
        String tomorrowDateString = tomorrowDate.format(dateWithMonth);
        System.out.println("getTomorrowDateWithMonthText: "+tomorrowDateString);
        return tomorrowDateString;
    }
    public String getTomorrow2DateWithMonthText(){
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrowDate = currentDate.plusDays(2);
        String tomorrow2DateString = tomorrowDate.format(dateWithMonth);
        System.out.println("getTomorrowDateWithMonthText: "+tomorrow2DateString);
        return tomorrow2DateString;
    }

    public String IntFormatCurrency(int amount, String countryInitial) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", countryInitial));
        return currencyFormatter.format(amount / 100.0);
    }

    public String DoubleFormatCurrencyZA(Double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "ZA"));
        return currencyFormatter.format(amount).replace(".",",");
    }

    public String getCurrentTime(){
        LocalTime currentTime = LocalTime.now();
        String current = currentTime.format(DateTimeFormatter.ofPattern("kk:mm"));
        System.out.println("getCurrentTime() "+current);
        return current;
    }

    public String getCurrentTimeOneHourDigit(){
        LocalTime currentTime = LocalTime.now();
        String current = currentTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
        System.out.println("getCurrentTime() "+current);
        return current;
    }

    public String get15TimeDifference(){
        LocalTime currentTime = LocalTime.now();
        LocalTime time15MinutesAhead = currentTime.plusMinutes(15);
        String diffTime = time15MinutesAhead.format(DateTimeFormatter.ofPattern("kk:mm"));
        System.out.println("get15TimeDifference() "+diffTime);
        return diffTime;
    }

    public String get15TimeDifferenceOneHourDigit(){
        LocalTime currentTime = LocalTime.now();
        LocalTime time15MinutesAhead = currentTime.plusMinutes(15);
        String diffTime = time15MinutesAhead.format(DateTimeFormatter.ofPattern("hh:mm a"));
        System.out.println("get15TimeDifference() "+diffTime);
        return diffTime;
    }

    public int getDaysDifference(String firstDate, String secondDate) {
        LocalDate firstDateL = LocalDate.parse(firstDate, simpleDateFormat);
        LocalDate secondDateL = LocalDate.parse(secondDate, simpleDateFormat);
        Period difference = Period.between(secondDateL,firstDateL);
        System.out.println("getDaysDifference: "+difference.getDays());
        return difference.getDays();
    }

    public String getDayOfWeek(String date){
        LocalDate localDate = LocalDate.parse(date, simpleDateFormat);
        String dayOfWeek = localDate.getDayOfWeek().toString();
        return dayOfWeek;
    }



}
