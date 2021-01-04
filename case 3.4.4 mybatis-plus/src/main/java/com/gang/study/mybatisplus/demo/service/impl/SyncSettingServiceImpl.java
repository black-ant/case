package com.gang.study.mybatisplus.demo.service.impl;

import com.gang.study.mybatisplus.demo.entity.SyncSetting;
import com.gang.study.mybatisplus.demo.mapper.SyncSettingMapper;
import com.gang.study.mybatisplus.demo.service.ISyncSettingService;
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
public class SyncSettingServiceImpl extends ServiceImpl<SyncSettingMapper, SyncSetting> implements ISyncSettingService {

}
