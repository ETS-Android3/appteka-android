package com.tomclaw.appsend.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Solkin
 * Date: 13.10.13
 * Time: 20:09
 */
public class StringUtil {

    public static final int DEFAULT_ALPHABET_INDEX = '?';

    public static final String UTF8_ENCODING = "UTF-8";

    public static Random random;

    static {
        random = new Random(System.currentTimeMillis());
    }

    public static int getAlphabetIndex(String name) {
        for (int c = 0; c < name.length(); c++) {
            char character = name.charAt(c);
            if (Character.isLetterOrDigit(character)) {
                return Character.toUpperCase(character);
            }
        }
        return DEFAULT_ALPHABET_INDEX;
    }

    public static boolean isNumeric(String value) {
        return !TextUtils.isEmpty(value) && TextUtils.isDigitsOnly(value);
    }

    public static void copyStringToClipboard(Context context, String string) {
        copyStringToClipboard(context, string, 0);
    }

    public static void copyStringToClipboard(Context context, String string, int toastText) {
        ClipboardManager clipboardManager = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", string));
        if (toastText > 0) {
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    public static String urlEncode(String string) {
        try {
            return URLEncoder.encode(string, UTF8_ENCODING).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }

    public static String urlDecode(String string) {
        try {
            return URLDecoder.decode(string, UTF8_ENCODING);
        } catch (UnsupportedEncodingException ignored) {
            return string;
        }
    }

    public static String appendIfNotEmpty(String where, String what, String divider) {
        if (!isEmptyOrWhitespace(what)) {
            if (!isEmptyOrWhitespace(where)) {
                where += divider;
            }
            where += what;
        }
        return where;
    }

    public static boolean isEmptyOrWhitespace(String string) {
        return TextUtils.isEmpty(string) || TextUtils.isEmpty(string.trim());
    }

    public static String getLocation(String country, String city) {
        String location = "";
        if (!TextUtils.isEmpty(country)) {
            location = country;
        }
        if (!TextUtils.isEmpty(city)) {
            if (!TextUtils.isEmpty(location)) {
                location += ", ";
            }
            location += city;
        }
        return location;
    }

    public static String getLocationPriorCity(String country, String city) {
        String location = "";
        if (TextUtils.isEmpty(city)) {
            if (!TextUtils.isEmpty(country)) {
                location = country;
            }
        } else {
            location = city;
        }
        return location;
    }

    public static String generateRandomText(Random r) {
        int wordCount = 10 + r.nextInt(13);
        return generateRandomText(r, wordCount);
    }

    public static String generateRandomText(Random r, int wordCount) {
        StringBuilder sb = new StringBuilder(wordCount);
        for (int i = 0; i < wordCount; i++) { // For each letter in the word
            sb.append(generateRandomWord(r, i == 0))
                    .append((i < (wordCount - 1)) ? " " : "."); // Add it to the String
        }
        return sb.toString();
    }

    private static String generateRandomWord(Random r) {
        return generateRandomWord(r, true);
    }

    private static String generateRandomWord(Random r, boolean capitalize) {
        String word = generateRandomString(r, 4, 10);
        if (capitalize) {
            return String.valueOf(word.charAt(0))
                    .toUpperCase(Locale.getDefault()) + word.substring(1);
        } else {
            return word;
        }
    }

    public static String generateCookie() {
        return Long.toHexString(System.currentTimeMillis()) + "-" + generateRandomString(random, 16, 16);
    }

    public static String generateBoundary() {
        return Long.toHexString(System.currentTimeMillis()) + generateRandomString(random, 16, 16);
    }

    public static String generateRandomString() {
        return generateRandomString(16);
    }

    public static String generateRandomString(int length) {
        return generateRandomString(random, length, length);
    }

    public static String generateRandomString(Random r, int minChars, int maxChars) {
        int wordLength = minChars;
        int delta = maxChars - minChars;
        if (delta > 0) {
            wordLength += r.nextInt(delta);
        }
        StringBuilder sb = new StringBuilder(wordLength);
        for (int i = 0; i < wordLength; i++) { // For each letter in the word
            char tmp = (char) ('a' + r.nextInt('z' - 'a')); // Generate a letter between a and z
            sb.append(tmp); // Add it to the String
        }
        return sb.toString();
    }

    public static SpannableStringBuilder formatQuote(String string) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
        int quoteStart = string.indexOf('>');
        int quoteEnd = string.indexOf('\n', quoteStart);
        if (quoteStart >= 0 && quoteEnd > 0) {
            spannable.setSpan(new StyleSpan(Typeface.BOLD), quoteStart, quoteEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }
}
