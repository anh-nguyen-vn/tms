package com.charter.vietnam.tms.model;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class BaseModelAndView extends ModelAndView {

    private String successMessage;
    private List<String> listErrorMessage;

    // Pagination info
    private Pagination pagination;

    public BaseModelAndView() {
        super();
    }

    public BaseModelAndView(String viewName) {
        super(viewName);
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public List<String> getListErrorMessage() {
        return listErrorMessage;
    }

    public void setListErrorMessage(List<String> listErrorMessage) {
        this.listErrorMessage = listErrorMessage;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
