package com.serven.zsb.toolkit;


import com.serven.zsb.toolkit.lang.StringFormatter;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhangjiayuan
 * Date: 2019/1/4
 */
public class StringUtils {

    public static final char C_SPACE = ' ';
    public static final char C_TAB = '\t';
    public static final char C_DOT = '.';
    public static final char C_SLASH = '/';
    public static final char C_BACKSLASH = '\\';
    public static final char C_CR = '\r';
    public static final char C_LF = '\n';
    public static final char C_UNDERLINE = '_';
    public static final char C_COMMA = ',';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';
    public static final char C_COLON = ':';
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String DOT = ".";
    public static final String DOUBLE_DOT = "..";
    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    public static final String EMPTY = "";
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = "\r\n";
    public static final String UNDERLINE = "_";
    public static final String COMMA = ",";
    public static final String DELIM_START = "{";
    public static final String DELIM_END = "}";
    public static final String COLON = ":";
    public static final String HTML_NBSP = "&nbsp;";
    public static final String HTML_AMP = "&amp";
    public static final String HTML_QUOTE = "&quot;";
    public static final String HTML_LT = "&lt;";
    public static final String HTML_GT = "&gt;";
    public static final String EMPTY_JSON = "{}";

    public StringUtils() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(String... strs) {
        String[] var1 = strs;
        int var2 = strs.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            if (isEmpty(s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotEmpty(String... strs) {
        return !isEmpty(strs);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isBlank(String... strings) {
        String[] var1 = strings;
        int var2 = strings.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            if (isBlank(s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isNotBlank(String... strings) {
        return !isBlank(strings);
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static String format(String template, Object... params) {
        return !ArrayUtils.isEmpty(params) && !isBlank(template) ? StringFormatter.format(template, params) : template;
    }

    public static String indexedFormat(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    public static String format(String template, Map<?, ?> map) {
        if (null != map && !map.isEmpty()) {
            Map.Entry entry;
            for(Iterator var2 = map.entrySet().iterator(); var2.hasNext(); template = template.replace("{" + entry.getKey() + "}", utf8Str(entry.getValue()))) {
                entry = (Map.Entry)var2.next();
            }

            return template;
        } else {
            return template;
        }
    }

    public static byte[] utf8Bytes(String str) {
        return bytes(str, CharsetUtils.CHARSET_UTF_8);
    }

    public static byte[] bytes(String str) {
        return bytes(str, Charset.defaultCharset());
    }

    public static byte[] bytes(String str, String charset) {
        return bytes(str, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    public static byte[] bytes(String str, Charset charset) {
        if (str == null) {
            return null;
        } else {
            return null == charset ? str.getBytes() : str.getBytes(charset);
        }
    }

    public static String utf8Str(Object obj) {
        return str(obj, CharsetUtils.CHARSET_UTF_8);
    }

    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }

    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        } else if (obj instanceof String) {
            return (String)obj;
        } else if (obj instanceof byte[]) {
            return str((byte[])((byte[])obj), charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[])((Byte[])obj), charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer)obj, charset);
        } else {
            return ArrayUtils.isArray(obj) ? ArrayUtils.toString(obj) : obj.toString();
        }
    }

    public static String str(byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    public static String str(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        } else {
            return null == charset ? new String(data) : new String(data, charset);
        }
    }

    public static String str(Byte[] bytes, String charset) {
        return str(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    public static String str(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        } else {
            byte[] bytes = new byte[data.length];

            for(int i = 0; i < data.length; ++i) {
                Byte dataByte = data[i];
                bytes[i] = null == dataByte ? -1 : dataByte;
            }

            return str(bytes, charset);
        }
    }

    public static String str(ByteBuffer data, String charset) {
        return data == null ? null : str(data, Charset.forName(charset));
    }

    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }

        return charset.decode(data).toString();
    }

    public static ByteBuffer byteBuffer(String str, String charset) {
        return ByteBuffer.wrap(bytes(str, charset));
    }

    public static String join(String conjunction, Object... objs) {
        return ArrayUtils.join(objs, conjunction);
    }

    public static String toUnderlineCase(String camelCaseStr) {
        if (camelCaseStr == null) {
            return null;
        } else {
            int length = camelCaseStr.length();
            StringBuilder sb = new StringBuilder();
            boolean isPreUpperCase = false;

            for(int i = 0; i < length; ++i) {
                char c = camelCaseStr.charAt(i);
                boolean isNextUpperCase = true;
                if (i < length - 1) {
                    isNextUpperCase = Character.isUpperCase(camelCaseStr.charAt(i + 1));
                }

                if (!Character.isUpperCase(c)) {
                    isPreUpperCase = false;
                } else {
                    if ((!isPreUpperCase || !isNextUpperCase) && i > 0) {
                        sb.append("_");
                    }

                    isPreUpperCase = true;
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static String toCamelCase(String name) {
        if (name == null) {
            return null;
        } else if (name.contains("_")) {
            name = name.toLowerCase();
            StringBuilder sb = new StringBuilder(name.length());
            boolean upperCase = false;

            for(int i = 0; i < name.length(); ++i) {
                char c = name.charAt(i);
                if (c == '_') {
                    upperCase = true;
                } else if (upperCase) {
                    sb.append(Character.toUpperCase(c));
                    upperCase = false;
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return name;
        }
    }

    public static String wrap(String str, String prefix, String suffix) {
        return format("{}{}{}", prefix, str, suffix);
    }

    public static boolean isWrap(String str, String prefix, String suffix) {
        return str.startsWith(prefix) && str.endsWith(suffix);
    }

    public static boolean isWrap(String str, String wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    public static boolean isWrap(String str, char wrapper) {
        return isWrap(str, wrapper, wrapper);
    }

    public static boolean isWrap(String str, char prefixChar, char suffixChar) {
        return str.charAt(0) == prefixChar && str.charAt(str.length() - 1) == suffixChar;
    }

    public static String padPre(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(minLength);

            for(int i = str.length(); i < minLength; ++i) {
                sb.append(padChar);
            }

            sb.append(str);
            return sb.toString();
        }
    }

    public static String padEnd(String str, int minLength, char padChar) {
        if (str.length() >= minLength) {
            return str;
        } else {
            StringBuilder sb = new StringBuilder(minLength);
            sb.append(str);

            for(int i = str.length(); i < minLength; ++i) {
                sb.append(padChar);
            }

            return sb.toString();
        }
    }

    public static StringBuilder builder() {
        return new StringBuilder();
    }

    public static String sub(String string, int fromIndex, int toIndex) {
        int len = string.length();
        if (fromIndex < 0) {
            fromIndex += len;
            if (fromIndex < 0) {
                fromIndex = 0;
            }
        } else if (fromIndex > len) {
            fromIndex = len;
        }

        if (toIndex < 0) {
            toIndex += len;
            if (toIndex < 0) {
                toIndex = len;
            }
        } else if (toIndex > len) {
            toIndex = len;
        }

        if (toIndex < fromIndex) {
            int tmp = fromIndex;
            fromIndex = toIndex;
            toIndex = tmp;
        }

        return fromIndex == toIndex ? "" : string.substring(fromIndex, toIndex);
    }
}
