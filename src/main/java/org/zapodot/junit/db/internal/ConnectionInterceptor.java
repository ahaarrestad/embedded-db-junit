package org.zapodot.junit.db.internal;

import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.AllArguments;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.Origin;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.RuntimeType;
import net.bytebuddy.instrumentation.method.bytecode.bind.annotation.This;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Needs to be public to be used by ByteBuddy. Part of internal api, so it may be changed or removed without prior warning
 */
public class ConnectionInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionInterceptor.class);

    private ConnectionInterceptor() {

    }

    public static void close() {
        logger.debug("close() is suppressed");
    }

    @RuntimeType
    public static Object intercept(@Origin(cacheMethod = true) Method method,
                                   @This ConnectionProxy delegator,
                                   @AllArguments Object[] arguments) throws Exception {
        return method.invoke(delegator.getDelegatedConnection(), arguments);
    }
}
