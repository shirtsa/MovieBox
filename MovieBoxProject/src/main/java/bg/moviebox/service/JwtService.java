package bg.moviebox.service;

import io.jsonwebtoken.Claims;

import java.util.Map;

public interface JwtService {
  String generateToken(String userId, Map<String, Object> claims);

  Claims parseClaims(String token); ///

  boolean isTokenExpired(String token);////
}
