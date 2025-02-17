package com.sme.service;

import com.sme.entity.Branch;
import com.sme.entity.Holiday;
import com.sme.repository.BranchRepository;
import com.sme.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private GoogleCalendarService googleCalendarService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // ✅ Fetch Myanmar holidays from Google Calendar and store them in DB
    @Transactional
    public void importMyanmarHolidays(int year) throws Exception {
        List<Map<String, String>> holidays = googleCalendarService.fetchMyanmarHolidays(year);

        for (Map<String, String> holidayData : holidays) {
            Date holidayDate = dateFormat.parse(holidayData.get("date"));
            String holidayName = holidayData.get("name");

            // Get all branches
            List<Branch> branches = branchRepository.findAll();
            for (Branch branch : branches) {
                // Check if holiday already exists for this branch
                boolean exists = holidayRepository.existsByBranchAndHolidayDate(branch, holidayDate);
                if (!exists) {
                    Holiday holiday = new Holiday();
                    holiday.setHolidayDate(holidayDate);
                    holiday.setDescription(holidayName);
                    holiday.setBranch(branch);
                    holidayRepository.save(holiday);
                }
            }
        }
    }

    // ✅ Get holidays by branch
    public List<Holiday> getHolidaysByBranch(Long branchId) {
        return holidayRepository.findByBranchId(branchId);
    }
}
