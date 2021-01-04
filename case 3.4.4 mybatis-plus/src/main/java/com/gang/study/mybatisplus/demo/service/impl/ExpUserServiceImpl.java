package com.gang.study.mybatisplus.demo.service.impl;

import com.gang.study.mybatisplus.demo.entity.ExpUser;
import com.gang.study.mybatisplus.demo.mapper.ExpUserMapper;
import com.gang.study.mybatisplus.demo.service.IExpUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ant-black
 * @since 2020-02-01
 */
@Service
public class ExpUserServiceImpl extends ServiceImpl<ExpUserMapper, ExpUser> implements IExpUserService {

}
