package io.kimmking.rpcfx.client.netty;

import com.alibaba.fastjson.JSON;
import io.kimmking.rpcfx.api.RpcfxResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

public class NettyHttpClientOutboundHandler extends ChannelInboundHandlerAdapter{
    private String respJson;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        if (msg instanceof FullHttpResponse) {
            respJson = msg.toString();
            System.out.println("resp json: "+respJson);
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String cont = buf.toString(CharsetUtil.UTF_8);
            System.out.println(cont);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public String getRespJson() {
        return this.respJson;
    }
}
