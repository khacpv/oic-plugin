package com.oic.plugin.multidimens.logic;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.xml.XmlDocument;

/**
 * Created by khacpham on 10/1/16.
 */
public interface LogicXmlRead {

    /**
     * get dimen of folder. (ex: values-w320dp -> 320)
     * @return 0 for missing dimen value
     */
    int getDimenOfValueFolder(String folderName);

    /**
     * load content of xml file
     * @see #getContent(XmlDocument, float)
     */
    String getContent(XmlDocument document);

    /**
     * load content of xml file with dimens changed
     * @param document input file
     * @param fract constant to multiply with dimens
     * @return content of new document
     */
    String getContent(XmlDocument document, float fract);
}
