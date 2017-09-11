## android中运用aop进行数据采集

android开发中做数据埋点一般都是直接写在方法里面，这样做虽说是能够完成功能，但是代码的入侵性太强，整体埋点通用性也不强。在后台开发中，对系统可以采用aop进行日志和性能监控，于是搜了下android中也是可以采用aop的方式进行日志记录和性能监控的。

### 技术点

1. 面向切面编程，这里采用aspectj
2. gradle自定义插件，在代码编译时调用aspectj进行处理
3. 自定义编译时注解，运行时注解

#### 实现步骤

* 自定义gradle插件，在代码编译时调用aspectj进行处理，主要参考开源项目[hugo](https://github.com/JakeWharton/hugo)

```
public class AspectPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.dependencies {
            compile 'org.aspectj:aspectjrt:1.8.6'
        }

        variants.all { variant ->

            JavaCompile javaCompile = variant.javaCompile
            javaCompile.doLast {
                String[] args = ["-showWeaveInfo",
                                 "-1.5",
                                 "-inpath", javaCompile.destinationDir.toString(),
                                 "-aspectpath", javaCompile.classpath.asPath,
                                 "-d", javaCompile.destinationDir.toString(),
                                 "-classpath", javaCompile.classpath.asPath,
                                 "-bootclasspath", project.android.bootClasspath.join(File.pathSeparator)]
                log.debug "ajc args: " + Arrays.toString(args)

                MessageHandler handler = new MessageHandler(true);
                new Main().run(args, handler);
                for (IMessage message : handler.getMessages(null, true)) {
                    switch (message.getKind()) {
                        case IMessage.ABORT:
                        case IMessage.ERROR:
                        case IMessage.FAIL:
                            log.error message.message, message.thrown
                            break;
                        case IMessage.WARNING:
                            log.warn message.message, message.thrown
                            break;
                        case IMessage.INFO:
                            log.info message.message, message.thrown
                            break;
                        case IMessage.DEBUG:
                            log.debug message.message, message.thrown
                            break;
                    }
                }
            }
        }

    }
}
```

* 在app中引入插件`apply plugin: 'com.min.aspect'`
* 定义注解，编译时注解用于标记切点，运行时注解用于记录埋点名称以便在后面获取

```
/**
 * Created by minyangcheng on 2017/9/7.
 * <p>
 * 用于注解切点方法
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface RecordPoint {
}
```

```
/**
 * Created by minyangcheng on 2017/9/7.
 * <p>
 * 用于注解埋点动作名称
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordLog {

    String value() default "";

}
```

* 定义切面和处理方法

```
@Aspect
public class RecordLogAspect {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String POINTCUT_METHOD =
            "execution(@com.min.aop_demo.record.RecordPoint * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithRecordLog() {
    }

    @Around("methodAnnotatedWithRecordLog()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        method.setAccessible(true);
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        RecordLog recordLog = method.getAnnotation(RecordLog.class);
        String name = recordLog.value();
        Object result = joinPoint.proceed();
        String pointPos = className + "." + methodName + "()";
        RecordHandler.getInstance().handle(pointPos, name, result);
        return result;
    }

}
```

```
public class RecordHandler {

    private static class HOLDER {
        public static final RecordHandler INSTANCE = new RecordHandler();
    }

    private RecordHandler() {
    }

    public static RecordHandler getInstance() {
        return HOLDER.INSTANCE;
    }

    /**
     * 处理埋点数据回调
     *
     * @param point        埋点位置信息:类名方法
     * @param name         埋点名称
     * @param externalData 埋点附加数据，该数据由被埋点方法的返回，可能为空
     */
    public void handle(String point, String name, Object externalData) {
        Timber.d("point=%s , name=%s , result=%s", point, name, externalData);
    }

}
```

#### 参考
1. <http://www.jianshu.com/p/0fa8073fd144>
2. <https://github.com/JakeWharton/hugo>
3. <http://blog.csdn.net/eclipsexys/article/details/50973205>
4. <http://blog.csdn.net/crazy__chen/article/details/52014672>