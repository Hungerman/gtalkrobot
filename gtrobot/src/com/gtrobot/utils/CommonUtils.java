package com.gtrobot.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 公共工具类
 * <p>
 * 
 * @author sunyuxin
 * 
 */
public class CommonUtils {
    private static final Map<String, Locale> supportedLocales = new Hashtable<String, Locale>();
    static {
        final Locale[] locales = Locale.getAvailableLocales();
        for (final Locale element : locales) {
            // System.out.println(locales[i].getLanguage());
            CommonUtils.supportedLocales.put(element.getLanguage()
                    .toLowerCase(), element);
        }
    }

    /**
     * 根据系统支持的所有Locale,解析Locale对象
     * 
     * @param lang
     * @return
     */
    public static Locale parseLocale(String lang) {
        lang = lang.trim().toLowerCase();
        return CommonUtils.supportedLocales.get(lang);
    }

    /**
     * 解析命令字符串。
     * <p>
     * isImmediateCommand标志是否传进来的命令串是包含了全局命令前导字符"/"或者":"的。<br>
     * 把去除前导字符之外的所有字符，分隔符拆分成字符串的列表进行返回。<br>
     * 分隔符支持：空格（半角和全角），制表符，换行和回车
     */
    public static List<String> parseCommand(final String body,
            final boolean isImmediateCommand) {
        final List<String> results = new ArrayList<String>();
        StringBuffer tempStr = new StringBuffer();
        int start = 0;
        if (isImmediateCommand) {
            start = 1;
        }
        for (int i = start; i < body.length(); i++) {
            final char cc = body.charAt(i);

            if ((cc == ' ') || (cc == '\t') || (cc == '\n') || (cc == '\r')
                    || (cc == '　')) {
                if (tempStr.length() == 0) {
                    continue;
                } else {
                    results.add(tempStr.toString());
                    tempStr = new StringBuffer();
                }
            } else {
                tempStr.append(cc);
            }
        }
        if (tempStr.length() != 0) {
            results.add(tempStr.toString());
        }
        return results;
    }

    /**
     * 解析命令字符串（简单版本）。
     * <p>
     * 把命令串根据第一个出现的分隔符，拆分成前后两个字符串进行返回。<br>
     * 分隔符支持：空格（半角和全角），制表符，换行和回车
     */
    public static List<String> parseSimpleCommand(final String body) {
        StringBuffer tempStr = new StringBuffer();
        int i = 0;
        if (body == null) {
            return null;
        }
        // parse the first command
        for (; i < body.length(); i++) {
            final char cc = body.charAt(i);

            if ((cc == ' ') || (cc == '\t') || (cc == '\n') || (cc == '\r')
                    || (cc == '　')) {
                if (tempStr.length() == 0) {
                    continue;
                } else {
                    break;
                }
            } else {
                tempStr.append(cc);
            }
        }
        // Check whether the input is an interactive command
        if (tempStr.charAt(0) != '.') {
            return null;
        }
        final List<String> results = new ArrayList<String>();
        results.add(tempStr.toString().toLowerCase());

        tempStr = new StringBuffer();
        // parse the first command
        for (; i < body.length(); i++) {
            final char cc = body.charAt(i);

            if ((cc == ' ') || (cc == '\t') || (cc == '\n') || (cc == '\r')
                    || (cc == '　')) {
                if (tempStr.length() == 0) {
                    continue;
                } else {
                    break;
                }
            } else {
                tempStr.append(cc);
            }
        }
        if (tempStr.length() > 0) {
            results.add(tempStr.toString());
        }
        return results;
    }
}
