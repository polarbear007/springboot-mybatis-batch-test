package cn.itcast;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SpringConfig {
	// 注意，我们不可以另外创建一个批量操作的SqlSessionTemplate
	// 因为虽然 sqlSessionTemplate 实现了 SqlSession 接口，但是sqlSessionTemplate 是线程安全的，可以单例
	// 而mybatis 的实现类比如   DefaultSqlSession 却是线程不安全的，不可以单例
	//  与spring 整合以后，Mapper 代理开发，所有的mapper 接口代理对象，内部用的都是同一个 sqlSessionTemplate
	//  关于 sqlSessionTemplate 如何保证线程安全，我们有时间再去研究吧
	
	// 反正你不能在一个项目中建两个 sqlSessionTemplate 就对了，如果建两个，会报错
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.SIMPLE);
	}
	
	// 不能建指操作的 SqlSessionTemplate， 我们可以建批量操作的 SqlSession
	// 其实 SqlSessionTemplate 就是 SqlSession 接口的实现类
	// 而且 SqlSessionTemplate 内部还包含着一个 SqlSession 对象，不知道什么模式，代理或者委托者模式
	
	// 【注意】 sqlSession 是线程不安全的，所以我们这里可不能配置成单例的，必须配置成 prototype
	@Bean
	@Scope("prototype")
	public SqlSession batchSqlSession(@Autowired SqlSessionFactory sqlSessionFactory) {
		return sqlSessionFactory.openSession(ExecutorType.BATCH);
	}
}
