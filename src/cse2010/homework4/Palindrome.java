package cse2010.homework4;

/*
 * CSE2010 Homework #4
 * Problem 1: Palindrome
 *
 * Complete the code below.
 *
 */

public class Palindrome {

    public static boolean isPalindrome(String target) {

        return isPalindrome(target, 0, target.length() - 1);
    }

    private static boolean isPalindrome(String as, int start, int end) {
    	
    	//base case
    	if (start>=end) {
    		return true;
    	}
    	
    	//if not alphabet
    	if (!isAlpha(as.charAt(start))) {
    		return isPalindrome(as, ++start, end);
    	}
    	if(!isAlpha(as.charAt(end))) {
    		return isPalindrome(as, start, --end);
    	}
    	
    	//recursive calls
        if (toLower(as.charAt(start)) != toLower(as.charAt(end))) {        	
        	return false;
        } else {
        	return isPalindrome(as, ++start, --end);
        }
}

    private static boolean isAlpha(final char ch) {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')
            return true;
        else
            return false;
    }

    private static int toLower(char c) {
        if ((c >= 'A') && (c <= 'Z'))
            return c + ('a' - 'A');
        return c;
    }

}
