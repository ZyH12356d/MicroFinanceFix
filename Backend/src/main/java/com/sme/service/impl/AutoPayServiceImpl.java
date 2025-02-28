//package com.sme.service.impl;
//
//import com.sme.repository.AccountTransactionRepository;
//import com.sme.repository.CurrentAccountRepository;
//import com.sme.service.AutoPayService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//
//@Service
//public class AutoPayServiceImpl implements AutoPayService {
//
//    @Autowired
//    private LoanRepaymentRepository loanRepaymentRepository;
//
//    @Autowired
//    private CurrentAccountRepository currentAccountRepository;
//
//    @Autowired
//    private AccountTransactionRepository accountTransactionRepository;
//
//    @Autowired
//    private HolidaySetupRepository holidaySetupRepository;
//
//    @Override
//    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
//    public void runAutoPay() {
//        LocalDate today = LocalDate.now();
//        today = getNextWorkingDay(today); // Get the next available working day
//
//        List<LoanRepayment> repayments = loanRepaymentRepository.findByDueDate(today);
//
//        for (LoanRepayment repayment : repayments) {
//            CurrentAccount account = currentAccountRepository.findByCifNumber(repayment.getCifNumber());
//            double balance = account.getBalance();
//            double dueAmount = repayment.getDueAmount();
//
//            if (balance >= dueAmount) {
//                // ✅ Full Payment
//                account.setBalance(balance - dueAmount);
//                repayment.setStatus("PAID");
////
////                AccountTransaction transaction = new AccountTransaction();
////                transaction.setCurrentAccount(account);
////                transaction.setTransactionType("Loan Repayment");
////                transaction.setAmount(dueAmount);
////                transaction.setTransactionDate(today);
////
////                accountTransactionRepository.save(transaction);
//            } else if (balance > 0) {
//                // 🟡 Partial Payment
//                repayment.setStatus("PARTIAL");
//                repayment.setDueAmount(dueAmount - balance);
//                account.setBalance(0);
//
//                AccountTransaction transaction = new AccountTransaction();
//                transaction.setCurrentAccount(account);
//                transaction.setTransactionType("Partial Repayment");
//                transaction.setAmount(balance);
//                transaction.setTransactionDate(today);
//
//                accountTransactionRepository.save(transaction);
//            } else {
//                // ❌ No Payment, move to overdue
//                repayment.setStatus("OVERDUE");
//                double lateFee = 5000; // Late Fee Amount
//                double iod = (dueAmount * 0.03); // 3% IOD
//
//                repayment.setDueAmount(dueAmount + lateFee + iod);
//
//                AccountTransaction lateFeeTransaction = new AccountTransaction();
//                lateFeeTransaction.setCurrentAccount(account);
//                lateFeeTransaction.setTransactionType("Late Fee");
//                lateFeeTransaction.setAmount(lateFee);
//                lateFeeTransaction.setTransactionDate(today);
//
//                accountTransactionRepository.save(lateFeeTransaction);
//            }
//
//            currentAccountRepository.save(account);
//            loanRepaymentRepository.save(repayment);
//        }
//    }
//
//    @Override
//    public LocalDate getNextWorkingDay(LocalDate date) {
//        while (holidaySetupRepository.existsByHolidayDate(date)) {
//            date = date.plusDays(1); // Move to the next day
//        }
//        return date;
//    }
//}
//
