package com.bezkoder.spring.oracle.controller;

import com.bezkoder.spring.oracle.model.Department;
import com.bezkoder.spring.oracle.model.Employee;
import org.hibernate.Criteria;
import org.junit.jupiter.api.Test;
//import org.springframework.util.Assert;
import org.testng.Assert;

import javax.persistence.Query;
import javax.persistence.Tuple;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentSessionControllerTest {

    @Test
    void test_get_department() {
        DepartmentSessionController controller = new DepartmentSessionController();

//        Assert.isTrue(controller.getDepartment().getDeptId() == 1);
        Assert.assertEquals(controller.getDepartment().getDeptId(), 1);
    }

    @Test
    void test_get_department2() {
        DepartmentSessionController controller = new DepartmentSessionController();

        Assert.assertEquals(controller.getDepartment2().getDeptId(), 1);
    }

    @Test
    void test_CriteriaBuilderAndCreateCriteriaSQLIsSame() {
        DepartmentSessionController controller = new DepartmentSessionController();

        List<Employee> expectList = controller.getResultList(controller.testCriteriaWithNotnullAndOrder());
        List<Employee> actualList = controller.getResultList(controller.testQueryWithNotnullAndOrder());

        String expect = controller.getSQL(controller.testCriteriaWithNotnullAndOrder());
        String actual =  controller.getSQL(controller.testQueryWithNotnullAndOrder());

        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_CriteriaBuilderAndCreateCriteriaSQLIsSameWithIn() {
        DepartmentSessionController controller = new DepartmentSessionController();

        List<Employee> actualList = controller.getResultList(controller.testQueryWithin());
        List<Employee> expectList = controller.getResultList(controller.testCriteriaWithIn());

        String actual =  controller.getSQL(controller.testQueryWithin());
        String expect = controller.getSQL(controller.testCriteriaWithIn());


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_CriteriaBuilderAndCreateCriteriaSQLIsSameWithEqual() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryEqual();
        Criteria criteria = controller.testCriteriaEqual();

        List<Employee> actualList = controller.getResultList(query);
        List<Employee> expectList = controller.getResultList(criteria);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

//    @Test
//    void test_get_department3() {
//        DepartmentSessionController controller = new DepartmentSessionController();
//
//        Department d = controller.getDepartment3();
//
//        Assert.isTrue(controller.getDepartment3().getDeptId() == 1);
//    }

    @Test
    void test_get_employee() {
        DepartmentSessionController controller = new DepartmentSessionController();

        Employee e = controller.getEmployee();

//        Assert.isTrue(.equals("test"));
        Assert.assertEquals(e.getDepartment().getDeptName(), "test");
    }

    @Test
    void test_get_employee_list() {
        DepartmentSessionController controller = new DepartmentSessionController();

        List<Employee> e = controller.getEmployeeList();

        Assert.assertEquals(e.size() , 1);
        Assert.assertEquals(e.get(0).getEmpName(), "test_employee3");
        Assert.assertEquals(e.get(0).getDepartment().getDeptName(),"test2");

        List<Employee> employeeList2 = controller.getEmployeeList2();

        Assert.assertEquals(employeeList2.size(), 2);
        Assert.assertEquals(employeeList2.get(0).getEmpName(),"test_employee");
        Assert.assertEquals(employeeList2.get(1).getEmpName(),"test_employee2");
        Assert.assertEquals(employeeList2.get(0).getDepartment().getDeptName(),"test");
    }

    @Test
    void test_BuilderAndCriteria_type() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryType();
        Criteria criteria = controller.testCriteriaType();

        List<Employee> actualList = controller.getResultList(query);
        List<Employee> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_BuilderAndCriteria_OtherClassAttribute() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryOtherClassAttribute();
        Criteria criteria = controller.testCriteriaOtherClassAttribute();

        List<Employee> actualList = controller.getResultList(query);
        List<Employee> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_ProjectionAndSelect() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQuerySelect();
        Criteria criteria = controller.testCriteriaProjection();

        List<Employee> actualList = controller.getResultList(query);
        List<Employee> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_MultiProjectionAndSelect() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryMultiSelect();
        Criteria criteria = controller.testCriteriaMultiProjection();

        List<Object[]> actualList = controller.getResultList(query);
        List<Object[]> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_MultiCondition() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryMultiEqual();
        Criteria criteria = controller.testCriteriaMultiAdd();

        List<Object[]> actualList = controller.getResultList(query);
        List<Object[]> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_Count() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryCount();
        Criteria criteria = controller.testCriteriaCount();

        List<Object[]> actualList = controller.getResultList(query);
        List<Object[]> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }

    @Test
    void test_Distinct() {
        DepartmentSessionController controller = new DepartmentSessionController();
        Query query = controller.testQueryDistinct();
        Criteria criteria = controller.testCriteriaDistinct();

        List<Object[]> actualList = controller.getResultList(query);
        List<Object[]> expectList = controller.getResultList(criteria);

        Assert.assertEquals(actualList, expectList);

        String actual =  controller.getSQL(query);
        String expect = controller.getSQL(criteria);


        Assert.assertEquals(actual, expect);
    }
}