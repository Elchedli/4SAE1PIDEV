package com.services.Implementations;


import com.darkprograms.speech.translator.GoogleTranslate;

import javax.swing.*;
import java.io.IOException;

public class GoogleTTS_Translate {
    public static String google_Translate(String languagetarget,String target)
    {
        String target_translated="";
        try {
            target_translated= GoogleTranslate.translate(languagetarget,target);

        }catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "No internet connection!");
        }
        return target_translated;
    }
}