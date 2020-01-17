package para.cic.cloud.core.feign;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.util.Exceptions;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import para.cic.cloud.core.common.CommonException;

import java.io.IOException;

@Configuration
public class FeignException implements ErrorDecoder {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
        logger.info("feign client response:", response);
        String body = null;
        try {
            body = Util.toString(response.body().asReader());
        } catch (IOException e) {
            logger.error("feign.IOException", e);
        }
        logger.info("------> JSON :{} <-------", body);
        return new CommonException(body);
    }
}
