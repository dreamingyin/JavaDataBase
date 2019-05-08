DBC连接池C3P0和Druid 常见的连接池有两种，分别是 C3P0 和阿里巴巴提供的Druid。 C3P0的使用步骤：

导入jar包： c3p0-0.9.5.2.jar 和其依赖包：mchange-commons-java-0.2.12.jar 定义配置文件：

名称： c3p0.properties || c3p0-config.xml。放置于src目录下 配置文件的配置详解见代码注释中：
com.mysql.jdbc.Driver jdbc:mysql://localhost:3306/db3 root root
<!-- 连接池参数 -->
<!--初始化链接数量-->
<property name="initialPoolSize">5</property>
<!--存在的最大连接数量-->
<property name="maxPoolSize">10</property>
<!--参数检查时间-->
<property name="checkoutTimeout">3000</property>
创建连接池对象 ComboPooledDataSource 
实现代码： DataSource ds = new ComboPooledDataSource(); 
获取连接方法： getConnection 
实现代码：Connection conn = ds.getConnection(); 示例如下：

public class C3P0Demo01 { 
public static void main(String[] args) throws SQLException {
	//1.创建连接池对象 DataSource ds = new ComboPooledDataSource();
	//2.获取连接方法 Connection conn = ds.getConnection();
	System.out.println(conn);
	}
}

Druid的使用步骤：

导入jar包 druid-1.0.9.jar
定义配置文件
加载配置文件(通过加载器)
获取数据库连接池对象：通过工厂来来获取 DruidDataSourceFactory
获取连接：getConnection
配置文件： druid.properties

driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/db3
username=root 
password=root
initialSize=5 
maxActive=10 
maxWait=3000

示例代码：

public class DruidDemo01 { public static void main(String[] args) throws Exception { 
//1.导入jar包，配置文件

    //2.通过类加载器找到配置文件
    
    Properties pro = new Properties();
    InputStream in = DruidDemo01.class.getClassLoader().getResourceAsStream("druid.properties");
    pro.load(in);
    //3.获取对象
    DataSource ds = DruidDataSourceFactory.createDataSource(pro);
    //4.获取连接
    Connection conn = ds.getConnection();
    System.out.println(conn);
}
}
作者：By~XiaoTao 来源：CSDN 原文：https://blog.csdn.net/qq_41073263/article/details/86522317 版权声明：本文为博主原创文章，转载请附上博文链接！
