package com.timecheck.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String replaceWhiteSpace(String str) {
        String destination = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            destination = m.replaceAll("");
        }
        return destination;
    }
}
