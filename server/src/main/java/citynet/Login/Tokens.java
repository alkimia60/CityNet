/**
 *
 * @author Francisco Javier Diaz Garzon
 */
package citynet.Login;

import citynet.Utils.TextUtils;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import io.jsonwebtoken.*;
import java.util.Date;
import java.util.HashMap;

public class Tokens {

//Sample method to construct a JWT
    //private String createJWT(String id, String issuer, String subject, long ttlMillis) {
    public String createJWT(String issuer, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        //byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(apiKey.getSecret());
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("ClauSecreta");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                //.setId(id)
                .setIssuedAt(now)
                //.setSubject(subject)
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

    public HashMap<String, String> parseJWT(String jwt) {
        HashMap tokenInfo = new HashMap();
        try {
            //This line will throw an exception if it is not a signed JWS (as expected)
            Claims claims = Jwts.parser()
                    //.setSigningKey(DatatypeConverter.parseBase64Binary(apiKey.getSecret()))
                    .setSigningKey(DatatypeConverter.parseBase64Binary("ClauSecreta"))
                    .parseClaimsJws(jwt).getBody();
            
            tokenInfo.put("user", claims.getIssuer());
            tokenInfo.put("Expiration", claims.getExpiration());

            return tokenInfo;
        } catch (Exception e) {
            System.out.println (e.getMessage());
        }
        return tokenInfo;
    }
}
