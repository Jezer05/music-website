package top.jezer.config;


import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.jezer.utils.TokenUtils;


@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("x-access-token");
        if (token!=null){
            boolean result= TokenUtils.verify(token);
            if (result){
                System.out.println("通过拦截器");
                return true;
            }
        }
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json=new JSONObject();
            json.put("message", "登录状态失效，请重新登录");
            json.put("code", 6000);
            json.put("isSuccess", false);
            json.put("type", "error");
            System.out.println("请求被拦截");
            response.getWriter().append(json.toString());
        } catch (Exception e) {
            return false;
        }
        /*
          还可以在此处检验用户存不存在等操作
         */
        return false;
    }
}