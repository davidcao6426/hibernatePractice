package com.bezkoder.spring.oracle.controller;

import com.bezkoder.spring.oracle.model.Department;
import com.bezkoder.spring.oracle.model.Employee;
import com.vladmihalcea.hibernate.query.SQLExtractor;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.jpa.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.LoadQueryInfluencers;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.loader.OuterJoinLoader;
import org.hibernate.loader.criteria.CriteriaLoader;
import org.hibernate.persister.entity.OuterJoinLoadable;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DepartmentSessionController<T> {

    private Configuration config;
    private SessionFactory sessionFactory;
    private Session session;

    public DepartmentSessionController(){
        config = new Configuration().configure();
        sessionFactory = config.buildSessionFactory();
        session = sessionFactory.openSession();
    }

    public Department getDepartment() {
        Department d = null;

        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Department.class);
        criteria.add(Restrictions.and(Restrictions.eq("deptName", "test"), Restrictions.eq("deptNo", "123")));
        d = (Department) criteria.uniqueResult();

        return d;
    }

    public Department getDepartment2(){
        Department d = null;

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Department> c = builder.createQuery(Department.class);
        Root<Department> departmentRoot = c.from(Department.class);

        c.where(builder.and(builder.equal(departmentRoot.get("deptName"), "test"), builder.equal(departmentRoot.get("deptNo"), "123")));
        d = session.createQuery(c).getSingleResult();

        String sql = SQLExtractor.from(session.createQuery(c));
        System.out.println(sql);

        return d;
    }

    public Department getDepartment3(){
        Department d = null;

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Department> c = builder.createQuery(Department.class);
        Root<Department> departmentRoot = c.from(Department.class);
        Join join = departmentRoot.join("employees");

//        List<Predicate> predicates = new ArrayList<>();

//        predicates.add(builder.equal(join.get("employees"), itemGroupCategory.getGrpCategoryName()));

//        c.where(predicates.toArray(new Predicate[] {}));

        d = session.createQuery(c).getSingleResult();

        return d;


//        Predicate condition1 = builder.equal(departmentRoot.get("deptName"), "test");
//        Predicate condition2 = builder.equal(departmentRoot.get("deptNo"), "123");
//        Predicate condition = builder.and(condition1, condition2);
//        c.where(condition);

    }

    public Employee getEmployee(){
        Employee d = null;

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root = c.from(Employee.class);
//        Metamodel m = em.getMetamodel();
//        EntityType<Pet> petMetaModel = m.entity(Pet.class);

        Join<Employee, Department> join = root.join("department");

//        List<Predicate> predicates = new ArrayList<>();

//        predicates.add(builder.equal(join.get("employees"), itemGroupCategory.getGrpCategoryName()));

//        c.where(predicates.toArray(new Predicate[] {}));

        d = session.createQuery(c).getSingleResult();

        return d;
    }

    public Query getEmployeeList_query(){
        List<Employee> e = null;

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root = c.from(Employee.class);
        c.where(builder.equal(root.join("department").get("deptName"), "test"));

        return session.createQuery(c);
    }

    public List<Employee> getEmployeeList(){
        return getEmployeeList_query().getResultList();
    }

    public String getEmployeeList_sql(){
        return SQLExtractor.from(getEmployeeList_query());
    }

    public List<Employee> getEmployeeList2(){
        List<Employee> e = null;

        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root = c.from(Employee.class);
        Root<Department> root2 = c.from(Department.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(root.join("department").get("deptName"), "test"));

        c.where(predicates.toArray(new Predicate[] {}));
        c.multiselect(root, root2);

        e = session.createQuery(c).getResultList();

        return e;
    }

    public Query getEmployeeList2_query(){
        //Object[].class
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<Tuple> c = builder.createTupleQuery();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root = c.from(Employee.class);
//        可行解法1
//        Join<Employee, Department> droot = root.join("department", JoinType.INNER);
//        c.multiselect(root, droot).where(builder.equal(droot.get("deptName"), "test"));
//      Peter
//        Join<Employee, Department> droot = root.join("department", JoinType.INNER);
        root.fetch("department");
        root.fetch("department");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("department").get("deptName"), "test"));
        predicates.add(builder.equal(root.get("department").get("deptNo"), "1234"));
        c.select(root).where(predicates.toArray(new Predicate[] {}));

        return session.createQuery(c);
    }

    public List<Employee> getEmployeeList2_object(){
        return getEmployeeList2_query().getResultList();
    }

    public String getCriteriaBuilderSQL(){
        return SQLExtractor.from(getEmployeeList2_query());
    }

    public Criteria getEmployeeList2_preCriteria(){
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.createCriteria("department").add(Restrictions.eq("deptName", "test"));

        return criteria;
    }

    public List<T> getResultList(Criteria criteria){
        return criteria.list();
    }

    public T getResult(Criteria criteria){
        return (T)criteria.uniqueResult();
    }

    public List<T> getResultList(Query query){
        return query.getResultList();
    }

    public T getResult(Query query){
        return (T)query.getSingleResult();
    }

    public String getSQL(Query query) {
        return SQLExtractor.from(query);
    }

    public String getSQL(Criteria criteria) {
        String sql = "";
        try{
            CriteriaImpl c = (CriteriaImpl)criteria;
            SessionImpl s = (SessionImpl)c.getSession();
            SessionFactoryImplementor factory = (SessionFactoryImplementor)s.getSessionFactory();
            String[] implementors = factory.getImplementors( c.getEntityOrClassName() );
            CriteriaLoader loader = new CriteriaLoader((OuterJoinLoadable)factory.getEntityPersister(implementors[0]),
                    factory, c, implementors[0], s.getLoadQueryInfluencers());
            Field f = OuterJoinLoader.class.getDeclaredField("sql");
            f.setAccessible(true);
            sql = (String)f.get(loader);
//            System.out.println(sql);
        }catch (NoSuchFieldException | IllegalAccessException e)
        {
            try{
                CriteriaImpl c = (CriteriaImpl)criteria;
                SessionImpl s = (SessionImpl)c.getSession();
                SessionFactoryImplementor factory = (SessionFactoryImplementor)s.getSessionFactory();
                String[] implementors = factory.getImplementors( c.getEntityOrClassName() );
                CriteriaLoader loader = new CriteriaLoader((OuterJoinLoadable)factory.getEntityPersister(implementors[0]),
                        factory, c, implementors[0], s.getLoadQueryInfluencers());
                Field f = OuterJoinLoader.class.getDeclaredField("sql");
                f.setAccessible(true);
                sql = (String)f.get(loader);
//            System.out.println(sql);
            }catch (NoSuchFieldException | IllegalAccessException ex)
            {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }

        return sql;
    }

    public Criteria testCriteriaWithNotnullAndOrder(){
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.add(Restrictions.isNotNull("salary"));
        criteria.addOrder(Order.asc("salary"));

        return criteria;
    }

    public Query testQueryWithNotnullAndOrder(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root =  c.from(Employee.class);
        c.where(builder.isNotNull(root.get("salary"))).orderBy(builder.asc(root.get("salary")));;

        return session.createQuery(c);
    }

    public Criteria testCriteriaWithIn(){
        List<Float> list = new ArrayList<Float>() {{add(1F);add(2F);add(3F);}};
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.add(Restrictions.in("salary", list));

        return criteria;
    }

    public Query testQueryWithin(){
        List<Float> list = new ArrayList<Float>() {{add(1F);add(2F);add(3F);}};
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root =  c.from(Employee.class);
        c.where(root.get("salary").in(list));

        return session.createQuery(c);
    }

    public Criteria testCriteriaEqual(){
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.add(Restrictions.eq("department.deptName", "test"));
        criteria.setFetchMode("startConnection", org.hibernate.FetchMode.JOIN);

        return criteria;
    }

    public Query testQueryEqual(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root =  c.from(Employee.class);
        c.where(builder.equal(root.get("department").get("deptName"), "test"));

        return session.createQuery(c);
    }

    public Criteria testCriteriaType(){
        Class<Employee> cls = Employee.class;
        Criteria criteria = session.createCriteria(cls);
        criteria.add(Restrictions.isNotNull("salary"));
        criteria.addOrder(Order.asc("salary"));

        return criteria;
    }

    public Query testQueryType(){
        Class<Employee> cls = Employee.class;
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(cls);
        Root<Employee> root =  c.from(cls);
        c.where(builder.isNotNull(root.get("salary"))).orderBy(builder.asc(root.get("salary")));;

        return session.createQuery(c);
    }

    public Criteria testCriteriaOtherClassAttribute(){
        Criteria criteria = session.createCriteria(Employee.class);
        criteria.createAlias("department", "department");
        criteria.add(Restrictions.eq("department.deptName", "test"));

        return criteria;
    }

    public Query testQueryOtherClassAttribute(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root =  c.from(Employee.class);
        c.where(builder.equal(root.get("department").get("deptName"), "test"));

        return session.createQuery(c);
    }

    public Criteria testCriteriaProjection(){
        Criteria criteria = session.createCriteria(Employee.class);
        ProjectionList proListIdOnly = Projections.projectionList();
        proListIdOnly.add(Projections.property("empName"), "emp");
        criteria = criteria.setProjection(proListIdOnly);

        return criteria;
    }

    public Query testQuerySelect(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> c = builder.createQuery(Employee.class);
        Root<Employee> root =  c.from(Employee.class);
        c.select(root.get("empName"));

        return session.createQuery(c);
    }

    public Criteria testCriteriaMultiProjection(){
        Criteria criteria = session.createCriteria(Employee.class);
        ProjectionList proListIdOnly = Projections.projectionList();
        proListIdOnly.add(Projections.property("empName"), "emp");
        proListIdOnly.add(Projections.property("empNo"), "empNo");
        proListIdOnly.add(Projections.property("job"), "job");
        criteria = criteria.setProjection(proListIdOnly);

        return criteria;
    }

    public Query testQueryMultiSelect(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> c = builder.createQuery(Object[].class);
        Root<Employee> root =  c.from(Employee.class);
        List<Selection<?>> selections = new ArrayList<>();
        selections.add(root.get("empName"));
        selections.add(root.get("empNo"));
        selections.add(root.get("job"));
        c.multiselect(selections);
//        c.multiselect(root.get("empName"), root.get("empNo"), root.get("job"));

        return session.createQuery(c);
    }

    public Criteria testCriteriaMultiAdd(){
        Criteria criteria = session.createCriteria(Department.class);
        criteria.add(Restrictions.eq("deptName", "test"));
        criteria.add(Restrictions.eq("deptNo", "123"));

        return criteria;
    }

    public Query testQueryMultiEqual(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Department> cq = builder.createQuery(Department.class);
        Root<Department> root =  cq.from(Department.class);
        cq.where(builder.equal(root.get("deptName"), "test"), builder.equal(root.get("deptNo"), "123"));

        return session.createQuery(cq);
    }

    public Criteria testCriteriaCount(){
        Criteria criteria = session.createCriteria(Department.class);
        criteria.setProjection(Projections.rowCount());

        return criteria;
    }

    public Query testQueryCount(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> cq = builder.createQuery(Long.class);
        Root<Department> root =  cq.from(Department.class);
        cq.select(builder.count(root));

        return session.createQuery(cq);
    }

    public Criteria testCriteriaDistinct(){
        Criteria criteria = session.createCriteria(Department.class);
        criteria.setProjection(Projections.distinct(Projections.property("deptName")));

        return criteria;
    }

    public Query testQueryDistinct(){
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<String> cq = builder.createQuery(String.class);
        Root<Department> root =  cq.from(Department.class);
        cq.select(root.get("deptName")).distinct(true);

        return session.createQuery(cq);
    }
}
