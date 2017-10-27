package com.sciencom.osd.helper;

import java.security.SecureRandom;
import java.util.Random;

public class CodeGenerator {
    public static String generate(int length){
        String[] xxx = {"1", "2", "3", "4", "5",
        "6", "7", "8", "9", "0"};
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<length; i++){
            int index = random.nextInt(xxx.length);
            String code = xxx[index];
            sb.append(code);
        }
        return sb.toString();
    }
}
