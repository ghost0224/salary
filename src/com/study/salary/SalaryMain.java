package com.study.salary;

import java.util.ArrayList;
import java.util.List;

public class SalaryMain {

    public static void main(String[] args) {
        new SalaryMain().generateReport(args);
    }

    public void generateReport(String[] args) {
        //arg[0]=startingSalary
        //arg[1]=incrementPercent
        //arg[2]=incrementFrequently(4=quarterly; 2=half-yearly; 1=annually)
        //arg[3]=deductions
        //arg[4]=deductionsFrequently(4=quarterly; 2=half-yearly; 1=annually)
        //arg[5]=prediction
        if (args.length != 6) {
            System.out.println("Please enter the param.");
            return;
        }
        System.out.print("startingSalary:" + args[0] + ".");
        Double startingSalary = changeToDouble(args[0]);
        System.out.print("incrementPercent:" + args[1] + ".");
        Integer incrementPercent = changeToInteger(args[1]);
        System.out.print("incrementFrequently:" + args[2] + " (4=quarterly; 2=half-yearly; 1=annually). ");
        Integer incrementFrequently = changeToInteger(args[2]);
        System.out.print("deductions:" + args[3] + ".");
        Double deductions = changeToDouble(args[3]);
        System.out.print("deductionsFrequently:" + args[4] + " (4=quarterly; 2=half-yearly; 1=annually). ");
        Integer deductionsFrequently = changeToInteger(args[4]);
        System.out.println("prediction:" + args[5] + ".");
        Integer prediction = changeToInteger(args[5]);

        if (checkNotNull(startingSalary, incrementPercent, incrementFrequently, deductions, deductionsFrequently, prediction)) {
            if (startingSalary < 1) {
                System.out.println("Starting salary can not less than 1.");
                return;
            }
            if(incrementFrequently < 1 || incrementFrequently > 12 || deductionsFrequently < 1 || incrementFrequently > 12) {
                System.out.println("Frequently of increment or deductions can not less than 1 and more than 12.");
                return;
            }
            if (incrementPercent < 0 || deductions < 0) {
                System.out.println("Do not accept a negative number for increment or deduction.");
                return;
            }
            if (prediction < 1) {
                System.out.println("Predication can not less than 1.");
                return;
            }
            double salary = startingSalary;
            List<ReportItem> incrementList = new ArrayList<>();
            List<ReportItem> deductionList = new ArrayList<>();
            for (int i = 1; i <= prediction; i++) {
                // step1 for increment data.
                double incrementAmount = salary;
                for (int j = 1; j <= incrementFrequently; j++) {
                    incrementAmount += incrementAmount * (incrementPercent * 0.01);
                }
                double temp = incrementAmount;
                incrementAmount = incrementAmount - salary;
                ReportItem incrementItem = new ReportItem();
                incrementItem.setStartingSalary(salary);
                incrementItem.setNumberOfIncrements(incrementFrequently);
                incrementItem.setIncrement(incrementPercent);
                incrementItem.setIncrementAmount(incrementAmount);
                incrementItem.setYear(i);
                incrementList.add(incrementItem);

                double deductionAmount = salary;
                salary = temp;
                temp = deductionAmount;

                // step2 for deduction data
                for (int k = 1; k <= deductionsFrequently; k++) {
                    deductionAmount -= deductionAmount * (deductions * 0.01);
                }
                deductionAmount = temp - deductionAmount;
                ReportItem deductionItem = new ReportItem();
                deductionItem.setYear(i);
                deductionItem.setStartingSalary(salary);
                deductionItem.setNumberOfDeduction(deductionsFrequently);
                deductionItem.setDeduction(deductions);
                deductionItem.setDeductionAmount(deductionAmount);
                deductionList.add(deductionItem);
            }
            System.out.println("==================================Increment Report==============================================");
            System.out.println("Year | Starting Salary | Number of Increments | Increment% | Increment Amount");
            for (int i = 0; i < incrementList.size(); i++) {
                ReportItem item = incrementList.get(i);
                System.out.println(item.getYear() + " | " + item.getStartingSalary() + " | " + item.getNumberOfIncrements() + " | " + item.getIncrement() + " | " + item.getIncrementAmount());
            }
            System.out.println("==================================Deduction Report==============================================");
            System.out.println("Year | Starting Salary | Number of Deductions | Deduction% | Deduction Amount");
            for (int i = 0; i < deductionList.size(); i++) {
                ReportItem item = deductionList.get(i);
                System.out.println(item.getYear() + " | " + item.getStartingSalary() + " | " + item.getNumberOfDeduction() + " | " + item.getDeduction() + " | " + item.getDeductionAmount());
            }
            System.out.println("====================================Prediction============================================");
            System.out.println("Year | Starting Salary | Increment Amount | Deduction Amount | Salary Growth");
            for (int i = 0; i < incrementList.size(); i++) {
                ReportItem incrementItem = incrementList.get(i);
                ReportItem deductionItem = deductionList.get(i);
                System.out.println(incrementItem.getYear() + " | " + incrementItem.getStartingSalary() + " | " + incrementItem.getIncrementAmount() + " | " + deductionItem.getDeductionAmount() + " | " + (incrementItem.getIncrementAmount() - deductionItem.getDeductionAmount()));
            }
        }

    }

    private boolean checkNotNull(Double startingSalary, Integer incrementPercent, Integer incrementFrequently, Double deductions, Integer deductionsFrequently, Integer prediction) {
        if(null == startingSalary || null == incrementPercent || null == incrementFrequently || null == deductions || null == deductionsFrequently || null == prediction) {
            return false;
        }
        return true;
    }

    private Integer changeToInteger(String str) {
        Integer num = null;
        try {
            num = Integer.valueOf(str);
            System.out.println("");
        } catch (RuntimeException e) {
            System.out.println("Please input number.");
        }
        return num;
    }

    private Double changeToDouble(String str) {
        Double num = null;
        try {
            num = Double.valueOf(str);
            System.out.println("");
        } catch (RuntimeException e) {
            System.out.println("Please input number.");
        }
        return num;
    }


}
