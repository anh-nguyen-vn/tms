package com.charter.vietnam.tms.repository;

import com.charter.vietnam.tms.repository.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
    List<UserRoleEntity> findByIsDeleted(Boolean isDeleted);
}
