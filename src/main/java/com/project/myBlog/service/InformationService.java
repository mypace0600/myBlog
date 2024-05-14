package com.project.myBlog.service;

import com.project.myBlog.entity.Information;
import com.project.myBlog.repository.InformationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InformationService {

    private final InformationRepository informationRepository;
    private final StringRedisTemplate redisTemplate;

    private boolean isFirstVisit(String clientAddress) {
        // 클라이언트 주소를 키로 사용
        return !redisTemplate.hasKey(clientAddress);
    }

    private void storeClientAddress(String clientAddress) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        // 클라이언트 주소를 고유 키로 저장
        ops.set(clientAddress, "visited", Duration.ofMinutes(100L));
    }

    private void incrementVisitorCount() {
        String key = "daily_visitor_count_" + LocalDate.now(); // 오늘 날짜를 포함한 고유 키
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.increment(key);
    }

    private int getVisitorCount() {
        String key = "daily_visitor_count_" + LocalDate.now(); // 오늘 날짜를 포함한 고유 키
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String count = ops.get(key);
        return (count != null) ? Integer.parseInt(count) : 0;
    }

    @Transactional
    public void countVisitor(HttpServletRequest request) {
        String clientAddress = request.getRemoteAddr();

        if (isFirstVisit(clientAddress)) {
            incrementVisitorCount();
            storeClientAddress(clientAddress); // 방문자 수를 증가시키기 전에 주소를 저장
        }

        int todayCount = getVisitorCount(); // 현재 방문자 수

        LocalDate todayDate = LocalDate.now();
        Optional<Information> informationOptional = informationRepository.findByDateInfo(todayDate);

        if (!informationOptional.isPresent()) {
            // 해당 날짜 정보가 없다면 새로 만듦
            informationRepository.save(Information.builder()
                    .todayCount(todayCount)
                    .dateInfo(todayDate)
                    .build());
        } else {
            // 이미 정보가 있으면 업데이트
            Information information = informationOptional.get();
            information.setTodayCount(todayCount); // 오늘 방문자 수 업데이트
        }
    }

    public int getTodayCount() {
        LocalDate todayDate = LocalDate.now();
        Optional<Information> informationOptional = informationRepository.findByDateInfo(todayDate);
        if (informationOptional.isPresent()) {
            return informationOptional.get().getTodayCount();
        } else {
            throw new IllegalArgumentException("오늘의 방문자 수 정보가 없습니다.");
        }
    }

    public int getTotalCount() {
        // 전체 방문자 수는 여러 날짜의 todayCount를 합산해야 함
        return informationRepository.findAll().stream()
                .mapToInt(Information::getTodayCount)
                .sum();
    }
}
