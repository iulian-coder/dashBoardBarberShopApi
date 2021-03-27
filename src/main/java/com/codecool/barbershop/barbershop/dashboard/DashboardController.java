package com.codecool.barbershop.barbershop.dashboard;

import com.codecool.barbershop.barbershop.user.CurrentUser;
import com.codecool.barbershop.barbershop.user.UserPrincipal;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

@RestController
@RequestMapping(path = "api/v1/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;


    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardData dashboardData(@CurrentUser UserPrincipal userPrincipal) {

        Date reportDate = new Date();
//        TODO Sort option input from User If needed
        Sort sort = Sort.by("bookingDate").ascending();
        Date firstDayOfTheMonth = java.sql.Date.valueOf(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()));
        Date lastDayOfTheMonth = java.sql.Date.valueOf(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));

        return dashboardService.getDataForDashBoard(reportDate, firstDayOfTheMonth, lastDayOfTheMonth, sort, userPrincipal.getId());
    }


}
