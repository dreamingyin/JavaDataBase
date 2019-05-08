# JavaDataBase
Templates used to connect databases in Java include jdbc, c3p0 and Druid
C3P0连接池
1.C3P0连接池简介
C3P0地址：https://sourceforge.net/projects/c3p0/?source=navbar C3P0是一个开源的连接池。Hibernate框架，默认推荐使用C3P0作为连接池实现。 C3P0的jar包： c3p0-0.9.1.2.jar

2.常用的配置参数


3.C3P0连接池基本使用
（1）C3P0配置文件
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
	<!-- 默认配置，当使用ComboPooledDataSource无参构造器时，使用的就是这个配置 -->
	<default-config>
		<!-- 基本配置 -->
		<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
		<property name="driverClass">com.mysql.jdbc.Driver</property>
		<property name="user">root</property>
		<property name="password">123</property>
		<!-- 每次增量，当需要创建Connection对象时，一次创建几个 -->
		<property name="acquireIncrement">3</property>
		<!-- 当创建池对象后，池中应该有几个Connection对象 -->
		<property name="initialPoolSize">10</property>
		<!-- 池中最少Connection个数，如果少于这个值，就会创建Connection -->
		<property name="minPoolSize">2</property>
		<!-- 池中最大连接个数 -->
		<property name="maxPoolSize">10</property>
	</default-config>
	<!-- 命名配置，new ComboPooledDataSource("oralce-config")时，使用的就是这个配置 -->
	<named-config name="oracle-config">
		<property name="jdbcUrl">jdbc:mysql://localhost:3306/mydb1</property>
		<property name="driverClass">com.mysql.jdbc.Driver</property>
		<property name="user">root</property>
		<property name="password">123</property>
		<property name="acquireIncrement">3</property>
		<property name="initialPoolSize">10</property>
		<property name="minPoolSize">2</property>
		<property name="maxPoolSize">10</property>
	</named-config>
</c3p0-config>
 
（2）API介绍
com.mchange.v2.c3p0.ComboPooledDataSource 类表示C3P0的连接池对象，常用2种创建连接池的方式： 1.无参构造，使用默认配置， 2.有参构造，使用命名配置

public ComboPooledDataSource():无参构造使用默认配置

public ComboPooledDataSource():有参构造使用命名配置

public Connection getConnection() throws SQLException:从连接池中取出一个连接

4.使用步骤
1. 导入jar包c3p0-0.9.1.2.jar

2. 编写c3p0-config.xml 配置文件，配置对应参数

3. 将配置文件放在src目录下

4. 创建连接池对象ComboPooledDataSource ，使用默认配置或命名配置

5. 从连接池中获取连接对象

6. 使用连接对象操作数据库

7. 关闭资源

二、DRUID连接池
1. DRUID简介
Druid是阿里巴巴开发的号称为监控而生的数据库连接池，Druid是目前最好的数据库连接池。在功能、性能、扩展性方面，都超过其他数据库连接池，同时加入了日志监控，可以很好的监控DB池连接和SQL的执行情况。Druid已经在阿里巴巴部署了超过600个应用，经过一年多生产环境大规模部署的严苛考验。Druid地址：

https://github.com/alibaba/druid DRUID 

连接池使用的jar包： druid-1.0.9.jar

2.DRUID连接池基本使用
（1）API介绍
com.alibaba.druid.pool.DruidDataSourceFactory 类有创建连接池的方法

public static DataSource createDataSource(Properties properties):创建一个连接池，连接池的参数使用properties中的数据



（2）使用步骤
1. 在src目录下创建一个properties文件，并设置对应参数

2. 加载properties文件的内容到Properties对象中

3. 创建DRUID连接池，使用配置文件中的参数

4. 从DRUID连接池中取出连接

5. 执行SQL语句

6. 关闭资源

（3）配置文件
driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/day25
username=root
password=root
initialSize=5
maxActive=10
maxWait=3000
minIdle=3
（4）DataSourceUtils工具类
import com.alibaba.druid.pool.DruidDataSourceFactory;
 
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
 
public class DataSourceUtils {
    // 1. 声明静态数据源成员变量
    private static DataSource ds;
 
