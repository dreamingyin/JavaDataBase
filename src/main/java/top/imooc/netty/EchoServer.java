package top.imooc.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	public static void main(String[] args) throws Exception {
		final EchoServerHandler serverHandler=new EchoServerHandler  ();
		int port=8001;
		EventLoopGroup group=new NioEventLoopGroup();
		try {
		//ServerBootstrap是netty 的一个服务器引导类
		ServerBootstrap b=new ServerBootstrap(); 
		b.group(group)
		.channel(NioServerSocketChannel.class) //设置通道类型
		.localAddress(new InetSocketAddress(port)) //设置监听端口
		.childHandler(new ChannelInitializer<SocketChannel>() { //初始化责任链

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ch.pipeline().addLast(serverHandler); 
			}
		});
		
			ChannelFuture f=b.bind().sync(); //开启监听
			if(f.isSuccess()) {
				System.out.println(EchoServer.class.getName()+"start and listening for the connection on:"+f.channel().localAddress());
				f.channel().closeFuture().sync();
				
			}
		} finally {
			// TODO Auto-generated catch block
			group.shutdownGracefully().sync();
		}
		
	}
}
