package com.oic.plugin.multidimens.logic.impl;

import com.intellij.psi.PsiElement;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlTag;
import com.oic.plugin.multidimens.common.StringUtils;
import com.oic.plugin.multidimens.logic.LogicXmlRead;
import org.junit.Assert;

/**
 * Created by khacpham on 10/1/16.
 */
public class LogicXmlReadImpl implements LogicXmlRead {

    //================== TAG =============================

    private static final String TAG_DIMEN = "dimen";
    private static final String TAG_COLOR = "color";
    private static final String TAG_STRING = "string";
    private static final String TAG_ARRAY = "array";

    //================== ATTRIBUTE =============================

    private static final String ATT_NAME = "name";

    //================== VALUES =============================

    private static final String VAL_DP = "dp";
    private static final String VAL_DIP = "dip";
    private static final String VAL_SP = "sp";
    private static final String VAL_PX = "px";

    private static final String VAL_VALUES = "values";

    //================== LOCAL =============================


    @Override
    public int getDimenOfValueFolder(String folderName) {
        if (folderName.equalsIgnoreCase(VAL_VALUES)) {
            return 0;
        }
        if (folderName.contains(VAL_VALUES)) {
            String dimen = StringUtils.extractNumber(folderName);
            try {
                return Integer.parseInt(dimen);
            } catch (NumberFormatException e) {
                System.out.println("getDimenOfValueFolder: dimen not found: " + folderName);
            }
        }
        return 0;
    }

    @Override
    public String getContent(XmlDocument document) {
        return getContent(document, 1f);
    }

    @Override
    public String getContent(XmlDocument document, float fract) {
        StringBuilder content = new StringBuilder("");

        XmlTag nodeResource = document.getRootTag();
        Assert.assertNotNull(nodeResource);

        PsiElement[] nodeDimens = nodeResource.getChildren();
        for (PsiElement node : nodeDimens) {
            if (node instanceof XmlTag) {
                XmlTag tag = (XmlTag) node;

                switch (tag.getName()) {
                    case TAG_DIMEN:
                        String tagValue = tag.getValue().getTrimmedText();

                        if (tagValue.endsWith(VAL_DIP) || tagValue.endsWith(VAL_DP)) {
                            String dimen = tagValue.replaceAll(VAL_DIP, "").replaceAll(VAL_DP, "");
                            String tagUnit = tagValue.replaceAll(dimen, "");
                            try {
                                float dimenF = Float.valueOf(dimen) * fract;
                                String dimenFStr = String.format("%.02f", dimenF).replace(".00","");

                                int startIndex = tag.getText().lastIndexOf(dimen);
                                int endIndex = startIndex + (dimen +tagUnit).length();
                                StringBuilder contentBuilder = new StringBuilder(tag.getText());
                                contentBuilder.replace(startIndex, endIndex, dimenFStr+tagUnit);

                                content.append(contentBuilder.toString());
                            } catch (NumberFormatException e) {
                                System.out.println("extract dimen failed: " + tagValue);
                            }
                        } else {
                            content.append(tag.getText());
                        }
                        break;
                    default:
                        content.append(tag.getText());
                        break;
                }
            } else {
                content.append(node.getText());
            }
        }

        return content.toString();
    }
}
