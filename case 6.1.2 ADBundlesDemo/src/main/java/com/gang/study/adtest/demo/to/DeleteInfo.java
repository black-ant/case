package com.gang.study.adtest.demo.to;

import lombok.Data;

import java.util.List;

/**
 * @Classname DeleteInfo
 * @Description TODO
 * @Date 2019/11/14 14:14
 * @Created by zengzg
 */

@Data
public class DeleteInfo<T, TO> {

    protected List<T> deleteIds;

    protected T deleteid;

    public TO getModuleDeleteInfo() {
        return null;
    }

    public void setModuleDeleteInfo(TO moduleInfo) {
    }


}
