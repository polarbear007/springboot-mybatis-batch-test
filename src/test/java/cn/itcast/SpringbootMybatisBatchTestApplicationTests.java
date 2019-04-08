package cn.itcast;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.itcast.mapper.EmployeeMapper;
import cn.itcast.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMybatisBatchTestApplicationTests {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Test
	public void contextLoads() throws SQLException {
		System.out.println(dataSource.getConnection());
	}
	
	// 请不要直接在 test 方法上面添加 @Transactional 注解
	// junit 默认会把所有操作都进行回滚
	@Test
	public void test() {
		employeeService.batchAddEmp();
	}
}
