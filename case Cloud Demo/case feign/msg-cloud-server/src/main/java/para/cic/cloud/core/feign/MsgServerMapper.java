package para.cic.cloud.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.alibaba.fastjson.JSONObject;

import para.cic.cloud.core.model.MsgSendTO;

/**
 * description
 *
 * @author zengzg@paraview.cn
 * @version 2019年9月3日
 * @since JDK 1.8
 */

@FeignClient(name = "application-name")
public interface MsgServerMapper {

    @PostMapping(value = "/send1", produces = "application/json;charset=UTF-8")
    JSONObject send(MsgSendTO msgRequest);

    @PostMapping(value = "/send2", produces = "application/json;charset=UTF-8")
    JSONObject send2(MsgSendTO msgRequest);

    @PostMapping(value = "/send3", produces = "application/json;charset=UTF-8")
    JSONObject send3(MsgSendTO msgSendRequest);

    @GetMapping("/send4")
    void showUser();
}
