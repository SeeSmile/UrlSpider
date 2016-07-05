package test;

import java.io.IOException;

import org.jsoup.Jsoup;

import sun.security.krb5.Config;
import utils.ConfigUtil;
import utils.WebUtil;

public class TokenTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String text = "1x45,,.8ae9bw";
		String number = "";
		for(int i = 0; i < text.length(); i++) {
			if(Character.isDigit(text.charAt(i)) || '.' == text.charAt(i)) {
				number += text.charAt(i);
			}
		}
		System.out.println(number);
	}
	

	
}
