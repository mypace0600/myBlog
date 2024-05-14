package com.project.myBlog.repository;

import com.project.myBlog.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface InformationRepository extends JpaRepository<Information, Integer> {

    Optional<Information> findByDateInfo(LocalDate date);
}
