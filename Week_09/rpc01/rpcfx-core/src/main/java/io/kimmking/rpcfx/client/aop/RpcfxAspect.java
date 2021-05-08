package io.kimmking.rpcfx.client.aop;

import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RpcfxAspect {

    @Pointcut("@annotation(io.kimmking.rpcfx.client.annotation.RpcfxReference)")
    public void RpcfxAspect() {
        System.out.println();

    }


    @Around("RpcfxAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // Object res = joinPoint.proceed();
        Object args = joinPoint.getArgs();

        // http请求远端controller
        RpcfxRequest request = new RpcfxRequest();
        // request.setMethod(joinPoint.getArgs();

        RpcfxResponse response = new RpcfxResponse();
        return response;
    }
}
