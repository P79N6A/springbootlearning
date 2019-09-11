package aopcachetest.cache.aop;


import aopcachetest.cache.annotattion.Cache;
import aopcachetest.cache.cachemanage.CacheManager;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

//@Component
//@Aspect
public class CacheAspect {

    @Autowired
    private Map<String, CacheManager> map;

    private SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    private static final String prefix = "acquisition:%s";

    private static final String splitor = ":";

    @Around("@annotation(Cache)")
    public Object handleCache(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        Object result = null;
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = null;
        Object[] args = proceedingJoinPoint.getArgs();
        method = methodSignature.getMethod();
        Cache cache = method.getDeclaredAnnotation(Cache.class);
        String type = cache.type();
        CacheManager cacheManager = null;
        if (type.equals("Redis")) {
            cacheManager = map.get("reidsCache");
        } else if (type.equals("Guava")) {
            //Guava实现
            cacheManager = map.get("guavaCache");
        }
        String[] key = cache.key();
        //获取RedisKey
        String cacheKey = generateKey(proceedingJoinPoint.getTarget().getClass().getName(), method, args, key);
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
        } else {
            try {
                result = proceedingJoinPoint.proceed();
                cacheManager.set(cacheKey, JSON.toJSONString(result), cache.expire(), cache.unit());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }

    private String generateKey(String className, Method method, Object[] args, String[] key) {

        StringBuilder redisKey = new StringBuilder();
        Formatter formatter = new Formatter(redisKey);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(className).append(splitor);
        stringBuilder.append(method.getName()).append(splitor);
        if ("".equals(key)) {
            for (Object o : args) {
                stringBuilder.append(o.toString()).append(splitor);
            }
        } else {
            stringBuilder.append(parseKey(discoverer.getParameterNames(method), args, key));
        }
        formatter.format(prefix, stringBuilder.toString());
        return redisKey.toString();
    }

    private String parseKey(String[] parameters, Object[] objects, String[] key) {
        StringBuilder res = new StringBuilder();
        EvaluationContext context = new StandardEvaluationContext();
        for (int j = 0; j < parameters.length; j++) {
            context.setVariable(parameters[j], objects[j]);
        }
        for (String s : key) {
            if (s.contains(".")) {
                res.append(spelExpressionParser.parseExpression(s).getValue(context).toString());
            } else {
                res.append(context.lookupVariable(s.substring(1)));
            }
        }
        return res.toString();
    }

}
