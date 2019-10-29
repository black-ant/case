package para.cic.cloud.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({CommonException.class})
    public String handleException(CommonException e) {
        e.printStackTrace();
        logger.info("------> this is in handle CommonException <-------");
//        return Response.status(Response.Status.ACCEPTED).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE.withCharset("UTF-8")).build();
        return e.getMessage();
    }

    @ExceptionHandler({Exception.class})
    public String handleException(Exception e) {
        e.printStackTrace();
        logger.info("------> this is in handle Exception :{}<-------", e.getMessage());
//        return Response.status(Response.Status.ACCEPTED).entity(e.getMessage()).type(MediaType.APPLICATION_JSON_TYPE.withCharset("UTF-8")).build();
        return e.getMessage();
    }
}
