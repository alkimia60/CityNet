/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.utils;

import citynet.dao.UserDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * Authenticacion Utils
 */
public class AuthUtils {

    public static final long TOKEN_EXP_TIME = 900000; //Token expiration time in millisecons
    private static final String SECRET_KEY = "sF|;^b?L\"J1c"; //Key to token encrypt

    /**
     * Hash a String password
     * @param plainTextPassword String of password
     * @return hash of plainTextPassword
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * Checks two passwords
     * @param plainPassword
     * @param hashedPassword
     * @return true if match hashed passwords
     */
    public static boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    /**
     * Creates a JSON Web Token 
     * @param issuer String logged user 
     * @param ttlMillis long Token expiration time in millisecons
     * @return JWT
     */
    public static String createJWT(String issuer, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    /**
     * Extract user or issuer from JWT
     * @param jwt
     * @return issuer or user of jwt
     */

    public static String JWTTokenUser(String jwt) {
        String tokenUser = "";
        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                    .parseClaimsJws(jwt).getBody();

            tokenUser = claims.getIssuer();
            return tokenUser;
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return tokenUser;
    }


    /**
     * Checks if token is valit and user is in DB
     * @param token jwt token
     * @return true
     */

    public static boolean isValidToken(String token) {
        //find user issuer
        String tokenUser;
        tokenUser = JWTTokenUser(token);
        if (tokenUser.isEmpty()) {
            return false;
        } else {
            //if user is in DB ->true
            return new UserDao().isUserInDB(tokenUser);
        }
    }

}
