package com.thenorthw.onesflow.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thenorthw.onesflow.common.constants.OnesflowConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by theNorthW on $date.
 * blog: thenorthw.com
 *
 * @autuor : theNorthW
 */
public class JwtUtil {
    private static final String secret = "iLon,asd193;1duvh9v'8i2brhbdhv9h9'2ni.;'wv98',ba]u29u3rbbcsahidhwq";
    private static final String secret1 = "ajisna0'ajcpasv]anohjuegf08q9h[fin'cj[PDikd[PJOD;Oh";
    private static final Map<String,Object> headerMap = new HashMap<String, Object>();
    private static JWTVerifier jwtV = null;
    private static JWTVerifier jwtVActivate = null;

    static {
        try {
            jwtV = JWT.require(Algorithm.HMAC256(secret)).build();
            jwtVActivate = JWT.require(Algorithm.HMAC256(secret1)).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    static {
        headerMap.put("alg","HS256");
        headerMap.put("typ","JWT");
    }

    public static String createToken(String uid,Boolean remermber){
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR,remermber ? 168 : 2);
        if(remermber) {
            //凌晨失效
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 4, 0);
        }
        Date expire = calendar.getTime();

        try {
            String token = JWT.create()
                    .withHeader(headerMap)
                    .withIssuedAt(now)
                    .withExpiresAt(expire) // 设置成在凌晨4点过期
                    .withClaim(OnesflowConstant.USER_ID_IN_JWT,uid)
                    .withClaim("uid",ShortUUIDUtil.randomUUID())
                    .sign(Algorithm.HMAC256(secret));
            return token;
        }catch (Exception e){
            logger.error("Create Jwt X-token error!\n Exception: {}",e.toString());
        }

        return null;
    }

    public static String createActivateToken(String uid,int expireTime){
        Date now = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,expireTime);
        Date expire = calendar.getTime();

        try {
            String token = JWT.create()
                    .withHeader(headerMap)
                    .withIssuedAt(now)
                    .withExpiresAt(expire)
                    .withClaim(OnesflowConstant.USER_ID_IN_JWT,uid)
                    .withClaim("uid",ShortUUIDUtil.randomUUID())
                    .sign(Algorithm.HMAC256(secret1));
            return token;
        }catch (Exception e){
            logger.error("Create Jwt Activate Token error!\n Exception: {}",e.toString());
        }

        return null;
    }

    public static Map<String,Claim> verify(String token){
        if(token == null){
            return null;
        }
        DecodedJWT dj = null;
        try {
             dj = jwtV.verify(token);
        }catch (Exception e){
            return null;
        }

        return dj.getClaims();
    }

    public static Map<String,Claim> verifyActivate(String token){
        if(token == null){
            return null;
        }
        DecodedJWT dj = null;
        try {
            dj = jwtVActivate.verify(token);
        }catch (Exception e){
            return null;
        }

        return dj.getClaims();
    }

    public static Long getUidFromClaims(Map<String,Claim> cs){
        if(cs.get(OnesflowConstant.USER_ID_IN_JWT) != null){
            return Long.parseLong(cs.get(OnesflowConstant.USER_ID_IN_JWT).asString());
        }

        return null;

    }

}
