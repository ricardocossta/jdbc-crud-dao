package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class ProgamTesteDepartment {

	public static void main(String[] args) {
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("DEPARTMENT INSERT");
		Department dep = new Department(null, "Electronics");
		departmentDao.insert(dep);
		System.out.println("Department: " + dep);
		
		System.out.println("DEPARTMENT UPDATE");
		dep.setName("Foods");
		departmentDao.update(dep);
		
		System.out.println("DEPARTMENT DELETE BY ID");
		departmentDao.deleteById(11);
		
		System.out.println("DEPARTMENT FIND BY ID");
		dep = departmentDao.findById(5);
		System.out.println(dep);
		
		System.out.println("DEPARTMENT FIND ALL");
		List<Department> departments = departmentDao.findAll();
		for (Department department : departments) {
			System.out.println(department);
		}
	}
}
