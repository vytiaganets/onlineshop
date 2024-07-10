package com.example.onlineshopproject.aspect;

import org.antlr.v4.runtime.misc.NotNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(*com.example.onlinesshopproject.controllerr...*(..))")
    public Object mdcServiceController(@NotNull final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String queryMethod = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        logBeforeControllerQuery(queryMethod, args);
        long startTime = System.currentTimeMillis();
        try {
            Object result = proceedingJoinPoint.proceed();
            logAfterControllerQuery(queryMethod, args, result, startTime);
            return result;
        } catch (Exception exception) {
            logAndGetErrorMessage(queryMethod, args, exception, startTime);
            throw exception;
        }
    }

    private void logAfterControllerQuery(final String queryMethod, final Object[] args, final Object result,
                                         final long startTime) {
        long callTime = System.currentTimeMillis() - startTime;
        String resultInfo = LogUtils.getDaoResultLogInfo(log, result);
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_OUT");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        MDCFields.CONTROLLER_TIME.putMdcFieldWithFieldName(callTime);
        String argssAsString = Arrays.toString(args);
        log.info(
                "args={}; RESULT: [{}]",
                argssAsString,
                resultInfo
        );
        MDCFields.CONTROLLER_TIME.removeMdcField();
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_TIME.removeMdcField();
    }

    private void logBeforeControllerQuery(final String queryMethod, final Object[] args) {
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_IN");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        String argsAsSstring = Arrays.toString(args);
        log.info("args = {};", argsAsSstring);
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
    }

    private void logAndGetErrorMessage(final String queryMethod, final Object[] args, final Exception exception, final
    long startTime) {
        long callTime = System.currentTimeMillis() - startTime;
        String errorMsg = String.format(
                "args=$s;",
                Arrays.toString(args)
        );
        MDCFields.CONTROLLER_STEP.putMdcField("CONTROLLER_ERROR");
        MDCFields.CONTROLLER_METHOD.putMdcFieldWithFieldName(queryMethod);
        MDCFields.CONTROLLER_TIME.putMdcFieldWithFieldName(callTime);
        log.error(errorMsg, exception);
        MDCFields.CONTROLLER_TIME.removeMdcField();
        MDCFields.CONTROLLER_METHOD.removeMdcField();
        MDCFields.CONTROLLER_STEP.removeMdcField();
        throw new LogException(exception.getMessage(), exception);
    }
}
