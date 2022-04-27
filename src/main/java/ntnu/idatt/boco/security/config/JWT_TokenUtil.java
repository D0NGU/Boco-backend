package ntnu.idatt.boco.security.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ntnu.idatt.boco.security.SecurityConstants;

@Component
public class JWT_TokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Method to get email from token
     * @param token
     * @return the users email
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieve expiration date from JWT token
     * @param token
     * @return token expiration date down to ms
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Method to get a single claim from token
     * @param <T>
     * @param token
     * @param claimsResolver 
     * @return 
     * @see Claims
     * @see #getAllClaimsFromToken(String)
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Method to get all claims from token
     * @param token
     * @return all JWT Claims for given token
     * @see Claims
     * @see #getClaimFromToken(String, Function)
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Method to check if token has expired
     * @param token
     * @return {@code true} if token has expired, {@code false} if not
     * @see #getExpirationDateFromToken(String)
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate new token for user
     * @param userDetails 
     * @return token as URL-safe String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Generates new token
     * @param claims
     * @param subject
     * @return token as URL-safe String
     * @see #generateToken(UserDetails)
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        // While creating the token:
        // 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
        // 2. Sign the JWT using the HS512 algorithm and secret key.
        // 3. According to JWS Compact Serialization compaction of the JWT to a URL-safe string
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * Method to validate token
     * @param token
     * @param userDetails
     * @return {@code true} if token is valid, {@code false} if not 
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}