package cn.itcast.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cn.itcast.entity.Employee;

// 直接在接口上面添加 @Mapper 注解，即可自动扫描，所以我们不需要再配置mapper 接口的包扫描
@Mapper
public interface EmployeeMapper {
	// 如果我们想要获取自增 id 的话可以这样设置
	@Options(useGeneratedKeys=true, keyProperty="eid")
	@Insert("insert into t_employee (emp_name, email, gender, birth_date)"
			+ "values(#{empName} , #{email}, #{gender}, #{birthDate})")
	public int addEmp(Employee emp);
	
	@Select("select eid, emp_name empName, email, gender, birth_date birthDate from t_employee")
	public List<Employee> emps();
	
	@Select("select eid, emp_name empName, email, gender, birth_date birthDate from t_employee where eid = #{eid}")
	public Employee getEmpById(Integer eid);
	
	// 这里为了简单起见，我们要求更新的时候，所有的属性都要传值
	// eid 肯定要有值，service 层可以检查一下
	@Update("update t_employee "
			+ "set emp_name = #{empName}, email = #{email}, gender = #{gender}, birth_date = #{birthDate}"
			+ "where eid = #{eid}")
	public int updateEmpById(Employee emp);
	
	@Delete("delete from t_employee where eid = #{eid}")
	public int deleteEmpById(Integer eid);
}
