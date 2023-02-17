package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class ProgramTestSeller {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("SELLER FIND BY ID");
		Seller seller = sellerDao.findById(5);
		System.out.println(seller);
		
		System.out.println("SELLER FIND BY DEPARTMENT");
		Department department = new Department(3, null);
		List<Seller> sellers = sellerDao.findByDepartment(department);
		for (Seller obj : sellers) {
			System.out.println(obj);
		}
		
		System.out.println("SELLER FIND ALL");
		sellers = sellerDao.findAll();
		for (Seller obj : sellers) {
			System.out.println(obj);
		}
		
		System.out.println("SELLER INSERT");
		Seller newSeller = new Seller(null, "Ricardo", "ricardo@gmail.com", new Date(), 4000.0, department);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! Id: " + newSeller.getId());
		
		System.out.println("SELLER UPDATE");
		seller.setName("Ricardo Costa");
		sellerDao.update(seller);
		
		System.out.println("SELLER DELETE");
		sellerDao.deleteById(46);
	}

}
