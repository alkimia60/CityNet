/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.Utils;

public class TextUtils {

    public static String jsonErrorMessage(String message) {
        //return "{\"Status\":{\"error\":\"" + message + "\"}}";
        return "{\"error\":\"" + message + "\"}";
    }

    public static String jsonOkMessage(String message) {
        //return "{\"Status\":{\"OK\":\"" + message + "\"}}";
        return "{\"OK\":\"" + message + "\"}";
    }
    public static String jsonTokenRolMessage(String token, String rol) {
        return "{\"token\":\"" + token +  "\",\"rol\":\"" + rol + "\"}";
    }

    public static String jsonUserDate(String user, String date) {
        return "{\"user\":\"" + user +  "\",\"date\":\"" + date + "\"}";
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
