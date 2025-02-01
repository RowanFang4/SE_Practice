package com.example.student.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<student, Long> {
    // 檢查是否有相同 email 的學生
    boolean existsByEmail(String email);

    // 檢查是否有相同名稱的學生
    boolean existsByName(String name);

    // 根據 department 查詢學生
    // List<student> findByDepartment(String department);

    // 根據 name 和 department 查詢學生
    // Optional<student> findByNameAndDepartment(String name, String department);

    // 根據名稱排序後查詢學生列表
    // List<student> findByNameOrderByIdAsc(String name);
}
