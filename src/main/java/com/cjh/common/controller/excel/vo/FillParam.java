package com.cjh.common.controller.excel.vo;

import com.cjh.common.controller.excel.po.EmployeeEntity;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FillParam {

    @NotNull
    private MultipartFile file;

    @NotNull
    private String template;

    @NotNull
    private Boolean firstWeekIsSingleDay;

//    @NotNull
//    private List<EmployeeEntity> dataList;

}
