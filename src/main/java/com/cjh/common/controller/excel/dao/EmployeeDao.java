package com.cjh.common.controller.excel.dao;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjh.common.controller.excel.po.EmployeeEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {

    List<EmployeeEntity> getByLeaveTimeAndEmployeeId(DateTime excelDate, List<String> employeeIds);

}