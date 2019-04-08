package cn.itcast.service;

import java.sql.Connection;
import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.entity.Employee;
import cn.itcast.mapper.EmployeeMapper;

@Service
public class EmployeeService {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	// 引入 employeeMapper 进行普通的操作
	@Autowired
	private EmployeeMapper employeeMapper;
	// 引入 batchSqlSession ，如果需要批量操作的时候，就用这个
	@Autowired
	private SqlSession batchSqlSession;
	
	// 测试批量操作与普通操作，进行事务嵌套
	// 也就是说用到了两个 sqlSession ，但是spring 的事务管理器可以保证
	// 这两个 sqlSession 用的其实是同一个Connection 对象
	@Transactional
	public void batchAddEmp() {
		// 嵌套事务
		addEmp();
		Employee emp = new Employee();
		emp.setEmpName("rose");
		emp.setBirthDate(new Date());
		emp.setEmail("rose@qq.com");
		emp.setGender('女');
		// 打印一下connection 的地址
		Connection connection = batchSqlSession.getConnection();
		System.out.println(connection);
		EmployeeMapper mapper = batchSqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 10; i++) {
			mapper.addEmp(emp);
			if(i == 8) {
				// 故意抛个异常，看看能不能正常回滚， addEmp() 方法看看有没有添加成功
				throw new RuntimeException();
			}
		}
	}
	
	@Transactional
	public void addEmp() {
		// 打印一下 connection 的地址
		Connection connection = sqlSessionTemplate.getConnection();
		System.out.println(connection);
		Employee emp = new Employee();
		emp.setEmpName("eric");
		emp.setBirthDate(new Date());
		emp.setEmail("eric@qq.com");
		emp.setGender('男');
		employeeMapper.addEmp(emp);
	}
}
