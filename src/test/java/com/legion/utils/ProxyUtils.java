package com.legion.utils;

import com.legion.GlobalVar;
import com.legion.tests.annotations.*;
import com.legion.entity.TestStep;
import com.legion.enums.HttpType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.directory.Attributes;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


public class ProxyUtils {
    private static Logger logger = LoggerFactory.getLogger(ProxyUtils.class);


    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {


        Annotation annotation = clazz.getAnnotation(SERVER.class);
        if (annotation == null) {
            throw new RuntimeException(String.format("API class not config @SERVER comment",
                    clazz.getName()));
        }

        String host;
        switch (clazz.getAnnotation(SERVER.class).value()){
            case GlobalVar.Base_SERVER:
                host = GlobalVar.Base_SERVER_URL;
                break;
            default:
                throw new RuntimeException(String.format("can not search the API Server address which configed by %s @HOST)",
                        clazz.getName(),
                        clazz.getAnnotation(SERVER.class).value(),
                        clazz.getAnnotation(SERVER.class).value()));
        }


        HttpUtils httpUtils = new HttpUtils(host);

        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        Annotation[] annotations = method.getAnnotations();
                        if (annotations.length == 0) {
                            throw new RuntimeException(String.format("not configurated API comment such as @POST and @GET",
                                    method.getName()));
                        }

                        HttpType httpType;
                        String path;
                        String description;


                        if (annotations[0] instanceof POST) {
                            httpType = HttpType.POST;
                            path = ((POST) annotations[0]).path();
                            description = ((POST) annotations[0]).description();
                        } else if (annotations[0] instanceof GET) {
                            httpType = HttpType.GET;
                            path = ((GET) annotations[0]).path();
                            description = ((GET) annotations[0]).description();
                        } else {
                            throw new RuntimeException(String.format("not support request type",
                                    method.getName(),
                                    annotations[0].annotationType()));
                        }

                        Annotation[][] parameters = method.getParameterAnnotations();
                        Integer length = parameters.length;
                        TestStep testStep = new TestStep();
                        if (length != 0) {
                            Map<String, Object> map = new HashMap<>();
                            Map<String, Object> maph = new HashMap<>();
                            for (Integer i = 0; i < length; i++) {

                                Annotation[] annos = parameters[i];
                                if (annos.length == 0) {
                                    throw new RuntimeException(String.format("not support the para",
                                            method.getName()));
                                }


                                if (annos[0] instanceof Param) {
                                    map.put((((Param) annos[0]).value()), args[i]);
                                    System.out.println("args[i]param=" +args[i]);
                                } else if (annos[0] instanceof Header) {
                                    maph.put((((Header) annos[0]).value()), args[i]);
                                    System.out.println("args[i]header=" +args[i]);
                                } else if (annos[0] instanceof Cookie) {
                                    maph.put((((Cookie) annos[0]).value()), args[i]);
                                    System.out.println("args[i]cookie=" +args[i]);
                                }else if (annos[0] instanceof PathVariable) {
                                    path = path.replaceFirst("\\{\\}", args[i].toString());
                                }
                                else {
                                    throw new RuntimeException(String.format("not support the para",
                                            method.getName(),
                                            annos[0].annotationType()));
                                }
                            }
                            testStep.setParams(map);
                            testStep.setHeader(maph);
                        }

                        testStep.setType(httpType);
                        testStep.setPath(path);

                        logger.info("[" + path + "]" + description);
                        return httpUtils.request(testStep);
                    }
                });
    }
}
