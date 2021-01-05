package para.cic.cloud.core.model;

import lombok.Data;

public class MsgSendErrorTO {
    @Data
    public class FeignFaildResult {
        private String message;
        private int status;
    }
}
