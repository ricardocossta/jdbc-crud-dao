package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{
	
	Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department department) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", new String[] {"id"});
			pst.setString(1, department.getName());
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected > 0) {
				rs = pst.getGeneratedKeys();
				if (rs.next()) {
					long id = rs.getLong(1);
					department.setId((int) id);
				}
			} else {
				throw new DbException("No rows effected");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void update(Department department) {
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement("UPDATE DEPARTMENT SET Name = ? WHERE Id = ?");
			pst.setString(1, department.getName());
			pst.setInt(2, department.getId());
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected == 0) {
				System.out.println("Id not found");
			} else {
				System.out.println("Update Completed");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("DELETE DEPARTMENT WHERE ID = ?");
			pst.setInt(1, id);
			int rowsAffected = pst.executeUpdate();
			
			if (rowsAffected == 0) {
				System.out.println("Id not found");
			} else {
				System.out.println("Delete Completed");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("select * from department where id = ?");
			pst.setInt(1, id);
			rs = pst.executeQuery();

			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				return dep;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Department> deps = new ArrayList<>();
		
		try {
			pst = conn.prepareStatement("select * from department order by id");
			rs = pst.executeQuery();

			while (rs.next()) {
				Department dep = instantiateDepartment(rs);
				deps.add(dep);
			}
			return deps;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		return new Department(rs.getInt("Id"), rs.getString("Name"));
	}

}
