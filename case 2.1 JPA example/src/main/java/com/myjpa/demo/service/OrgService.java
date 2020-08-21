package com.myjpa.demo.service;

import com.myjpa.demo.entity.api.IOrgEntity;
import com.myjpa.demo.repository.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname OrgService
 * @Description TODO
 * @Date 2020/8/21 11:06
 * @Created by zengzg
 */
@Service
public class OrgService {

    @Autowired
    private OrgRepository orgRepository;


    public List<IOrgEntity> findByOrgName(String name) {
        List<IOrgEntity> orgEntity = orgRepository.findByOrgname(name);
        return orgEntity;
    }

    public List<IOrgEntity> findByOrOrgnameIsLike(String name) {
        name = "%" + name + "%";
        List<IOrgEntity> orgEntity = orgRepository.findByOrOrgnameLike(name);
        return orgEntity;
    }

}
