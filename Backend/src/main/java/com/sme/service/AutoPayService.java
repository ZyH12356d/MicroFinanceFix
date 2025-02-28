package com.sme.service;

import java.time.LocalDate;

public interface AutoPayService {
    void runAutoPay();
    LocalDate getNextWorkingDay(LocalDate date);
}
