package com.oic.plugin.multidimens.logic;

import com.oic.plugin.multidimens.logic.impl.LogicXmlReadImpl;
import com.oic.plugin.multidimens.logic.impl.LogicXmlWriteImpl;

/**
 * Created by khacpham on 10/1/16.
 */
public class LogicFactory {

    private static LogicXmlRead xmlRead;
    private static LogicXmlWrite xmlWrite;

    private LogicFactory(){

    }

    /**
     * for read content, extract info from xml file
     */
    public static LogicXmlRead getLogicXmlRead() {
        if(xmlRead == null){
            xmlRead = new LogicXmlReadImpl();
        }
        return xmlRead;
    }

    /**
     * for write, save content to xml file
     */
    public static LogicXmlWrite getLogicXmlWrite(){
        if(xmlWrite == null){
            xmlWrite = new LogicXmlWriteImpl();
        }
        return xmlWrite;
    }
}
