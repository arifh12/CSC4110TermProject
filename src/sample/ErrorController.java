package sample;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.scene.control.Alert;

public class ErrorController {

    public static boolean numChecker(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean nameChecker(String name,int length){
        String checkname=name;
        int counter=0;
        char checkchar=0;

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
                AlertController a = new AlertController(Alert.AlertType.ERROR,"Character Error","word contail character");
                return false;
            }
        }else{
            AlertController a = new AlertController(Alert.AlertType.ERROR,"Length Error","Length Must Less than "+length+" character");
            return false;
        }
    }
   
}
