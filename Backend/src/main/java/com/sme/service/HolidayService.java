package com.sme.service;

import com.sme.entity.Branch;
import com.sme.entity.Holiday;
import com.sme.repository.BranchRepository;
import com.sme.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Transactional
    public void generateWeekendsForYear(int year) {
        List<Branch> branches = branchRepository.findAll(); // ✅ Get all branches
        List<Holiday> holidays = new ArrayList<>();

        // Loop through all days of the year
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        while (!startDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = startDate.getDayOfWeek();

            // ✅ Check if the day is Saturday or Sunday
            if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
                for (Branch branch : branches) {
                    Holiday holiday = new Holiday();
                    holiday.setHolidayDate(java.sql.Date.valueOf(startDate));
                    holiday.setDescription(dayOfWeek.name() + " (Weekend)");
                    holiday.setBranch(branch);
                    holidays.add(holiday);
                }
            }
            startDate = startDate.plusDays(1);
        }

        holidayRepository.saveAll(holidays); // ✅ Bulk insert all weekends
    }

    // ✅ Get holidays by branch
    public List<Holiday> getHolidaysByBranch(Long branchId) {
        return holidayRepository.findByBranchId(branchId);
    }
}
