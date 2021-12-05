package com.example.roadtrafficaccident.advice;

import com.example.roadtrafficaccident.entity.log.LogEntity;
import com.example.roadtrafficaccident.exceptions.AddressNotFoundException;
import com.example.roadtrafficaccident.exceptions.AreaNotFoundException;
import com.example.roadtrafficaccident.exceptions.RTAEntityNotFoundException;
import com.example.roadtrafficaccident.exceptions.RTASNotFoundException;
import com.example.roadtrafficaccident.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AdviceProcessor {

    private final LogService logService;
    private final HttpServletRequest httpServletRequest;

    @Pointcut("execution(public * com.example.roadtrafficaccident.controller.RTAController.*(..)) && args(inputArgs)")
    public void pointcut(Object inputArgs){};

    @Around(value = "pointcut(inputArgs)")
    public Object doLog(ProceedingJoinPoint pjp, Object inputArgs) throws Throwable {

        Optional<Object> result = Optional.ofNullable(null); // зачем?? опшенал и так с нулом внутри
        Exception except = null; // аналогично убрать
        Signature sign = pjp.getSignature();
        LocalDateTime timestamp = LocalDateTime.now();
        String clientHost = httpServletRequest.getRemoteHost();
        String method = sign.getDeclaringType() + "." + sign.getName();
        long startTime = System.currentTimeMillis();
        log.debug("before executing {}. Requesting from remote host {} with param: {}",
                method,
                clientHost,
                inputArgs);
        try {
            result = Optional.ofNullable(pjp.proceed());
        } catch (DataIntegrityViolationException exception) {
            saveLog(method, timestamp, System.currentTimeMillis() - startTime, result, exception.getRootCause(), clientHost);
            log.debug("after executing {}. Exception: {}", method, exception);
            throw exception;
        } catch (ConstraintViolationException | AreaNotFoundException | AddressNotFoundException | RTAEntityNotFoundException | RTASNotFoundException exception) {
            saveLog(method, timestamp, System.currentTimeMillis() - startTime, result, exception, clientHost);
            log.debug("after executing {}. Exception: {}", method, exception);
            throw exception;
        }
        // у тебя куча блоков с одинаковой логикой для разных исключкний, если так и задумано сделай им всем единого наследнига и лови его либо вот так все в один catch

        saveLog(method, timestamp, System.currentTimeMillis() - startTime, result, except, clientHost);
        log.debug("after executing {}. Returned value {}", method, result);
        return result.get(); // NPE?

    }

    private void saveLog(String method, LocalDateTime timeStamp, Long executionTime, Optional<Object> result, Throwable exception, String clientHost) {
        logService.save(LogEntity.builder()
                .method(method)
                .timeStamp(timeStamp)
                .executionTime(executionTime)
                .result(result.orElse("").toString())
                .exception(exception == null ? "" : exception.getMessage())
                .clientHost(clientHost)
                .build());
    }


}
