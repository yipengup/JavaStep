package com.yipeng.ioc.java.beans;

import java.beans.PropertyEditorSupport;

/**
 * @author yipeng
 */
public class String2IntegerPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws java.lang.IllegalArgumentException {
        // 对于配置文件中传过来的字符串直接将其转化成Integer
        setValue(Integer.valueOf(text));
    }

}
