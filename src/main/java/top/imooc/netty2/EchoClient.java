package top.imooc.netty2;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class EchoClient {

	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 8001;

		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.remoteAddress(new InetSocketAddress(host, port))
			.handler(new ChannelInitializer<SocketChannel>() {

						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// TODO Auto-generated method stub
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});
			//异步连接远程服务器，连接成功后输出已连接到服务器
			final ChannelFuture f = b.connect();
			f.addListener(new GenericFutureListener<Future<? super Void>>() {

				public void operationComplete(Future<? super Void> future) throws Exception {
					// TODO Auto-generated method stub
					if(future.isSuccess()) {
						System.out.println("以连接到服务器");
						ByteBuf byteBuf=Unpooled.copiedBuffer("Hello client:",Charset.defaultCharset());
						ChannelFuture channelFuture=f.channel().writeAndFlush(byteBuf);
						
					}else {
						
						Throwable throwable=new Throwable();
						throwable.printStackTrace();
					}
				}
			});
		} finally {
			
			group.shutdownGracefully().sync();
		}

	}
}
