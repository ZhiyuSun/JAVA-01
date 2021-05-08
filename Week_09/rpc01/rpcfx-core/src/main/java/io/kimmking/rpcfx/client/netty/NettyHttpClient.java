package io.kimmking.rpcfx.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyHttpClient {
    private String url;
    private int port;
    private String request;
    private NettyHttpClientInitializer clientInitializer;

    public NettyHttpClient(String url, int port, String reqJson) {
        this.url = url;
        this.port = port;
        this.request = reqJson;
        clientInitializer = new NettyHttpClientInitializer();
    }

    public String getResponse() {
        return clientInitializer.getResponse();
    }

    public void start() {
        EventLoopGroup workgroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workgroup);
        b.channel(NioSocketChannel.class);
        // b.option(ChannelOption.TCP_NODELAY, true);
        // b.handler(new NettyHttpClientInitializer());
        b.handler(clientInitializer);

        try {
            ChannelFuture f = b.connect(url, port).sync();
            f.channel().writeAndFlush(request);
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workgroup.shutdownGracefully();
        }
    }
}
