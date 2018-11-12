/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Condition;

import java.text.*;
import java.util.regex.*;

/**
 *
 * @author MCT
 */
public class CheckCode {

    public boolean checkStudentID(String id) {
        String str = "^(GCS|GBS)\\d{5}$";
        if (!id.matches(str)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkName(String Name) {
        int i = 0;
        while (i < Name.length()) {
            if (((Name.charAt(i) >= 'a') && (Name.charAt(i) <= 'z')) || ((Name.charAt(i) >= 'A') && (Name.charAt(i) <= 'Z')) || (Name.charAt(i) == ' ')) {
                i++;
            } else {
                break;
            }
        }
        if (i == Name.length()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPhone(String Phonenumber) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(Phonenumber);
        if (!matcher.matches()) {
            return false;
        } else if (Phonenumber.length() == 10 || Phonenumber.length() == 11) {
            return true;
        } else {
            return false;

        }
    }

    public boolean checkEmail(String Email) {
        String str = "^[_A-Za-z0-9]{5,30}(@FPT.EDU.VN|@fpt.edu.vn)";
        if (!Email.matches(str)) {
            return false;
        } else {
            return true;
        }
    }

}
