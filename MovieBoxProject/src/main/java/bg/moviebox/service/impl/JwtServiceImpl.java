package bg.moviebox.service.impl;

import bg.moviebox.model.entities.JwtProperties;
import bg.moviebox.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

    //  private final String jwtSecret;
//  private final long expiration;
    private final JwtProperties jwtProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtServiceImpl.class);

//    @PostConstruct
//    public void logEnvironmentVariables() {
//        System.out.println("JWT Secret: " + System.getenv("JWT_KEY"));
//        System.out.println("JWT Expiration: " + System.getenv("JWT_EXPIRATION"));
//    }

  @PostConstruct
  public void init() {
    LOGGER.info("JWT Secret: {}", jwtProperties.getSecret());
    LOGGER.info("JWT Expiration: {}", jwtProperties.getExpiration());
  }

    public JwtServiceImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }


//  public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret,
//                        @Value("${jwt.expiration}") long expiration) {
//    this.jwtSecret = jwtSecret;
//    this.expiration = expiration;
//    LOGGER.info("JWT Secret: {}", jwtSecret);
//    LOGGER.info("JWT Expiration: {}", expiration);
//  }

    @Override
    public String generateToken(String userId, Map<String, Object> claims) {
        var now = new Date();

        // signature = SHA256(key+text)
        //sign message = (text, signature) -> person who receives it and knows the key
        // can execute this check -> signature = SHA256(key+text)
        // the token is not crypt it's a plain text + digit signature

        return Jwts
                .builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setNotBefore(now)
                .setExpiration(new Date(now.getTime() + jwtProperties.getExpiration())) //expiration
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Claims parseClaims(String token) {
//    return Jwts
//            .parserBuilder()
//            .setSigningKey(getSigningKey())
//            .build()
//            .parseClaimsJws(token)
//            .getBody();
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.error("Failed to parse claims from token", e);
            throw e;
        }
    }

    @Override
    public boolean isTokenExpired(String token) {
        Claims claims = parseClaims(token);
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    private Key getSigningKey() {
      byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
//        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
