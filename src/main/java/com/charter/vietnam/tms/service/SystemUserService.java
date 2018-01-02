package com.charter.vietnam.tms.service;

import com.charter.vietnam.tms.model.Pagination;
import com.charter.vietnam.tms.model.SystemUserModel;
import com.charter.vietnam.tms.model.condition.SystemUserSearchCondition;
import com.charter.vietnam.tms.repository.SystemUserRepository;
import com.charter.vietnam.tms.repository.UserRoleRepository;
import com.charter.vietnam.tms.repository.entity.SystemUserEntity;
import com.charter.vietnam.tms.repository.entity.SystemUserEntity_;
import com.charter.vietnam.tms.repository.entity.UserRoleEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SystemUserService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void createSystemUser(SystemUserModel systemUser) {
        SystemUserEntity systemUserEntity = new SystemUserEntity();
        systemUserEntity.setUsername(systemUser.getUsername());
        systemUserEntity.setEmail(systemUser.getEmail());
        systemUserEntity.setPassword(bCryptPasswordEncoder.encode(systemUser.getPassword()));
        systemUserEntity.setFirstname(systemUser.getFirstName());
        systemUserEntity.setLastname(systemUser.getLastName());
        systemUserEntity.setRemark(systemUser.getRemark());
        systemUserEntity.setRoleId(systemUser.getRoleId());

        LOGGER.info("Saving new system user [{}]", systemUser.getUsername());
        systemUserRepository.save(systemUserEntity);
        LOGGER.info("Saved new system user [{}]", systemUser.getUsername());
    }

    public List<UserRoleEntity> getAllUserRole() {
        return userRoleRepository.findByIsDeleted(false);
    }

    public Page<SystemUserEntity> searchSystemUserByCondition(SystemUserSearchCondition searchCondition, Pagination pagination) {
        Specification<SystemUserEntity> specification = createSystemUserCriteria(searchCondition);
        Sort sort = new Sort(Sort.Direction.DESC, SystemUserEntity_.createdTimestamp.getName());

        PageRequest pageRequest = new PageRequest(pagination.getPageIndex(), pagination.getRecordPerPage(), sort);

        Page<SystemUserEntity> systemUserEntityList = systemUserRepository.findAll(specification, pageRequest);

        return systemUserEntityList;
    }

    private Specification<SystemUserEntity> createSystemUserCriteria(SystemUserSearchCondition searchCondition) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isBlank(searchCondition.getUsername())) {
                predicates.add(cb.like(root.get(SystemUserEntity_.username), searchCondition.getUsername()));
            }
            if (!StringUtils.isBlank(searchCondition.getEmail())) {
                predicates.add(cb.like(root.get(SystemUserEntity_.email), searchCondition.getEmail()));
            }
            if (!StringUtils.isBlank(searchCondition.getFirstName())) {
                predicates.add(cb.like(root.get(SystemUserEntity_.firstname), searchCondition.getFirstName()));
            }
            if (!StringUtils.isBlank(searchCondition.getLastName())) {
                predicates.add(cb.like(root.get(SystemUserEntity_.lastname), searchCondition.getLastName()));
            }
            if (searchCondition.getRoleId() != null && searchCondition.getRoleId().intValue() != -1) {
                predicates.add(cb.equal(root.get(SystemUserEntity_.roleId), searchCondition.getRoleId()));
            }
            if (searchCondition.getIsDeleted() != null && searchCondition.getIsDeleted().intValue() != -1) {
                predicates.add(cb.equal(root.get(SystemUserEntity_.isDeleted), searchCondition.getIsDeleted()));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
