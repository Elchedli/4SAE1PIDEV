package com.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValide {
    
    static Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");
public static boolean verifierEmail(String email) 
{       
    Matcher m = emailPattern.matcher(email);
        return m.matches();
}
    
}