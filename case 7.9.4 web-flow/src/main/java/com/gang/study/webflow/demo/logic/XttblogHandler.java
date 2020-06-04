package com.gang.study.webflow.demo.logic;

import com.gang.study.webflow.demo.entity.ServerResponse;
import org.omg.CORBA.ServerRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * @Classname XttblogHandler
 * @Description TODO
 * @Date 2020/6/4 16:36
 * @Created by zengzg
 */
@Component
public class XttblogHandler {
    public Mono<ServerResponse> helloXttblog(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello, www.xttblog.com !"));

    }
}
