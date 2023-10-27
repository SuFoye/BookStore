package com.miaomiao.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class WebUtils {

    /**
     * 把Map中的值（请求参数）注入到对应的JavaBean属性中，参数名和属性名对应
     * 底层按照反射机制，依据JavaBean中set属性名方法对应注入
     * @param value
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }
}