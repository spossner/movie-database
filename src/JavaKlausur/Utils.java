package JavaKlausur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

    public static void dump(Collection c) {
        for (Object o : c) {
            System.out.println(o);
        }
    }

    public static int toInt(String s) {
        return toInt(s, 0);
    }

    public static int toInt(String s, int defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toDouble(String s) {
        return toDouble(s, 0.0);
    }

    public static double toDouble(String s, double defaultValue) {
        if (s == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static List<String> zeileZerlegen(String buffer) {
        boolean anfuehrungszeichen = false;
        StringBuilder builder = new StringBuilder();
        List<String> zeile = new ArrayList<>();
        for (int i = 0; i < buffer.length(); i++) {
            if (buffer.charAt(i) == '"') {
                if (anfuehrungszeichen) {
                    zeile.add(builder.toString().trim());
                    builder.setLength(0); // reset the builder
                    anfuehrungszeichen = false;
                } else {
                    anfuehrungszeichen = true;
                }
            } else {
                if (anfuehrungszeichen) {
                    builder.append(buffer.charAt(i));
                }
            }
        }
        return zeile;
    }

    public static String[] splitAndTrimQuotes(String buffer) {
        return splitAndTrimQuotes(buffer, ",", 0);
    }

    public static String[] splitAndTrimQuotes(String buffer, char separator) {
        return splitAndTrimQuotes(buffer, String.valueOf(separator), 0);
    }

    public static String[] splitAndTrimQuotes(String buffer, String separator, int maxSplits) {
        String[] words = buffer.split(separator, maxSplits);
        for (int i = 0; i < words.length; i++) {
            if ((words[i].startsWith("'") && words[i].endsWith("'")) || (words[i].startsWith("\"") && words[i].endsWith("\""))) {
                words[i] = words[i].substring(1,words[i].length()-1);
            }
        }
        return words;
    }

    public static double distance(double d1, double d2) {
        return Math.abs(d1-d2);
    }

    public static  double distance(double[] v1, double[] v2) {
        double d = 0;

        assert v1.length == v2.length : "die Vektoren müssen gleich lang sein";

        for (int i=0; i<v1.length; i++) {
            d += Math.pow(v1[i]-v2[i], 2.0);
        }
        return Math.sqrt(d);
    }
}
