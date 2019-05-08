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
		//ServerBootstrap��netty ��һ��������������
		ServerBootstrap b=new ServerBootstrap(); 
		b.group(group)
		.channel(NioServerSocketChannel.class) //����ͨ������
		.localAddress(new InetSocketAddress(port)) //���ü����˿�
		.childHandler(new ChannelInitializer<SocketChannel>() { //��ʼ��������

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				// TODO Auto-generated method stub
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
