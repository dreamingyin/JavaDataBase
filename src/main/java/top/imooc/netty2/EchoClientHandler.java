package top.imooc.netty2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
		ctx.writeAndFlush(Unpooled.copiedBuffer("netty rocks",CharsetUtil.UTF_8));
		
	}
	

	@Override
	protected void channelRead0(ChannelHandlerContext arg0, ByteBuf buf){
		// TODO Auto-generated method stub
		System.out.println("client recvied:"+buf.toString(CharsetUtil.UTF_8));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		// TODO Auto-generated method stub
		cause.getMessage();
		ctx.close();
	}
}
