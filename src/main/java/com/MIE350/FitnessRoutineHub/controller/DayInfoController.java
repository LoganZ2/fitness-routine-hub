package com.MIE350.FitnessRoutineHub.controller;

import com.MIE350.FitnessRoutineHub.model.entity.DayInfo;
import com.MIE350.FitnessRoutineHub.model.service.IDayInfoService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users/day-info")
public class DayInfoController {

    private final IDayInfoService dayInfoService;

    public DayInfoController(IDayInfoService dayInfoService) {
        this.dayInfoService = dayInfoService;
    }

    @GetMapping("/{id}")
    public List<DayInfo> getDayInfos(@PathVariable Long id) {
        return dayInfoService.getDayInfos(id);
    }

    @GetMapping("/{id}/challenge")
    public Boolean checkChallengeCompletion(@PathVariable Long id, @RequestParam Instant date) {
        return dayInfoService.checkChallengeCompletion(id, date);
    }

    @PutMapping("/{id}")
    public DayInfo updateDayInfo(@PathVariable Long id, @RequestBody DayInfo dayInfo) {
        return dayInfoService.updateDayInfo(id, dayInfo);
    }
}
