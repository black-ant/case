package com.tcc.demo.capital.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tcc.demo.capital.model.CapitalAccount;

/**
 * Created by changming.xie on 4/2/16.
 */
@Mapper
public interface CapitalAccountDao {

    CapitalAccount findByUserId(long userId);

    int update(CapitalAccount capitalAccount);
}
