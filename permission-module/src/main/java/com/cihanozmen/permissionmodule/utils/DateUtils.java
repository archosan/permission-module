package com.cihanozmen.permissionmodule.utils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {

    private Set<LocalDate> holidays = new HashSet<>(Arrays.asList(
            // 2024 yılında sabit olan resmi tatiller
            LocalDate.of(2024, 1, 1), // Yeni Yıl
            LocalDate.of(2024, 4, 23), // Ulusal Egemenlik ve Çocuk Bayramı
            LocalDate.of(2024, 5, 1), // Emek ve Dayanışma Günü
            LocalDate.of(2024, 5, 19), // Atatürk'ü Anma, Gençlik ve Spor Bayramı
            LocalDate.of(2024, 8, 30), // Zafer Bayramı
            LocalDate.of(2024, 10, 29), // Cumhuriyet Bayramı
            // 2024 yılı için tahmini dini bayramlar (Bu tarihler her yıl için
            // güncellenmelidir)
            LocalDate.of(2024, 6, 9), // Ramazan Bayramı'nın 1. günü
            LocalDate.of(2024, 6, 10), // Ramazan Bayramı'nın 2. günü
            LocalDate.of(2024, 6, 11), // Ramazan Bayramı'nın 3. günü
            LocalDate.of(2024, 10, 14), // Kurban Bayramı'nın 1. günü
            LocalDate.of(2024, 10, 15), // Kurban Bayramı'nın 2. günü
            LocalDate.of(2024, 10, 16), // Kurban Bayramı'nın 3. günü
            LocalDate.of(2024, 10, 17) // Kurban Bayramı'nın 4. günü
    ));

    public int calculateWeekendAndHolidayDays(LocalDate startDate, LocalDate endDate) {
        int dayOff = 0;
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek().getValue() == 6 || currentDate.getDayOfWeek().getValue() == 7
                    || holidays.contains(currentDate)) {
                dayOff++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return dayOff;
    }
}
