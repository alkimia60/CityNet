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

    public void checkPass(String plainPassword, String hashedPassword) {
        if (BCrypt.checkpw(plainPassword, hashedPassword)) {
            System.out.println("The password matches.");
        } else {
            System.out.println("The password does not match.");
        }
    }

}
