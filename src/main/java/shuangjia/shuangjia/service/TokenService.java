package shuangjia.shuangjia.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;
import shuangjia.shuangjia.entities.User;
import java.util.Date;

/**
*@Author 张典
*@Description token验证服务
 *
*/
@Service
public class TokenService {
    public  String  getToken(User user){
        Date start =new Date();
        //设置token有效期为1小时
        long currenTime=System.currentTimeMillis()+60*60*1000;
        Date end =new Date(currenTime);
        String token=null;
        token= JWT.create().withAudience(user.getUserName()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getPsw()));
        return token;
    }
}
