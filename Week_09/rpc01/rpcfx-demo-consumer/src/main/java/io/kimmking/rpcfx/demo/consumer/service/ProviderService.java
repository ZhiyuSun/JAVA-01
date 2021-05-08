package io.kimmking.rpcfx.demo.consumer.service;

import io.kimmking.rpcfx.client.annotation.RpcfxReference;
import io.kimmking.rpcfx.demo.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ProviderService {

    @Autowired
    UserService userService;

}
