package com.example.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Complaint;
import com.example.entity.User;	
	@Repository
	public interface ComplaintRepository extends JpaRepository<Complaint,String>
	{
		List<Complaint> findByUser(User user);
}