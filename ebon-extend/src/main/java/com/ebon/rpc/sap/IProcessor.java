package com.ebon.rpc.sap;

import com.sap.mw.jco.JCO.Function;

/**
 * sap function处理器,可以设置sap中的函数名，过滤条件以及对数据的业务处理
 */ 
public interface IProcessor {
    /**
     * 设置Function的名字
     * @return
     */
    public String fname();
    /**
     * 添加过滤条件
     * @param r_function
     */
    public void doFilter(Function r_function);
    /**
     * 对得到的数据做业务逻辑处理
     * @param r_function
     * @return
     */
    public Object doBusiness(Function r_function);
}
