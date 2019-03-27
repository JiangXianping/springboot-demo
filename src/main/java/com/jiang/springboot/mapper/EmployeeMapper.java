package com.jiang.springboot.mapper;
import com.jiang.springboot.bean.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper{

    @Select("SELECT * FROM employee WHERE ID = #{id}")
    public Employee getEmpById(Integer id);

    @Update("UPDATE employee SET lastName = #{lastName},email=#{email},gender=#{gender},d_id=#{dId} WHERE ID = #{id}")
    public void updateEmp(Employee employee);

    @Delete("DELETE FROM employee where ID = #{id}")
    public void deleteEmployee(Integer id);

    @Insert("INSERT INTO employee(lastName,email,gender,d_id) VALUES(#{lastName},#{email},#{gender},#{dId})")
    public void insertEmployee(Employee employee);

}