/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.Utils;

public class StringUtils {

    public static String jsonErrorMessage(String message) {
        return "{\"Status\":{\"error\":\"" + message + "\"}}";
    }

    public static String jsonOkMessage(String message) {
        return "{\"Status\":{\"OK\":\"" + message + "\"}}";
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