    // 2. 创建连接池对象
    static {
        // 加载配置文件中的数据
        InputStream is =
                DataSourceUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        Properties pp = new Properties();
        try {
            pp.load(is);
            // 创建连接池，使用配置文件中的参数
            ds = DruidDataSourceFactory.createDataSource(pp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // 3. 定义公有的得到数据源的方法
    public static DataSource getDataSource() {
        return ds;
    }
 
    // 4. 定义得到连接对象的方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
 
    // 5.定义关闭资源的方法
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }
 
    // 6.重载关闭方法
    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}
三、JdbcTemplate
1. JdbcTemplate概念
JDBC已经能够满足大部分用户最基本的需求，但是在使用JDBC时，必须自己来管理数据库资源如：获取PreparedStatement，设置SQL语句参数，关闭连接等步骤。JdbcTemplate就是Spring对JDBC的封装，目的是使JDBC更加易于使用。JdbcTemplate是Spring的一部分。 JdbcTemplate处理了资源的建立和释放。他帮助我们避免一些常见的错误，比如忘了总要关闭连接。他运行核心的JDBC工作流，如Statement的建立和执行，而我们只需要提供SQL语句和提取结果。

Spring源码地址：https://github.com/spring-projects/spring-framework 

在JdbcTemplate中执行SQL语句的方法大致分为3类：

1. execute ：可以执行所有SQL语句，一般用于执行DDL语句。

2. update ：用于执行INSERT 、UPDATE 、DELETE 等DML语句。

3. queryXxx ：用于DQL数据查询语句。

2. JdbcTemplate使用过程
（1）准备DruidDataSource连接池或C3P0连接池

（2）导入依赖的jar包



（3）创建JdbcTemplate对象，传入连接池对象

（4）调用execute、update、queryXxx等方法

3.JdbcTemplate实现增删改
（1）创建表
    public static void main(String[] args) {
        String sql = "CREATE TABLE product("
                + "pid INT PRIMARY KEY AUTO_INCREMENT,"
                + "pname VARCHAR(20),"
                + "price DOUBLE"
                + ");";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        jdbcTemplate.execute(sql);
    }
（2）插入数据
public static void main(String[] args) {
        String sql = "INSERT INTO product VALUES (NULL, ?, ?);";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        jdbcTemplate.update(sql, "iPhone3GS", 3333);
        jdbcTemplate.update(sql, "iPhone4", 5000);
        jdbcTemplate.update(sql, "iPhone4S", 5001);
        jdbcTemplate.update(sql, "iPhone5", 5555);
        jdbcTemplate.update(sql, "iPhone5C", 3888);
        jdbcTemplate.update(sql, "iPhone5S", 5666);
        jdbcTemplate.update(sql, "iPhone6", 6666);
        jdbcTemplate.update(sql, "iPhone6S", 7000);
        jdbcTemplate.update(sql, "iPhone6SP", 7777);
        jdbcTemplate.update(sql, "iPhoneX", 8888);
    }
（3）更改数据
public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "UPDATE product SET pname=?, price=? WHERE pid=?;";
        // String sql = "update pruduct set pname=?,price=? where pid=?;";
        int i = jdbcTemplate.update(sql, "小米5s", 1999, 10);
        System.out.println(i);
    }
（4）删除数据
 public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "DELETE FROM product WHERE pid=?;";
        int i = jdbcTemplate.update(sql, 7);
        System.out.println(i);
    }
4.JdbcTemplate实现查询
（1）queryForObject查询数据返回整数
 public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "SELECT price FROM product where pid=3;";
        Integer integer = jdbcTemplate.queryForObject(sql, int.class);
        System.out.println(integer);
    }
（2）queryForObject查询数据返回String
public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "SELECT pname FROM product where pid=1;";
        String s = jdbcTemplate.queryForObject(sql, String.class);
        System.out.println(s);
    }
（3）queryForMap查询数据返回Map集合
public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM product where pid=?";
        Map<String, Object> forMap = jdbcTemplate.queryForMap(sql,6);
        Set<Map.Entry<String, Object>> entries = forMap.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println(entry.getKey() + "---" + entry.getValue());
        }
    }
（4）queryForList查询数据返回List集合
public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM product";
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> objectMap : mapList) {
            Set<Map.Entry<String, Object>> entries = objectMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                System.out.print(entry.getKey() + "---" + entry.getValue());
            }
            System.out.println();
        }
    }
（5）query使用RowMapper做映射返回对象
public class Product {
    private int pid;
    private String pname;
    private double price;
 
    @Override
    public String toString() {
        return "Pruduct{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                '}';
    }
 
    public Product() {
    }
 
    public Product(int pid, String pname, double price) {
        this.pid = pid;
        this.pname = pname;
        this.price = price;
    }
 
    public int getPid() {
        return pid;
    }
 
    public void setPid(int pid) {
        this.pid = pid;
    }
 
    public String getPname() {
        return pname;
    }
 
    public void setPname(String pname) {
        this.pname = pname;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
}
public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "select * from product";
        List<Product> products = jdbcTemplate.query(sql, (resultSet, i) -> {
            Product p = new Product();
            p.setPid(resultSet.getInt("pid"));
            p.setPname(resultSet.getString("pname"));
            p.setPrice(resultSet.getDouble("price"));
            return p;
        });
        for (Product product : products) {
            System.out.println(product);
        }
    }
（6）query使用BeanPropertyRowMapper做映射返回对象
public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "select * from product";
        List<Product> products = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
        for (Product product : products) {
            System.out.println(product);
        }
    }
--------------------- 
作者：空城1995 
来源：CSDN 
原文：https://blog.csdn.net/fy_java1995/article/details/82313205 
版权声明：本文为博主原创文章，转载请附上博文链接！
