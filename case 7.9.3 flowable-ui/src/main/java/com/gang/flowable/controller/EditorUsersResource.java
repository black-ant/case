/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gang.flowable.controller;

import org.flowable.engine.ManagementService;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.idm.api.User;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 重写 {@link org.flowable.ui.modeler.rest.app.EditorUsersResource} 类,直接查库获取用户列表
 * @author 陈敏
 * @version $Id: EditorUsersResource.java,v 1.1 2019-06-20 15:01 chenmin Exp $
 * Created on 2019-06-20 15:01
 * My blog： https://www.chenmin.info
 */
@RestController
@RequestMapping("/app")
public class EditorUsersResource {

    @Autowired
    protected IdmIdentityService idmIdentityService;
    @Autowired
    protected ManagementService managementService;

    @RequestMapping(value = "/rest/editor-users", method = RequestMethod.GET)
    public ResultListDataRepresentation getUsers(@RequestParam(value = "filter", required = false) String filter) {
        if (!StringUtils.isEmpty(filter)) {
            filter = filter.trim();
            String sql = "select * from act_id_user where ID_ like #{id} or LAST_ like #{name} limit 10";
            filter = "%"+filter+"%";
            List<User> matchingUsers = idmIdentityService.createNativeUserQuery().sql(sql).parameter("id",filter).parameter("name",filter).list();
            List<UserRepresentation> userRepresentations = new ArrayList<>(matchingUsers.size());
            for (User user : matchingUsers) {
                userRepresentations.add(new UserRepresentation(user));
            }
            return new ResultListDataRepresentation(userRepresentations);
        }
       return null;
    }

}
