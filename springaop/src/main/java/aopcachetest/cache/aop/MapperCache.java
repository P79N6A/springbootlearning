package aopcachetest.cache.aop;

import aopcachetest.cache.cachemanage.CacheManager;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class MapperCache {
    private static final String prefix = "acquisition:%s";
    private static final String splitor = ":";
    private static ImmutableSet<String> immutableSet = ImmutableSet.<String>builder().
            add(new String[]{"java.lang.Integer", "java.lang.Long", "java.lang.String", "java.lang.Boolean", "java.lang.Double"
                    , "java.lang.Byte", "java.lang.Float", "java.lang.Short", "java.lang.Character"}).
            build();
    @Autowired
    private Map<String, CacheManager> map;

    @Value("${ele.mapper.type:Redis}")
    private String type;
    @Value("${ele.mapper.globalid:false}")
    private boolean globalid;
    @Value("${ele.mapper.globalidName:globalid}")
    private String idName;
    @Value("${ele.mapper.expiretime:10}")
    private int expiretime;

    @Value("${ele.mapper.nullexpiretime:60}")
    private int nullexpiretime;
    @Value("${ele.mapper.unit:SECONDS}")
    private String unit;


    @Pointcut("execution(* aopcachetest.mapper.*.select*(..))")
    public void selectPointCut() {
    }


    @Around("selectPointCut()")
    public Object mapperCach(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        Object result = null;
        Object taregt = proceedingJoinPoint.getTarget();
        Object[] objects = proceedingJoinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        CacheManager cacheManager = null;
        if (type.equals("Redis")) {
            cacheManager = map.get("redisCache");
        } else if (type.equals("Guava")) {
            //Guava实现
            cacheManager = map.get("guavaCache");
        }
        String cacheKey = generateKey(taregt.getClass().getInterfaces()[0].getName(), method.getName(), objects);
        if (cacheManager != null && cacheManager.containsKey(cacheKey)) {
            //返回值：多值List和单值
            Type typeGeneric = method.getGenericReturnType();
            Class clazz = method.getReturnType();
            if (clazz.isAssignableFrom(List.class)) {
                if (typeGeneric instanceof ParameterizedType) {
                    Type[] types = ((ParameterizedType) typeGeneric).getActualTypeArguments();
                    result = JSON.parseArray(cacheManager.get(cacheKey), Class.forName(types[0].getTypeName()));
                }
            } else {
                result = JSON.parseObject(cacheManager.get(cacheKey), clazz);
            }
        } else if (cacheManager != null) {
            try {
                result = proceedingJoinPoint.proceed();
                if (result == null) {
                    cacheManager.set(cacheKey, JSON.toJSONString(result), nullexpiretime, Enum.valueOf(TimeUnit.class, unit));
                    return result;
                }
                cacheManager.set(cacheKey, JSON.toJSONString(result), expiretime, Enum.valueOf(TimeUnit.class, unit));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }

    private String generateKey(String className, String methodName, Object[] objects) throws Exception {
        StringBuilder key = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(key);
        stringBuilder.append(className).append(splitor);
        stringBuilder.append(methodName).append(splitor);
        for (Object o : objects) {
            if (immutableSet.contains(o.getClass().getName())) {
                stringBuilder.append(o.toString());
            } else {
                if (globalid) {
                    try {
                        stringBuilder.append(BeanUtils.getProperty(o, idName));
                    } catch (Exception e) {
                        //如果不存在，则取id
                        stringBuilder.append(getValue(o));
                    }
                } else {
                    stringBuilder.append(getValue(o));
                }
            }
        }
        formatter.format(prefix, stringBuilder);
        return key.toString();
    }

    private String getValue(Object o) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        Object o1 = BeanUtils.getProperty(o, "id");
        if (o1 != null && !globalid) {
            stringBuilder.append(o1.toString());
        } else {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                o1 = field.get(o);
                if (!(o1 == null || o1.toString().equals(""))) {
                    stringBuilder.append(field.getName()).append(o1.toString());
                }
            }
        }
        return stringBuilder.toString();
    }

}
