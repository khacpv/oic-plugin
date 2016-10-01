package com.oic.plugin.multidimens.common;

/**
 * Created by khacpham on 10/1/16.
 */
public class StringUtils {

    /**
     * remove all non-digits from text. (ex: 123-456-789 --> 123456789)
     */
    public static String extractNumber(String text){
        return text.replaceAll("\\D+","");
    }
}
