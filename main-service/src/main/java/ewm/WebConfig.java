package ewm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        HandlerInterceptor userHeaderCheckInterceptor = new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                String userIdHeader = request.getHeader("X-User-Id");
                if (userIdHeader == null || userIdHeader.isEmpty()) {

                    if (response != null) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("text/plain");
                        response.getWriter().write("Отсутствует обязательный для администраторов заголовок");
                        response.getWriter().flush();
                        return false;
                    }
                }
                return true;
            }
        };
        registry.addInterceptor(userHeaderCheckInterceptor).addPathPatterns("/admin/**");
    }
}
