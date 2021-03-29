package com.gang.kryo.kryo.service;

/**
 * @Classname ObjectSerializer
 * @Description TODO
 * @Date 2021/3/24
 * @Created by zengzg
 */
public interface ObjectSerializer<T> {

    /**
     * Serialize the given object to binary data.
     *
     * @param t object to serialize
     * @return the equivalent binary data
     */
    byte[] serialize(T t);

    /**
     * Deserialize an object from the given binary data.
     *
     * @param bytes object binary representation
     * @return the equivalent object instance
     */
    T deserialize(byte[] bytes);


    T clone(T object);
}
