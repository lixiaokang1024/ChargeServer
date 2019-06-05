package com.charge.util;

import com.charge.Exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class JsonUtil {

    private static ObjectMapper MAPPER;

    static {
        MAPPER = generateMapper(JsonInclude.Include.ALWAYS);
    }

    private JsonUtil() {
    }

    /**
     * 将json通过类型转换成对象
     *
     * <pre>
     *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\", \"password\":\"password\"}", User.class);
     * </pre>
     *
     * @param json json字符串
     * @param clazz 泛型类型
     * @return 返回对象
     * @throws IOException
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * 将json通过类型转换成对象
     *
     * <pre>
     *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\", \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new TypeReference&lt;List&lt;User&gt;&gt;);
     * </pre>
     *
     * @param json json字符串
     * @param typeReference 引用类型
     * @return 返回对象
     * @throws IOException
     */
    public static <T> T fromJson(String json, TypeReference<?> typeReference) {
        try {
            return (T) (typeReference.getType().equals(String.class) ? json : MAPPER.readValue(json, typeReference));
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换成json
     *
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user);
     * </pre>
     *
     * @param src 对象
     * @return 返回json字符串
     * @throws IOException
     */
    public static <T> String toJson(T src) {
        try {
            return src instanceof String ? (String) src : MAPPER.writeValueAsString(src);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * 将对象转换成json, 可以设置输出属性
     *
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user, {@link JsonInclude Inclusion.ALWAYS});
     * </pre>
     *
     * {@link JsonInclude Inclusion 对象枚举}
     * <ul>
     *     <li>{@link JsonInclude.Include Inclusion.ALWAYS 全部列入}</li>
     *     <li>{@link JsonInclude.Include Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
     *     <li>{@link JsonInclude.Include Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
     *     <li>{@link JsonInclude.Include Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
     * </ul>
     *
     * @param src 对象
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回json字符串
     * @throws IOException
     */
    public static <T> String toJson(T src, JsonInclude.Include inclusion) {
        if (src instanceof String) {
            return (String) src;
        }
        ObjectMapper customMapper = generateMapper(inclusion);
        try {
            return customMapper.writeValueAsString(src);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    public static <T> String toJson(T src, ObjectMapper mapper) {
        if (mapper == null) {
            mapper = MAPPER;
        }
        if (src instanceof String) {
            return (String) src;
        }
        try {
            return mapper.writeValueAsString(src);
        } catch (IOException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
     *
     * @return {@link ObjectMapper ObjectMapper}对象
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    /**
     * 通过Inclusion创建ObjectMapper对象
     *
     * {@link JsonInclude Inclusion 对象枚举}
     * <ul>
     *     <li>{@link JsonInclude.Include Inclusion.ALWAYS 全部列入}</li>
     *     <li>{@link JsonInclude.Include Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li>
     *     <li>{@link JsonInclude.Include Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
     *     <li>{@link JsonInclude.Include Inclusion.NON_NULL 字段为NULL时候不会列入}</li>
     * </ul>
     *
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回ObjectMapper对象
     */
    private static ObjectMapper generateMapper(JsonInclude.Include inclusion) {

        ObjectMapper customMapper = new ObjectMapper();

        // 设置输出时包含属性的风格
        customMapper.setSerializationInclusion(inclusion);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 所有日期格式都统一为以下样式
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return customMapper;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException
     *             如果分析类属性失败
     * @throws IllegalAccessException
     *             如果实例化 JavaBean 失败
     * @throws InstantiationException
     *             如果实例化 JavaBean 失败
     * @throws InvocationTargetException
     *             如果调用属性的 setter 方法失败
     */
    public static Object convertMap(Class type, Map map) throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
