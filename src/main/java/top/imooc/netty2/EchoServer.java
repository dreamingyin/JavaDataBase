package top.imooc.netty2;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import top.imooc.netty.EchoServerOutHandler;

public class EchoServer {

	public static void main(String[] args) throws Exception {
		final EchoServerHandler serverHandler=new EchoServerHandler  ();
		final EchoServerOutHandler outHandler=new EchoServerOutHandler();
		final EchoServerFirstInHandler firstHandler=new EchoServerFirstInHandler();
		int port=8001;
		EventLoopGroup group=new NioEventLoopGroup();
		try {
		//ServerBootstrap��netty ��һ��������������
		ServerBootstrap b=new ServerBootstrap(); 
		b.group(group)
		.channel(NioServerSocketChannel.class) //����ͨ������
		.localAddress(new InetSocketAddress(port)) //���ü����˿�
		.childHandler(new ChannelInitializer<SocketChannel>() { //��ʼ��������

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
				ChannelPipeline channelPipeline=ch.pipeline();
				channelPipeline.addFirst(outHandler);
				channelPipeline.addLast(firstHandler);
				ch.pipeline().addLast(serverHandler); 
			}
		});
		
			ChannelFuture f=b.bind().sync(); //��������
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
