package top.jezer.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import top.jezer.controller.dto.LoginDTO;
import top.jezer.domain.Admin;
import top.jezer.exception.SystemException;

import java.util.Date;

public class TokenUtils {

    //token到期时间7天
    private static final long EXPIRE_TIME= 1000 * 60 * 60 * 168;
    //密钥盐
    private static final String TOKEN_SECRET="jezer_music";

    /**
     * 生成token
     * @param loginDTO
     * @return token
     */
    public static String sign(LoginDTO loginDTO){
        String token;
        try {
            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("auth0")
                    //存放数据
                    .withClaim("username",loginDTO.getUsername())
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException je) {
            throw new SystemException();
        }
        return token;
    }


    /**
     * token验证
     * @param token
     * @return
     */
    public static Boolean verify(String token){

        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            //System.out.println("认证通过：");
            //System.out.println("username: " + decodedJWT.getClaim("username").asString());
            //System.out.println("过期时间：      " + decodedJWT.getExpiresAt());
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }

}