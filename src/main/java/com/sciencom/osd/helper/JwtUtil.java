package com.sciencom.osd.helper;

import com.sciencom.osd.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String secret = "asduek84&4%6jkeu&ku7j9uel0+sd[jdli!jasdi76niry";

    private String DELIMITER = ",";

    public String createToken(User user) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        String[] temp = new String[user.getRoles().size()];
        for (int i=0; i<user.getRoles().size(); i++)
            temp[i] = String.valueOf(user.getRoles().get(i).getId());
        JwtBuilder builder = Jwts.builder()
                .setId(user.getId())
                .setIssuedAt(now)
                .setSubject(implode(temp, DELIMITER))
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    private String implode(String[] data, String delimiter){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<data.length; i++){
            sb.append(data[i]);
            if (i != data.length - 1)
                sb.append(delimiter);
        }
        return sb.toString();
    }

    public boolean authenticate(String token) {
        try {
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Integer[] getRoles(String token) throws Exception {
        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token)
                .getBody();
        String roleString = claims.getSubject();
        String[] ss = roleString.split(DELIMITER);
        Integer [] nn = new Integer[ss.length];
        int i = 0;
        for(String s : ss){
            nn[i] = Integer.parseInt(s);
        }
        return nn;
    }

    public String getUserId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secret)).parseClaimsJws(token)
                    .getBody();
            return claims.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
