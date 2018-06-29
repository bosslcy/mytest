package com.itheima;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// 1、加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");

			// 2、获取数据库链接
			/**
			 * 问题1：频繁获取释放数据库连接，影响系统性能
			 * 解决：数据库连接池  C3P0 DBCP DRUID(德鲁伊，阿里巴巴荣誉出品，号称前无古人后无来者世界最强没有之一)
			 */
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8", "root", "root");
			// 3、定义sql语句 ?表示占位符
			/**
			 * 问题2：sql语句硬编码，后期维护困难
			 * 如果sql语句和java代码分离，比如放到配置文件。Mybatis就是这么干的
			 */
			String sql = "select * from blog  where author = ?";
			// 4、获取预处理statement
			preparedStatement = connection.prepareStatement(sql);
			// 5、设置参数，第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
			/**
			 * 问题3：参数和占位符一一对应，sql语句where条件发生变化时，维护困难
			 */
			preparedStatement.setString(1, "李四");
			// 6、向数据库发出sql执行查询，查询出结果集
			resultSet = preparedStatement.executeQuery();
			// 7、解析处理结果集
			/**
			 * 问题4：解析麻烦，查询列硬编码
			 * 如果是单条记录，返回对象；如果是多条记录，返回对象集合
			 */
			while (resultSet.next()) {
				System.out.println(resultSet.getString("id") + "  " + resultSet.getString("author"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 8、释放资源
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
