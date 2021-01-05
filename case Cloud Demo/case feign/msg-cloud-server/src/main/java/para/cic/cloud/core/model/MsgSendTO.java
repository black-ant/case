package para.cic.cloud.core.model;

import lombok.Data;

/**
* description  
* @author zengzg@paraview.cn
* @version 2019年9月3日
* @since JDK 1.8
*/
@Data
public class MsgSendTO {

    private String title;

    private String content;

    private String receiver;
}
