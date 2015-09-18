/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.drprog.happystory.utils;

import android.util.Log;

import com.drprog.happystory.BuildConfig;
import com.drprog.happystory.Config;


public class LogHelper {
    private static final String LOG_PREFIX = "hs_";
    private static final String EMPTY_MESSAGE = "Empty message";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 22;

    /**
     * Use as "private static final String TAG = makeLogTag("GCMs");"
     * @param str
     * @return
     */
    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    private static String getMessage(final String _msg) {
        if (_msg == null)
            return EMPTY_MESSAGE;
        return _msg;
    }

    /**
     * Don't use this when obfuscating class names!
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }

    public static void LOGD(final String _tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        String tag = makeLogTag(_tag);
        if (BuildConfig.DEBUG || Config.IS_DOGFOOD_BUILD || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(tag, getMessage(message));
        }
    }

    public static void LOGD(final String _tag, String message, Throwable cause) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        String tag = makeLogTag(_tag);
        if (BuildConfig.DEBUG || Config.IS_DOGFOOD_BUILD || Log.isLoggable(tag, Log.DEBUG)) {
            Log.d(makeLogTag(tag), getMessage(message), cause);
        }
    }

    public static void LOGV(final String _tag, String message) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        String tag = makeLogTag(_tag);
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(tag, getMessage(message));
        }
    }

    public static void LOGV(final String _tag, String message, Throwable cause) {
        //noinspection PointlessBooleanExpression,ConstantConditions
        String tag = makeLogTag(_tag);
        if (BuildConfig.DEBUG && Log.isLoggable(tag, Log.VERBOSE)) {
            Log.v(makeLogTag(tag), getMessage(message), cause);
        }
    }

    public static void LOGI(final String tag, String message) {
        Log.i(makeLogTag(tag), getMessage(message));
    }

    public static void LOGI(final String tag, String message, Throwable cause) {
        Log.i(makeLogTag(tag), getMessage(message), cause);
    }

    public static void LOGW(final String tag, String message) {
        Log.w(tag, getMessage(message));
    }

    public static void LOGW(final String tag, String message, Throwable cause) {
        Log.w(makeLogTag(tag), getMessage(message), cause);
    }

    public static void LOGE(final String tag, String message) {
        Log.e(makeLogTag(tag), getMessage(message));
    }

    public static void LOGE(final String tag, String message, Throwable cause) {
        Log.e(makeLogTag(tag), getMessage(message), cause);
    }

    private LogHelper() {
    }
}
