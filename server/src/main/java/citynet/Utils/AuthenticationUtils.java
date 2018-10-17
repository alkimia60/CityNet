/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class AuthenticationUtils {

    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            return true;
        } 
        return false;
    }

}
