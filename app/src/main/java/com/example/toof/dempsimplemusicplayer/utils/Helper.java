package com.example.toof.dempsimplemusicplayer.utils;

import java.text.SimpleDateFormat;

public class Helper {
    public static String formatDate(Object input, String outputFormat) {
        String result;
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
        result = outputDateFormat.format(input);
        return result;
    }
}
