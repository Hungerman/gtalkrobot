package com.gtrobot.utils;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;

import com.gtrobot.engine.GTRobotContextHelper;

/**
 * 提供了通过Spring的MessageSource访问Message内容的接口。
 * 
 * @author Joey
 * 
 */
public class MessageHelper {
    protected static final transient Log log = LogFactory
            .getLog(MessageHelper.class);

    private static final ApplicationContext context = GTRobotContextHelper
            .getApplicationContext();;

    public static String getMessage(final String key) {
        return MessageHelper.getMessage(key, null, null);
    }

    public static String getMessage(final String key, final Locale locale) {
        return MessageHelper.getMessage(key, null, locale);
    }

    public static String getMessage(final String key, final Object[] args,
            final Locale locale) {
        // if (locale == null) {
        // locale = Locale.ENGLISH;
        // }
        String message = null;
        try {
            message = MessageHelper.context.getMessage(key, args, locale);
        } catch (final Exception e) {
            MessageHelper.log
                    .error("Can't find the message from the resource for: "
                            + locale + "\n***>>> " + key + "=" + key);
            message = key + "_" + locale;
        }
        return message;
    }
}
