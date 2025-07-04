package org.example.users.repository;

import org.example.users.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhone(String phone);

    Page<User> findAllByCompanyIdIn(List<Long> companyIds, Pageable pageable);
}
