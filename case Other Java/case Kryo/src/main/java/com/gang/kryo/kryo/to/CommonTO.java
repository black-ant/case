package com.gang.kryo.kryo.to;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @Classname CommonTO
 * @Description TODO
 * @Date 2021/3/24
 * @Created by zengzg
 */
public class CommonTO implements Serializable {

    private String username;

    private List<CommonTO> userList;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<CommonTO> getUserList() {
        return userList;
    }

    public void setUserList(List<CommonTO> userList) {
        this.userList = userList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommonTO)) return false;
        CommonTO commonTO = (CommonTO) o;
        return Objects.equals(username, commonTO.username) &&
                Objects.equals(userList, commonTO.userList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userList);
    }
}
