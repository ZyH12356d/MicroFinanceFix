package com.sme.repository;

import com.sme.entity.Branch;
import com.sme.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

<<<<<<< Updated upstream
     List<Holiday> findByBranchId(Long branchId);

     boolean existsByBranchAndHolidayDate(Branch branch, Date holidayDate);
=======
    // ✅ Find holidays by branch
    List<Holiday> findByBranchId(Long branchId);

    // ✅ Check if a holiday already exists for a branch
    boolean existsByBranchAndHolidayDate(Branch branch, Date holidayDate);
>>>>>>> Stashed changes
}
