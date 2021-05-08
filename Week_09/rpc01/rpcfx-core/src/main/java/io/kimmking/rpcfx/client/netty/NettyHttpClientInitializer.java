package io.kimmking.rpcfx.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;


public class NettyHttpClientInitializer extends ChannelInitializer<SocketChannel> {
    NettyHttpClientOutboundHandler outboundHandler = new NettyHttpClientOutboundHandler();

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline p = socketChannel.pipeline();
        p.addLast(new HttpResponseDecoder());
        p.addLast(new HttpRequestEncoder());
        p.addLast(outboundHandler);
    }

    public String getResponse() {
        return outboundHandler.getRespJson();
    }
}
