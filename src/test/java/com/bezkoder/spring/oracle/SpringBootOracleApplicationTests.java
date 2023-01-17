package com.bezkoder.spring.oracle;

import com.bezkoder.spring.oracle.model.Department;
import com.bezkoder.spring.oracle.model.Employee;
import com.bezkoder.spring.oracle.repository.DepartmentRepository;
import com.bezkoder.spring.oracle.repository.EmployeeRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.persistence.criteria.Predicate;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest
class SpringBootOracleApplicationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Test
	void test_get_employee_in_department() {
		Optional<Department> departmentData = departmentRepository.findById(1);
		Assert.isTrue(departmentData.isPresent());
		Department department = departmentData.get();
		Collection<Employee> departments = department.getEmployees();
		Assert.notEmpty(departments);
	}

	@Test
	void test_get_department() {
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Department d = new Department();
		// 取得Session
		Session session = sessionFactory.openSession();
		Criteria c = session.createCriteria(Department.class);
		c.add(Restrictions.and(Restrictions.eq("dept_name", "test"), Restrictions.eq("dept_no", "123")));
		d = (Department) c.uniqueResult();

		Assert.isTrue(d.getDeptId() == 1);
//		// 開啟交易
//		Transaction tx= session.beginTransaction();
//		// 直接儲存物件
//		session.save(user);
//		// 送出交易
//		tx.commit();
//		session.close();
	}

	@Test
	void test_get_department3() {
		Configuration config = new Configuration().configure();
		SessionFactory sessionFactory = config.buildSessionFactory();
		Department d = new Department();
		// 取得Session
		Session session = sessionFactory.openSession();
		Criteria c = session.createCriteria(Department.class);
		c.add(Restrictions.and(Restrictions.eq("dept_name", "test"), Restrictions.eq("dept_no", "123")));
		d = (Department) c.uniqueResult();

		Assert.isTrue(d.getDeptId() == 1);
//		// 開啟交易
//		Transaction tx= session.beginTransaction();
//		// 直接儲存物件
//		session.save(user);
//		// 送出交易
//		tx.commit();
//		session.close();
	}

//	Criteria c = sessionFactory.getCurrentSession().createCriteria(LksData.class);
//        c.add(Restrictions.and(Restrictions.eq("lkpTypeName", lkpType), Restrictions.eq("lkpValueCode", lkpValueCode)));
//	lksData = (LksData) c.uniqueResult();

//	CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
//	CriteriaQuery<LksData> c = builder.createQuery(LksData.class);
//        c.having((Predicate) Restrictions.and(Restrictions.eq("lkpTypeName", lkpType), Restrictions.eq("lkpValueCode", lkpValueCode)));
//	lksData = sessionFactory.getCurrentSession().createQuery(c).getSingleResult();
}
