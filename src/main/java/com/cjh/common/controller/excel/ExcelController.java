package com.cjh.common.controller.excel;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.R;
import com.cjh.common.controller.excel.core.Employee;
import com.cjh.common.controller.excel.core.MyExcelUtil;
import com.cjh.common.controller.excel.dao.EmployeeDao;
import com.cjh.common.controller.excel.po.EmployeeEntity;
import com.cjh.common.controller.excel.vo.FillParam;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Value("${domain}")
    private String domain;
    @Autowired
    private EmployeeDao employeeDao;

    @PostMapping("/parse")
    public R<List<EmployeeEntity>> parse(MultipartFile file) throws IOException {
        String path = "/home/excel/file/".replace("/", File.separator);
        String filePath = saveFile(path, file);
        List<Employee> list = MyExcelUtil.readEmployee(filePath);
        if (CollectionUtils.isEmpty(list)) {
            return R.failed("文件解析异常");
        }
        List<String> employeeIds = list.stream().map(Employee::getId).collect(Collectors.toList());
        List<EmployeeEntity> employeeList = employeeDao.getByLeaveTimeAndEmployeeId(null, employeeIds);
        List<Employee> newEmployee = list.stream()
            .filter(employee -> employeeList.stream().noneMatch(employeeEntity ->
                employee.getId().equals(employeeEntity.getEmployeeId())))
            .collect(Collectors.toList());
        for (Employee employee : newEmployee) {
            EmployeeEntity entity = new EmployeeEntity();
            entity.setEmployeeId(employee.getId());
            entity.setName(employee.getName());
            entity.setDepartment(employee.getDepartment());
            entity.setIsSingleDay(true);
            employeeDao.insert(entity);
        }

        DateTime excelDate = MyExcelUtil.getExcelDate(filePath);
        List<EmployeeEntity> entityList = employeeDao.getByLeaveTimeAndEmployeeId(excelDate, employeeIds);

        // 自定义部门排序顺序
        Map<String, Integer> departmentOrder = new HashMap<>();
        departmentOrder.put("主管", 0);
        departmentOrder.put("客户经理", 1);
        departmentOrder.put("按揭专员", 2);
        departmentOrder.put("销售", 3);

        // 根据部门排序
        // 根据部门排序，不在指定部门列表中的部门按字母顺序排序
        Comparator<EmployeeEntity> customComparator = Comparator.comparing((EmployeeEntity emp) -> {
            Integer order = departmentOrder.get(emp.getDepartment());
            return (order != null) ? order : 9999; // 如果部门不在列表中，默认值为 9999（可以根据需求调整）
        }).thenComparing(EmployeeEntity::getDepartment); // 按照字母顺序排序

        // 对员工列表进行排序
        entityList.sort(customComparator);

        return R.ok(entityList);
    }

    @PostMapping("/employee/update")
    public R<EmployeeEntity> update(@RequestBody EmployeeEntity param) {
        QueryWrapper<EmployeeEntity> query = Wrappers.query(new EmployeeEntity(param.getEmployeeId()));
        EmployeeEntity entity = employeeDao.selectOne(query);
        if (entity != null) {
            entity.setName(param.getName());
            entity.setDepartment(param.getDepartment());
            entity.setIsSingleDay(param.getIsSingleDay());
            entity.setJoinTime(param.getJoinTime());
            entity.setLeaveTime(param.getLeaveTime());
            employeeDao.updateById(entity);
        }
        return R.ok(entity);
    }

    @PostMapping("/fill")
    public R<String> fill(@Valid FillParam param) throws IOException {
        String templatePath = "/home/excel/template/".replace("/", File.separator);

        String path = "/home/excel/file/".replace("/", File.separator);
        String filePath = saveFile(path, param.getFile());

        List<Employee> list = MyExcelUtil.readEmployee(filePath);
        List<String> employeeIds = list.stream().map(Employee::getId).collect(Collectors.toList());
        DateTime excelDate = MyExcelUtil.getExcelDate(filePath);
        List<EmployeeEntity> entityList = employeeDao.getByLeaveTimeAndEmployeeId(excelDate, employeeIds);

        String outPath = MyExcelUtil.fill(
            filePath,
            templatePath + param.getTemplate(),
            param.getFirstWeekIsSingleDay(),
            entityList
        );
        return R.ok(domain + outPath);
    }

    private static String saveFile(String path, MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        File file = new File(path + fileName);

        //save template data
        FileUtil.writeFromStream(multipartFile.getInputStream(), file);
        return file.getAbsolutePath();
    }

    @PostMapping("/template/upload")
    public R<String> templateUpload(MultipartFile file) throws IOException {
        String path = "/home/excel/template/".replace("/", File.separator);
        String filePath = saveFile(path, file);
        return R.ok(filePath);
    }

    @GetMapping("/template/list")
    public R<List<String>> templateList() {
        String path = "/home/excel/template/".replace("/", File.separator);
        File filePath = new File(path);
        File[] files = filePath.listFiles();
        List<String> list = Lists.newArrayList();
        if (files != null && files.length > 0) {
            list = Arrays.stream(files).map(File::getName).sorted().collect(Collectors.toList());
        }
        return R.ok(list);
    }

}
