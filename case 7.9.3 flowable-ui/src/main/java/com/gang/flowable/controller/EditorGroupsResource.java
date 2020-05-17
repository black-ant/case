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

import org.flowable.idm.api.Group;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.ui.common.model.GroupRepresentation;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 重写 {@link org.flowable.ui.modeler.rest.app.EditorGroupsResource} 类,直接查库获取用户组列表
 * @author 陈敏
 * @version $Id: EditorGroupsResource.java,v 1.1 2019-06-20 15:00 chenmin Exp $
 * Created on 2019-06-20 15:00
 * My blog： https://www.chenmin.info
 */
@RestController
@RequestMapping("/app")
public class EditorGroupsResource {

    @Autowired
    protected IdmIdentityService idmIdentityService;

    @RequestMapping(value = "/rest/editor-groups", method = RequestMethod.GET)
    public ResultListDataRepresentation getGroups(@RequestParam(required = false, value = "filter") String filter) {
        if(!StringUtils.isEmpty(filter)){
            filter = filter.trim();
            String sql = "select * from act_id_group where NAME_ like #{name} limit 10";
            filter = "%"+filter+"%";
            List<Group> groups = idmIdentityService.createNativeGroupQuery().sql(sql).parameter("name",filter).list();
            List<GroupRepresentation> result = new ArrayList<>();
            for (Group group : groups) {
                result.add(new GroupRepresentation(group));
            }
            return new ResultListDataRepresentation(result);
        }
        return null;
    }
}
