package main;
/*
this class uses certain methods to check specific formats. for example if the number is a digit or not?
a string is actually a string or not?
and many other things
 */
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ErrorController {

    public static boolean numChecker(String text,String message) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Number Error",message+" must be a number(digit)");
            return false;
        }
    }
    public static boolean nameChecker(String name,int length,String message){
        String checkname=name;
        int counter=0;
        char checkchar=0;

        if(name.equals("")){
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Empty Error",message+" cannot be empty");
        }

        for(int i=0;i<checkname.length();i++)
        {
            checkchar= checkname.charAt(i);

            if((checkchar<=90 && checkchar>=65)||(checkchar<=122 && checkchar>=97) || checkchar==32)
            {
                counter++;
            }
        }
        if(checkname.length() < length){
            if(counter==checkname.length()){
                return true;
            }
            else{
                AlertController a = new AlertController(Alert.AlertType.ERROR,"Character Error",message+" contain Illegal character");
                return false;
            }
        }else{
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Length Error",message+" Length Must Less than "+length+" character");
            return false;
        }
    }

    public static boolean strChecker(String name,int length,String message){
        String checkname=name;
        int counter=0;
        char checkchar=0;

        if(name.equals("")){
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Empty Error",message+" cannot be empty");
        }

        for(int i=0;i<checkname.length();i++)
        {
            checkchar= checkname.charAt(i);

            if((checkchar<=90 && checkchar>=58)||(checkchar<=126 && checkchar>=91) ||(checkchar<=47 && checkchar>=32) )
            {
                counter++;
            }
        }
        if(checkname.length() < length){
            if(counter==checkname.length()){
                return true;
            }
            else{
                AlertController a = new AlertController(Alert.AlertType.ERROR,"Character Error",message+" contain Illegal character");
                return false;
            }
        }else{
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Length Error",message+" Length Must Less than "+length+" character");
            return false;
        }
    }

    public static boolean passwordChecker(String name,String name2,String message){
        String checkname=name;
        int counter=0;
        char checkchar=0;

        if(name.equals("")){
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Empty Error",message+" cannot be empty");
            return false;
        }

        int a =0 , b = 0, c = 0, d = 0;

        for(int i=0;i<checkname.length();i++)
        {
            checkchar= checkname.charAt(i);
            if(checkchar <= 57 && checkchar >= 48){
                a++;
            }
            if(checkchar >= 97 && checkchar <= 122){
                b++;
            }
            if(checkchar >= 65 && checkchar <= 90){
                c++;
            }

            if((checkchar<=64 && checkchar>=58)||(checkchar<=96 && checkchar>=91)||(checkchar<=126 && checkchar>=123) ||(checkchar<=47 && checkchar>=32) )
            {
                d++;
            }

        }
        if((a >= 1) && (b >= 1) && (c >= 1) && (d >= 1)){
            counter = a + b + c + d;
        }else{
            AlertController aa = new AlertController(Alert.AlertType.ERROR,"Character Error",message+" must contain special charater and alphanumeric character");
            return false;
        }
        if(checkname.length() < 17 && checkname.length() > 7){
            if(counter==checkname.length()){
                if(name.equals(name2)){
                    return true;
                }
                else{
                    AlertController aa = new AlertController(Alert.AlertType.ERROR,"Match Error",message+" does not match");
                    return false;
                }
            }
            else{
                AlertController aa = new AlertController(Alert.AlertType.ERROR,"Character Error",message+" must contain special charater and alphanumeric character");
                return false;
            }
        }else{
            AlertController aa = new AlertController(Alert.AlertType.ERROR,"Length Error",message+" Length Must be 8 to 16 alphanumeric and special character");
            return false;
        }
    }

    public static boolean phnChecker(String name){
        String checkname=name;
        int counter=0;
        char checkchar=0;

        try {
            if (checkname.charAt(3) == '-' && checkname.charAt(7) == '-') {
                try {
                    int verify = checkname.charAt(0) + checkname.charAt(1) + checkname.charAt(2) + checkname.charAt(4) + checkname.charAt(5) + checkname.charAt(6) + checkname.charAt(8) + checkname.charAt(9) + checkname.charAt(10) + checkname.charAt(11);
                    return true;
                } catch (Exception e) {
                    AlertController a = new AlertController(Alert.AlertType.ERROR, "Number Error", "Phone Number Must be contain numbers and in format[xxx-xxx-xxxx]");
                }
            } else {
                AlertController a = new AlertController(Alert.AlertType.ERROR, "Format Error", "Phone Number Must be in format[xxx-xxx-xxxx]");
            }
        }catch(Exception e){
            AlertController a = new AlertController(Alert.AlertType.ERROR, "Number Error", "Phone Number Must be contain numbers and in format[xxx-xxx-xxxx]");
        }
        return false;
    }
    public static boolean pastChecker(String value){

        if(value.equals("")){
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Empty Error","date cannot be empty");
            return false;
        }
        // Create SimpleDateFormat object
        SimpleDateFormat
                sdfo
                = new SimpleDateFormat("yyyy-MM-dd");

        // Get the two dates to be compared
        Date d1 = null;
        Date d2 = null;
        try {
            LocalDate todayDate = LocalDate.now();
            d1 = sdfo.parse(value);
            d2 = sdfo.parse(todayDate.toString());
            System.out.println("Date1 : " + sdfo.format(d1));
            System.out.println("Date2 : " + sdfo.format(d2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Print the dates

        // Compare the dates using compareTo()
        if (d1.compareTo(d2) > 0) {

            // When Date d1 > Date d2
            // future date
            System.out.println("Date1 is after Date2");
            return true;
        }

        else if (d1.compareTo(d2) < 0) {

            // When Date d1 < Date d2
            System.out.println("Date1 is before Date2");
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Date Error","date cannot be added to the past");
            return false;

        }

        else if (d1.compareTo(d2) == 0) {

            // When Date d1 = Date d2
            System.out.println("Date1 is equal to Date2");
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Date Error","date cannot be equal to today");
            return false;
        }
        return false;
    }
}

