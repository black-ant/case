package para.cic.cloud.core.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Configuration
public class FeignRequest implements RequestInterceptor {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String token = "1";     // ADD YOU TOKEN
    private String value1 = "2";    // ADD YOU OTHER 

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);
                template.header(name, values);

            }
            logger.info("feign interceptor header:{}", template);
        }

        // TODO : ADD DOMAIN-ID
        if (StringUtils.isEmpty(request.getHeader("VALUE"))) {
            template.header("VALUE", value1);
        }
    }
}
