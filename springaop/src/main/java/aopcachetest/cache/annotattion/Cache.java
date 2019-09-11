package aopcachetest.cache.annotattion;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 缓存时间单位为秒
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {
    //key使用 , 为分隔符，例如{"#user.name","#user.age","#id"}
    //如果方法入参为PO对象，必须指定key      如果为Long，String之类，可以不指定key
    String[] key() default "";
    long expire() default 1000;
    //Guava   Redis
    String type() default "Redis";
    //单位支持Redis
    TimeUnit unit() default TimeUnit.SECONDS;

}
