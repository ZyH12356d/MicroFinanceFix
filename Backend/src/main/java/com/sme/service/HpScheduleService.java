package com.sme.service;

import com.sme.dto.HpScheduleDTO;
import com.sme.entity.HpRegistration;
import com.sme.entity.HpSchedule;
import com.sme.repository.HpRegistrationRepository;
import com.sme.repository.HpScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HpScheduleService {

    @Autowired
    private HpScheduleRepository hpScheduleRepository;

    @Autowired
    private HpRegistrationRepository hpRegistrationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<HpScheduleDTO> generateHpRepaymentSchedule(Long hpRegistrationId) {
        HpRegistration hpRegistration = hpRegistrationRepository.findById(hpRegistrationId)
                .orElseThrow(() -> new RuntimeException("HP Registration not found"));

        // Check if status is ACTIVE (4)
        if (hpRegistration.getStatus() != 4) {
            throw new IllegalStateException("HP Repayment Schedule can only be generated for ACTIVE (4) status.");
        }

        List<HpSchedule> schedules = new ArrayList<>();
        BigDecimal monthlyInstallment = hpRegistration.getLoanAmount()
                .subtract(hpRegistration.getDownPayment())
                .divide(BigDecimal.valueOf(hpRegistration.getLoanTerm()), BigDecimal.ROUND_HALF_UP);

        LocalDateTime startDate = hpRegistration.getStartDate();
        for (int i = 1; i <= hpRegistration.getLoanTerm(); i++) {
            HpSchedule schedule = new HpSchedule();
            schedule.setDate(Timestamp.valueOf(startDate.plusMonths(i)));
            schedule.setInterestAmount(1000L); // Placeholder for interest calculation
            schedule.setPrincipalAmount(monthlyInstallment.longValue());
            schedule.setLateDay(0L);
            schedule.setLateFee(BigDecimal.ZERO);
            schedule.setPrincipalOd("N/A");
            schedule.setInterestOd("N/A");
            schedule.setInstallmentNo("Installment " + i);
            schedule.setHpRegistrationId(hpRegistrationId);
            schedule.setLateFeePaidDate(null);
            schedules.add(schedule);
        }

        hpScheduleRepository.saveAll(schedules);

        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, HpScheduleDTO.class))
                .collect(Collectors.toList());
    }

    public List<HpScheduleDTO> getHpSchedulesByHpRegistrationId(Long hpRegistrationId) {
        return hpScheduleRepository.findByHpRegistrationId(hpRegistrationId).stream()
                .map(schedule -> modelMapper.map(schedule, HpScheduleDTO.class))
                .collect(Collectors.toList());
    }
}
