package com.jiang.springboot.bean;

import java.io.Serializable;

/**
 * 部门实体类
 */
public class Department implements Serializable {

    private Integer id;
    private String departmentName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Department() {
    }

    public Department(Integer id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }
}
