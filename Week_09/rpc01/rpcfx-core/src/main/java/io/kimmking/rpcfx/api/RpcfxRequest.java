package io.kimmking.rpcfx.api;

import lombok.Data;

@Data
public class RpcfxRequest {
  private String serviceClass;
    // private Class<?> serviceClass;
  private String method;
  private Object[] params;
}
