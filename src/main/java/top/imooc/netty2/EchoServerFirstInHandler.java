package top.imooc.netty2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerFirstInHandler extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("�¼�ע��");
		ctx.fireChannelRegistered();
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("ȡ���¼�ע��");
		ctx.fireChannelUnregistered();
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("�ͻ������µĽ���:"+ctx.channel().remoteAddress());
	}
}
