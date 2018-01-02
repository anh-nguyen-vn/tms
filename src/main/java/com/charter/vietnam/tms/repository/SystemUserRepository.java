package com.charter.vietnam.tms.repository;

import com.charter.vietnam.tms.repository.entity.SystemUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Integer>, JpaSpecificationExecutor<SystemUserEntity> {

    SystemUserEntity findByUsername(String username);
}
