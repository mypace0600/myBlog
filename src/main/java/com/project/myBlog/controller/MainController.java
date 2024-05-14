package com.project.myBlog.controller;

import com.project.myBlog.repository.PostRepository;
import com.project.myBlog.service.InformationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final InformationService informationService;
    private final PostRepository postRepository;

    @GetMapping({"","/"})
    public String main(HttpServletRequest request, Model model) {

        try {
            // 방문자 수 증가
            informationService.countVisitor(request);
        } catch (Exception e) {
            log.error("Failed to count visitor", e); // 오류 처리
        }

        LocalDate startDate = LocalDate.of(2024,5,1);
        LocalDate todayDate = LocalDate.now(); // 두 날짜 간의 일 수 계산
        int totalDaysOperation = (int) ChronoUnit.DAYS.between(startDate, todayDate);
        int totalVisitCount = informationService.getTotalCount();
        int todayVisitCount= informationService.getTodayCount();
        int totalPostCount = (int) postRepository.count();

        model.addAttribute("totalVisitCount",totalVisitCount);
        model.addAttribute("todayVisitCount",todayVisitCount);
        model.addAttribute("totalPostCount",totalPostCount);
        model.addAttribute("totalDaysOperation",totalDaysOperation);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            log.debug("@@@@@@@@@@@@@@ User : {}", auth.getPrincipal());
        } else {
            log.error("User authentication failed");
        }
        return "index";
    }
}
