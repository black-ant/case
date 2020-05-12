package com.gang.study.spring.fileupload.demo.service;


import com.gang.study.spring.fileupload.demo.dao.BaseDao;
import com.gang.study.spring.fileupload.demo.dao.Query;
import com.gang.study.spring.fileupload.demo.utils.PageResults;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * BaseService作为所有Service的基类，需要使用的话，需要先编写一个继承自此类的类
 *
 * @param <T> 实体类型
 */
@SuppressWarnings({"rawtypes", "unchecked"})
class BaseService<T> {
    @Autowired
    private BaseDao<T> baseDao;
    private Class<T> modelClass;

    public BaseService() {
        modelClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * 保存对象
     *
     * @param model 需要添加的对象
     */
    public void save( final T model) throws Exception {
        baseDao.save(model);
    }

    /**
     * 批量保存对象
     *
     * @param modelList 需要增加的对象的集合
     *                  失败会抛异常
     */
    public void saveAll( final List<T> modelList) throws Exception {
        baseDao.saveAll(modelList);
    }

    /**
     * 删除对象
     *
     * @param model 需要删除的对象
     *              失败会抛异常
     */
    public void delete( final T model) throws Exception {
        baseDao.delete(model);
    }

    /**
     * 批量删除对象
     *
     * @param modelList 需要删除的对象的集合
     *                  失败会抛异常
     */
    public void deleteAll( final List<T> modelList) throws Exception {
        baseDao.deleteAll(modelList);
    }

    /**
     * 按照id删除对象
     *
     * @param id 需要删除的对象的id
     *           失败抛出异常
     */
    public void deleteById( final Serializable id) throws Exception {
        baseDao.deleteById(modelClass, id);
    }

    /**
     * 更新或保存对象
     *
     * @param model 需要更新的对象
     *              失败会抛出异常
     */
    public void saveOrUpdate( final T model) throws Exception {
        baseDao.saveOrUpdate(model);
    }

    /**
     * 批量更新或保存对象
     *
     * @param modelList 需要更新或保存的对象
     *                  失败会抛出异常
     */
    public void saveOrUpdateAll( final List<T> modelList) throws Exception {
        baseDao.saveOrUpdateAll(modelList);
    }

    /**
     * 通过主键, 查询对象
     *
     * @param id 主键(Serializable)
     * @return model
     */
    public T getById( final Serializable id) throws Exception {
        return baseDao.getById(modelClass, id);
    }

    /**
     * 获得全部
     *
     * @return List
     */
    public List<T> getAll() throws Exception {
        return baseDao.getAll(modelClass);
    }


    /**
     * 分页查询
     *
     * @param currentPageNumber 页码
     * @param pageSize          每页数量
     * @return 查询结果
     */
    public List<T> getListByPage( final Integer currentPageNumber,
                                  final Integer pageSize)
            throws Exception {
        return baseDao.getListByPage(modelClass, currentPageNumber, pageSize);
    }

    /**
     * 按条件分页
     *
     * @param currentPageNumber 页码
     * @param pageSize          每页数量
     * @param query             封装的查询条件
     * @return 查询结果
     */
    public PageResults<T> getListByPageAndQuery( Integer currentPageNumber,
                                                 Integer pageSize,
                                                 Query query)
            throws Exception {
        return baseDao.getListByPageAndQuery(currentPageNumber, pageSize, query);
    }

    /**
     * 获得数量 利用Count(*)实现
     *
     * @return 数量
     */
    public int getCount() throws Exception {
        return baseDao.getCount(modelClass);
    }

    /**
     * 获得符合对应条件的数量 利用Count(*)实现
     *
     * @param query 查询条件
     * @return 数量
     */
    public int getCountByQuery( final Query query) throws Exception {
        return baseDao.getCountByQuery(query);
    }

    /**
     * 执行Sql语句
     *
     * @param sql    sql
     * @param values 不定参数数组
     * @return 受影响的行数
     */
    public int executeSql( final String sql,  final Object... values)
            throws Exception {
        return baseDao.executeSql(sql, values);
    }

    /**
     * 通过jpql查询
     *
     * @param jpql   jpql语句
     * @param values 参数列表
     * @return 受影响的行数
     */
    public Object queryByJpql( final String jpql,  final Object... values) {
        return baseDao.queryByJpql(jpql, values);
    }

    /**
     * 获得符合对应条件的数量 利用Count(*)实现
     *
     * @param jpql jpql查询条件
     * @return 数量
     */
    public int getCountByJpql( final String jpql,  final Object... values) {
        return baseDao.getCountByJpql(jpql, values);
    }


    /**
     * 通过Jpql分页查询
     *
     * @param currentPageNumber 当前页
     * @param pageSize          每页数量
     * @param jpql              jpql语句
     * @param values            jpql参数
     * @return 查询结果
     */
    public PageResults<Object> getListByPageAndJpql( Integer currentPageNumber,
                                                     Integer pageSize,
                                                     final String jpql,
                                                     Object... values) {
        return baseDao.getListByPageAndJpql(currentPageNumber, pageSize, jpql, values);
    }

    /**
     * 执行jpql语句
     *
     * @param jpql   jpql语句
     * @param values 参数列表
     * @return 受影响的行数
     */
    public int executeJpql( final String jpql,  final Object... values) {
        return baseDao.executeJpql(jpql, values);
    }

    /**
     * refresh 刷新实体状态
     *
     * @param model 实体
     */
    public void refresh( T model) throws Exception {
        baseDao.refresh(model);
    }
}
