package com.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class MessagerieUtils {
	private static Scanner myReader;
	public static String filterbadwords(String message) throws FileNotFoundException{
		String badwordchange = "@#^&$!";
//		System.out.println("votre path est : "+System.getProperty("user.dir"));
		File myObj = new File("src/main/resources/chedli/badwords.txt");
		myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine().toLowerCase();
			if ((message.toLowerCase()).contains(data)) {
				Random rd = new Random();
				String newdata = "";
				for (int i = 0; i < data.length(); i++) {
					char letter = badwordchange.charAt(rd.nextInt(badwordchange.length()));
					newdata += letter;
				}
				System.out.println(newdata);
//				message = message.replace(data,newdata);
				message = message.replaceAll("(?i)"+data,newdata); // regex used here for case insensitive
				System.out.println("avant : "+message);
			}
		}
		System.out.println("apres : "+message);
		return message;	
	}
}
