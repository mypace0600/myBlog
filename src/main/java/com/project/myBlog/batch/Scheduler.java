package com.project.myBlog.batch;

import com.project.myBlog.entity.Information;
import com.project.myBlog.repository.InformationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class Scheduler {
    @Autowired
    private InformationRepository informationRepository;


    @Transactional
    @Scheduled(cron = "00 00 10 * * *")
    public void saveTodayCount(){
        LocalDate todayInfo = LocalDate.now();
        Information information = informationRepository.findByDateInfo(todayInfo).orElseThrow(()->{
            return new IllegalArgumentException("날짜 정보가 없습니다.");
        });
        information.setTotalCount(information.getTotalCount()+information.getTodayCount());
    }
}
