package shuangjia.shuangjia.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import shuangjia.shuangjia.Constant.Constant;
import shuangjia.shuangjia.dao.UserDao;
import shuangjia.shuangjia.entities.User;
import shuangjia.shuangjia.service.UserService;
import shuangjia.shuangjia.util.LoginRequired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
*@Author 张典
*@Description token拦截,并验证token
 *
*/
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService ;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取token
        String token=httpServletRequest.getHeader("token");
        if(!(o instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod =(HandlerMethod) o;
        Method method=handlerMethod.getMethod();
        //判断是否拦截接口进行登陆验证
        LoginRequired loginRequired =method.getAnnotation(LoginRequired.class);
        if(loginRequired != null){
            if(token==null){
                throw new RuntimeException(Constant.TONKEN_NOT_EMPTY);
            }
            String userName=null;
            try {
            userName= JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException e) {
                throw new RuntimeException(Constant.TONKEN_INVALID);
            }
            User user=userService.getUserInfoByUserName(userName);
            if(user==null){
                throw new RuntimeException(Constant.TONKEN_NOUSER);
            }
            //验证token
                JWTVerifier verifier;
                verifier = JWT.require(Algorithm.HMAC256(user.getPsw())).build();
                try {
                    verifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException(Constant.TONKEN_INVALID);
                }
            httpServletRequest.setAttribute("currentUser", user);
            return true;
         }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
