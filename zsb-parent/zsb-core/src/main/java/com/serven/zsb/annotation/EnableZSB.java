package com.serven.zsb.annotation;

import com.serven.zsb.configuration.ZSBAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by zhangjiayuan
 * Date: 2018/11/22
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ZSBAutoConfiguration.class})
@Documented
public @interface EnableZSB {
}
