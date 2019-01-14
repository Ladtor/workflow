package com.ladtor.workflow.converter;

/**
 * @author liudongrong
 * @date 2019/1/14 22:26
 */
public interface Converter<T1, T2> {
    T2 convertTo(T1 obj);
}
