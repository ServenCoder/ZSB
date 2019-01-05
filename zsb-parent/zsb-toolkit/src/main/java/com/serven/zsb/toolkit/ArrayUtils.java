package com.serven.zsb.toolkit;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by zhangjiayuan
 * Date: 2019/1/4
 */
public class ArrayUtils {
    public ArrayUtils() {
    }

    public static Map toMap(Object[] array) {
        if (array == null) {
            return null;
        } else {
            HashMap map = new HashMap((int)((double)array.length * 1.5D));

            for(int i = 0; i < array.length; ++i) {
                Object object = array[i];
                if (object instanceof Map.Entry) {
                    Map.Entry entry = (Map.Entry)object;
                    map.put(entry.getKey(), entry.getValue());
                } else {
                    if (!(object instanceof Object[])) {
                        throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
                    }

                    Object[] var5 = (Object[])((Object[])object);
                    if (var5.length < 2) {
                        throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                    }

                    map.put(var5[0], var5[1]);
                }
            }

            return map;
        }
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(long[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(int[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(short[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(char[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(byte[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(double[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(float[] array) {
        return array != null && array.length != 0;
    }

    public static boolean isNotEmpty(boolean[] array) {
        return array != null && array.length != 0;
    }

    public static <T> T[] newArray(Class<?> componentType, int newSize) {
        return (Object[])((Object[]) Array.newInstance(componentType, newSize));
    }

    @SafeVarargs
    public static <T> T[] append(T[] buffer, T... newElements) {
        if (isEmpty(newElements)) {
            return buffer;
        } else {
            T[] t = resize(buffer, buffer.length + newElements.length);
            System.arraycopy(newElements, 0, t, buffer.length, newElements.length);
            return t;
        }
    }

    public static <T> T[] resize(T[] buffer, int newSize, Class<?> componentType) {
        T[] newArray = newArray(componentType, newSize);
        if (isNotEmpty(buffer)) {
            System.arraycopy(buffer, 0, newArray, 0, Math.min(buffer.length, newSize));
        }

        return newArray;
    }

    public static <T> T[] resize(T[] buffer, int newSize) {
        return resize(buffer, newSize, buffer.getClass().getComponentType());
    }

    @SafeVarargs
    public static <T> T[] addAll(T[]... arrays) {
        if (arrays.length == 1) {
            return arrays[0];
        } else {
            int length = 0;
            Object[][] var2 = arrays;
            int var3 = arrays.length;

            int var4;
            for(var4 = 0; var4 < var3; ++var4) {
                T[] array = var2[var4];
                if (array != null) {
                    length += array.length;
                }
            }

            T[] result = newArray(arrays.getClass().getComponentType().getComponentType(), length);
            length = 0;
            Object[][] var8 = arrays;
            var4 = arrays.length;

            for(int var9 = 0; var9 < var4; ++var9) {
                T[] array = var8[var9];
                if (array != null) {
                    System.arraycopy(array, 0, result, length, array.length);
                    length += array.length;
                }
            }

            return result;
        }
    }

    public static <T> T[] clone(T[] array) {
        return array == null ? null : (Object[])array.clone();
    }

    public static int[] range(int excludedEnd) {
        return range(0, excludedEnd, 1);
    }

    public static int[] range(int includedStart, int excludedEnd) {
        return range(includedStart, excludedEnd, 1);
    }

    public static int[] range(int includedStart, int excludedEnd, int step) {
        int deviation;
        if (includedStart > excludedEnd) {
            deviation = includedStart;
            includedStart = excludedEnd;
            excludedEnd = deviation;
        }

        if (step <= 0) {
            step = 1;
        }

        deviation = excludedEnd - includedStart;
        int length = deviation / step;
        if (deviation % step != 0) {
            ++length;
        }

        int[] range = new int[length];

        for(int i = 0; i < length; ++i) {
            range[i] = includedStart;
            includedStart += step;
        }

        return range;
    }

    public static byte[][] split(byte[] array, int len) {
        int x = array.length / len;
        int y = array.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }

        byte[][] arrays = new byte[x + z][];

        for(int i = 0; i < x + z; ++i) {
            byte[] arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(array, i * len, arr, 0, y);
            } else {
                System.arraycopy(array, i * len, arr, 0, len);
            }

            arrays[i] = arr;
        }

        return arrays;
    }

    public static <T> T[] filter(T[] array, Editor<T> editor) {
        ArrayList<T> list = new ArrayList();
        Object[] var4 = array;
        int var5 = array.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            T t = var4[var6];
            T modified = editor.edit(t);
            if (null != modified) {
                list.add(t);
            }
        }

        return list.toArray(Arrays.copyOf(array, list.size()));
    }

    public static <T, K> Map<T, K> zip(T[] keys, K[] values) {
        if (!isEmpty(keys) && !isEmpty(values)) {
            int size = Math.min(keys.length, values.length);
            Map<T, K> map = new HashMap((int)((double)size / 0.75D));

            for(int i = 0; i < size; ++i) {
                map.put(keys[i], values[i]);
            }

            return map;
        } else {
            return null;
        }
    }

    public static <T> boolean contains(T[] array, T value) {
        Class<?> componetType = array.getClass().getComponentType();
        boolean isPrimitive = false;
        if (null != componetType) {
            isPrimitive = componetType.isPrimitive();
        }

        Object[] var4 = array;
        int var5 = array.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            T t = var4[var6];
            if (t == value) {
                return true;
            }

            if (!isPrimitive && null != value && value.equals(t)) {
                return true;
            }
        }

        return false;
    }

    public static Integer[] wrap(int... values) {
        int length = values.length;
        Integer[] array = new Integer[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static int[] unWrap(Integer... values) {
        int length = values.length;
        int[] array = new int[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Long[] wrap(long... values) {
        int length = values.length;
        Long[] array = new Long[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static long[] unWrap(Long... values) {
        int length = values.length;
        long[] array = new long[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Character[] wrap(char... values) {
        int length = values.length;
        Character[] array = new Character[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static char[] unWrap(Character... values) {
        int length = values.length;
        char[] array = new char[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Byte[] wrap(byte... values) {
        int length = values.length;
        Byte[] array = new Byte[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static byte[] unWrap(Byte... values) {
        int length = values.length;
        byte[] array = new byte[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Short[] wrap(short... values) {
        int length = values.length;
        Short[] array = new Short[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static short[] unWrap(Short... values) {
        int length = values.length;
        short[] array = new short[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Float[] wrap(float... values) {
        int length = values.length;
        Float[] array = new Float[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static float[] unWrap(Float... values) {
        int length = values.length;
        float[] array = new float[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Double[] wrap(double... values) {
        int length = values.length;
        Double[] array = new Double[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static double[] unWrap(Double... values) {
        int length = values.length;
        double[] array = new double[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Boolean[] wrap(boolean... values) {
        int length = values.length;
        Boolean[] array = new Boolean[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static boolean[] unWrap(Boolean... values) {
        int length = values.length;
        boolean[] array = new boolean[length];

        for(int i = 0; i < length; ++i) {
            array[i] = values[i];
        }

        return array;
    }

    public static Object[] wrap(Object obj) {
        if (isArray(obj)) {
            try {
                return (Object[])((Object[])obj);
            } catch (Exception var5) {
                String className = obj.getClass().getComponentType().getName();
                byte var4 = -1;
                switch(className.hashCode()) {
                    case -1325958191:
                        if (className.equals("double")) {
                            var4 = 7;
                        }
                        break;
                    case 104431:
                        if (className.equals("int")) {
                            var4 = 1;
                        }
                        break;
                    case 3039496:
                        if (className.equals("byte")) {
                            var4 = 4;
                        }
                        break;
                    case 3052374:
                        if (className.equals("char")) {
                            var4 = 3;
                        }
                        break;
                    case 3327612:
                        if (className.equals("long")) {
                            var4 = 0;
                        }
                        break;
                    case 64711720:
                        if (className.equals("boolean")) {
                            var4 = 5;
                        }
                        break;
                    case 97526364:
                        if (className.equals("float")) {
                            var4 = 6;
                        }
                        break;
                    case 109413500:
                        if (className.equals("short")) {
                            var4 = 2;
                        }
                }

                switch(var4) {
                    case 0:
                        return wrap((long[])((long[])obj));
                    case 1:
                        return wrap((int[])((int[])obj));
                    case 2:
                        return wrap((short[])((short[])obj));
                    case 3:
                        return wrap((char[])((char[])obj));
                    case 4:
                        return wrap((byte[])((byte[])obj));
                    case 5:
                        return wrap((boolean[])((boolean[])obj));
                    case 6:
                        return wrap((float[])((float[])obj));
                    case 7:
                        return wrap((double[])((double[])obj));
                    default:
                        throw new ToolkitException(var5);
                }
            }
        } else {
            throw new ToolkitException(StringUtils.format("[{}] is not Array!", new Object[]{obj.getClass()}));
        }
    }

    public static boolean isArray(Object obj) {
        if (null == obj) {
            throw new NullPointerException("Object check for isArray is null");
        } else {
            return obj.getClass().isArray();
        }
    }

    public static String toString(Object obj) {
        if (null == obj) {
            return null;
        } else if (isArray(obj)) {
            try {
                return Arrays.deepToString((Object[])((Object[])obj));
            } catch (Exception var5) {
                String className = obj.getClass().getComponentType().getName();
                byte var4 = -1;
                switch(className.hashCode()) {
                    case -1325958191:
                        if (className.equals("double")) {
                            var4 = 7;
                        }
                        break;
                    case 104431:
                        if (className.equals("int")) {
                            var4 = 1;
                        }
                        break;
                    case 3039496:
                        if (className.equals("byte")) {
                            var4 = 4;
                        }
                        break;
                    case 3052374:
                        if (className.equals("char")) {
                            var4 = 3;
                        }
                        break;
                    case 3327612:
                        if (className.equals("long")) {
                            var4 = 0;
                        }
                        break;
                    case 64711720:
                        if (className.equals("boolean")) {
                            var4 = 5;
                        }
                        break;
                    case 97526364:
                        if (className.equals("float")) {
                            var4 = 6;
                        }
                        break;
                    case 109413500:
                        if (className.equals("short")) {
                            var4 = 2;
                        }
                }

                switch(var4) {
                    case 0:
                        return Arrays.toString((long[])((long[])obj));
                    case 1:
                        return Arrays.toString((int[])((int[])obj));
                    case 2:
                        return Arrays.toString((short[])((short[])obj));
                    case 3:
                        return Arrays.toString((char[])((char[])obj));
                    case 4:
                        return Arrays.toString((byte[])((byte[])obj));
                    case 5:
                        return Arrays.toString((boolean[])((boolean[])obj));
                    case 6:
                        return Arrays.toString((float[])((float[])obj));
                    case 7:
                        return Arrays.toString((double[])((double[])obj));
                    default:
                        throw new ToolkitException(var5);
                }
            }
        } else {
            return obj.toString();
        }
    }

    public static <T> String join(T[] array, String conjunction) {
        if (null == array) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            boolean isFirst = true;
            Object[] var4 = array;
            int var5 = array.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                T item = var4[var6];
                if (isFirst) {
                    isFirst = false;
                } else {
                    sb.append(conjunction);
                }

                if (isArray(item)) {
                    sb.append(join(wrap(item), conjunction));
                } else if (item instanceof Iterable) {
                    sb.append(CollectionUtils.join((Iterable)item, conjunction));
                } else if (item instanceof Iterator) {
                    sb.append(CollectionUtils.join((Iterator)item, conjunction));
                } else {
                    sb.append(item);
                }
            }

            return sb.toString();
        }
    }
}
