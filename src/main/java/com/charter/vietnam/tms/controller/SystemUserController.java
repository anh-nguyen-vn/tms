package com.charter.vietnam.tms.controller;

import com.charter.vietnam.tms.layout.Layout;
import com.charter.vietnam.tms.model.BaseModelAndView;
import com.charter.vietnam.tms.model.Pagination;
import com.charter.vietnam.tms.model.SystemUserModel;
import com.charter.vietnam.tms.model.UserRoleModel;
import com.charter.vietnam.tms.model.condition.SystemUserSearchCondition;
import com.charter.vietnam.tms.repository.entity.SystemUserEntity;
import com.charter.vietnam.tms.repository.entity.UserRoleEntity;
import com.charter.vietnam.tms.service.SystemUserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SystemUserController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private SystemUserService systemUserService;

    @RequestMapping(value = "/list-system-user")
    @Layout("layout/default")
    public ModelAndView searchSystemUser(@ModelAttribute("searchCriteria")SystemUserSearchCondition searchCondition,
                                         @ModelAttribute("pagination")Pagination pagination) {
        BaseModelAndView modelAndView = new BaseModelAndView();

        // search system user by condition
        Page<SystemUserEntity> systemUserEntityList = systemUserService.searchSystemUserByCondition(searchCondition, pagination);
        List<SystemUserModel> systemUserModelList = systemUserEntityList.getContent().stream()
                .map(this::convertToSystemUserModel)
                .collect(Collectors.toList());
        modelAndView.setViewName("systemUser/list-system-user");
        modelAndView.addObject("systemUserList", systemUserModelList);
        modelAndView.setPagination(pagination);

        // get list all user role
        List<UserRoleEntity> listAllUserRole = systemUserService.getAllUserRole();
        List<UserRoleModel> userRoleModelList = listAllUserRole.stream()
                .map(this::convertToUserRoleModel)
                .collect(Collectors.toList());
        modelAndView.addObject("userRoleList", userRoleModelList);

        return modelAndView;
    }

    @RequestMapping(value = "/init-system-user")
    @Layout("layout/default")
    public String initCreateSystemUser(Model model) {
        model.addAttribute("systemUser", new SystemUserModel());

        List<UserRoleEntity> listAllUserRole = systemUserService.getAllUserRole();
        List<UserRoleModel> userRoleModelList = listAllUserRole.stream()
                .map(this::convertToUserRoleModel).collect(Collectors.toList());
        model.addAttribute("userRoleList", userRoleModelList);

        return "systemUser/create-system-user";
    }

    @RequestMapping(value = "/create-system-user")
    @Layout("layout/default")
    public String createSystemUser(@ModelAttribute("systemUser") SystemUserModel systemUser) {
        LOGGER.info("========== Start to create system user ==========");
        systemUserService.createSystemUser(systemUser);
        LOGGER.info("========== End to create system user ==========");

        return "systemUser/create-system-user";
    }

    private SystemUserModel convertToSystemUserModel(SystemUserEntity entity) {
        SystemUserModel systemUserModel = new SystemUserModel();
        systemUserModel.setId(entity.getId());
        systemUserModel.setUsername(entity.getUsername());
        systemUserModel.setEmail(entity.getEmail());
        systemUserModel.setFirstName(entity.getFirstname());
        systemUserModel.setLastName(entity.getLastname());
        systemUserModel.setRoleId(entity.getRoleId());
        systemUserModel.setRemark(entity.getRemark());
        systemUserModel.setIsDeleted(entity.getDeleted());
        systemUserModel.setCreatedTimestamp(entity.getCreatedTimestamp());
        systemUserModel.setLastUpdatedTimestamp(entity.getLastUpdatedTimestamp());

        return systemUserModel;
    }

    private UserRoleModel convertToUserRoleModel(UserRoleEntity entity) {
        UserRoleModel userRoleModel = new UserRoleModel();
        userRoleModel.setId(entity.getId());
        userRoleModel.setName(entity.getName());

        return userRoleModel;
    }
}
