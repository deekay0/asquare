package edu.cmu.square.client.utils;

import java.util.List;

public class SquareUtil
{
	public static String firstCharacterToUpperCase(String text)
	{
		if (text.trim().length() > 0)
		{
			String newString = "";
			text = text.toLowerCase();
			char[] charArray = text.toCharArray();
			int count = 0;

			for (char c : charArray)
			{
				if (count == 0)
				{
					c = Character.toUpperCase(c);

				}
				newString = newString + c;

				count++;
			}
			return newString;
		}
		return text;

	}
	
	public static String createHTMLList(List<String> elements)
	{
		if (elements.size() == 0)
		{
			return "";
		}
		
		StringBuilder listBuilder = new StringBuilder();
		listBuilder.append("<ul>");
	
		for (String warning : elements)
		{
			listBuilder.append("<li>" + warning + "</li>");
		}
		
		//listBuilder.append("</ul>");
		
		return listBuilder.toString();
	}
	

	/**
	 * This generates a random password that contains only alphanumeric
	 * characters
	 * 
	 * @return
	 */
	public static String generatePassword()
	{
		String password = "";
		String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_1234567890!@#$%^&*.?";
		for (int i = 0; i < 12; i++)
		{
			password = password + String.valueOf(validChars.charAt((int) (Math.random() * validChars.length())));
		}
		return password;
	}
	
	public static String getEmailRegex()
	{
		return ".+@.+\\..+";
	}

}
