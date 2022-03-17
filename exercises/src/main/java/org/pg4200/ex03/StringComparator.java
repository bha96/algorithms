package org.pg4200.ex03;

import java.util.Comparator;

public class StringComparator implements Comparator<String> {

    @Override
    public int compare(String s1, String s2) {
        int length;
        int shortLength;
        if (s1.length() >= s2.length()){
            length = s1.length();
            shortLength = s2.length();
        }else{
            length = s2.length();
            shortLength = s1.length();
        }
        String lowerCaseS1 = s1.toLowerCase();
        String lowerCaseS2 = s2.toLowerCase();
        char currentLetterS1;
        char currentLetterS2;

        for (int i = 0; i < length; i++){
            if (i == shortLength){

            }
            currentLetterS1 = lowerCaseS1.charAt(i);
            currentLetterS2 = lowerCaseS2.charAt(i);
            if (currentLetterS1 < currentLetterS2){
                return -1;
            }else if(currentLetterS1 > currentLetterS2){
                return 1;
            }
        }
        return 0;
    }
}
