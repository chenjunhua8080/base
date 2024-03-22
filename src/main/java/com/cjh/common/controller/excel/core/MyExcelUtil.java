package com.cjh.common.controller.excel.core;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Week;
import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;
import com.cjh.common.conifg.GlobalErrorCodeConstants;
import com.cjh.common.conifg.ServiceException;
import com.cjh.common.controller.excel.core.EmployeeClock.ClockByDay;
import com.cjh.common.controller.excel.po.EmployeeEntity;
import com.google.common.collect.Lists;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Slf4j
public class MyExcelUtil {

    private static final List<Holiday> HOLIDAY_LIST = new ArrayList<>();

    static {
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.YUANDAN, 12, 30));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.YUANDAN, 12, 31));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.YUANDAN, 1, 1));

        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 10));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 11));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 12));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 13));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 14));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 15));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 16));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 17));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 4, true));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.CHUNJIE, 2, 18, true));

        HOLIDAY_LIST.add(new Holiday(HolidayConstant.QINGMING, 4, 4));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.QINGMING, 4, 5));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.QINGMING, 4, 6));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.QINGMING, 4, 7, true));

        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 5, 1));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 5, 2));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 5, 3));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 5, 4));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 5, 5));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 4, 28, true));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.LAODONG, 5, 11, true));

        HOLIDAY_LIST.add(new Holiday(HolidayConstant.DUANWU, 6, 8));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.DUANWU, 6, 9));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.DUANWU, 6, 10));

        HOLIDAY_LIST.add(new Holiday(HolidayConstant.ZHONGQIU, 9, 15));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.ZHONGQIU, 9, 16));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.ZHONGQIU, 9, 17));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.ZHONGQIU, 9, 14, true));

        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 1));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 2));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 3));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 4));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 5));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 6));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 7));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 9, 29, true));
        HOLIDAY_LIST.add(new Holiday(HolidayConstant.GUOQING, 10, 12, true));
    }

    public static String fill(String dataFilePath, String templatePath, boolean firstWeekIsSingleDay,
        List<EmployeeEntity> dataList) {
        // Read excel
        ExcelReader reader = ExcelUtil.getReader(dataFilePath);

        DateTime dateTime = getExcelDate(reader);
        Integer month = dateTime.monthStartFromOne();
        log.info("Excel month: {}", month);

//        // Read employee
//        List<Employee> employeeList = readEmployee(reader);
//        print(employeeList);

        // Read employee clock
        List<EmployeeClock> employeeClockList = readEmployeeClock(reader, dataList, dateTime);
        print(employeeClockList);

        reader.close();

        // 自定义部门排序顺序
        Map<String, Integer> departmentOrder = new HashMap<>();
        departmentOrder.put("主管", 0);
        departmentOrder.put("客户经理", 1);
        departmentOrder.put("按揭专员", 2);
        departmentOrder.put("销售", 3);

        // 根据部门排序
        // 根据部门排序，不在指定部门列表中的部门按字母顺序排序
        Comparator<EmployeeClock> customComparator = Comparator.comparing((EmployeeClock emp) -> {
            if (emp.getEmployee() == null) {
                return 9999;
            }
            Integer order = departmentOrder.get(emp.getEmployee().getDepartment());
            return (order != null) ? order : 9999; // 如果部门不在列表中，默认值为 9999（可以根据需求调整）
        }).thenComparing((EmployeeClock emp) -> {
            if (emp.getEmployee() == null) {
                return "A";
            }
            return emp.getEmployee().getName();
        }); // 按照字母顺序排序

        // 对员工列表进行排序
        employeeClockList.sort(customComparator);

        List<DayOfMonth> dayList = getDayByMonth(firstWeekIsSingleDay, month);
        print(dayList);

        // 输出文件名
        String path = "/home/excel/out/".replace("/", File.separator);
        FileUtil.mkdir(path);
        String fileName = String.format("%s年%s月考勤表.xlsx", dateTime.year(), dateTime.monthStartFromOne());
        String outPath = path + fileName;

        // Write excel
        // 用 easyexcel 填充模板
        fillData(outPath, templatePath, dayList, employeeClockList, dateTime);

        // 用 hutool 修改单元格
        addComment(outPath, dayList);

        changeWuKaStyle(outPath);
        changeRStyle(outPath);
        changeFStyle(outPath);
        changeHolidayStyle(outPath);
        changeDayAndWeekStyle(outPath, dayList);

        hideDay(outPath, dayList);
        renameSheet(outPath, dateTime);

        return outPath;
    }

    public static DateTime getExcelDate(String dataFilePath) {
        // Read excel
        ExcelReader reader = ExcelUtil.getReader(dataFilePath);
        DateTime excelDate = getExcelDate(reader);
        reader.close();
        return excelDate;
    }

    private static DateTime getExcelDate(ExcelReader reader) {
        // Read head info
        List<String> infoList = readHeadInfo(reader);
        if (infoList.isEmpty()){
            throw new ServiceException(GlobalErrorCodeConstants.FILE_INVALID);
        }
        print(infoList);

        // Get the Excel dateTime
        Pattern pattern = Pattern.compile(".*?：(\\d{4}\\.\\d{2}\\.\\d{2}).*?");
        Matcher matcher = pattern.matcher(infoList.get(0));
        if (matcher.find()) {
            String date = matcher.group(1);
            return DateUtil.parseDate(date);
        }
        return new DateTime();
    }

    private static void renameSheet(String templatePath, DateTime dateTime) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        String sheetName = String.format("%s月", dateTime.monthStartFromOne());
        writer.renameSheet(sheetName);

        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static void hideDay(String templatePath, List<DayOfMonth> dayList) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        // 删除某列
        int monthDay = dayList.size();
        int maxDay = 30;
        if (monthDay != maxDay) {
            int columnIndex = 3 + monthDay;
            for (int k = 0; k <= maxDay - monthDay; k++) {
                for (int i = 0; i < writer.getRowCount(); i++) {
                    Row row = writer.getOrCreateRow(i);
                    Cell cellToRemove = row.getCell(columnIndex);
                    if (cellToRemove != null) {
                        row.removeCell(cellToRemove);
                    }
                }
                writer.getSheet().setColumnHidden(columnIndex + k, true);
            }
        }

        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static List<DayOfMonth> getDayByMonth(boolean singleDay, Integer month) {
        List<DayOfMonth> list = Lists.newArrayList();

        DateTime today;
        if (month != null) {
            today = DateUtil.parseDate(String.format("%s-%s-01", DateUtil.thisYear(), month));
        } else {
            today = DateUtil.date();
        }
        DateTime startOfMonth = DateUtil.beginOfMonth(today);
        DateTime endOfMonth = DateUtil.endOfMonth(today);
        DateRange thisDayOfMonth = DateUtil.range(startOfMonth, endOfMonth, DateField.DAY_OF_MONTH);

        Map<Integer, Map<Integer, Holiday>> holidayMonthMap = HOLIDAY_LIST.stream()
            .collect(Collectors.groupingBy(Holiday::getMonth, Collectors.toMap(Holiday::getDay, holiday -> holiday)));

        List<DateTime> singleList = Lists.newArrayList();
        for (DateTime dateTime : thisDayOfMonth) {
            Week week = dateTime.dayOfWeekEnum();
            if (week.equals(Week.SUNDAY)) {
                if (singleDay) {
                    singleList.add(dateTime);
                    singleDay = false;
                } else {
                    singleDay = true;
                }
            }
        }
        thisDayOfMonth.reset();

        for (DateTime dateTime : thisDayOfMonth) {
            DayOfMonth dayOfMonth = new DayOfMonth();
            int thisMonth = dateTime.monthStartFromOne();
            int thisDay = dateTime.dayOfMonth();

            dayOfMonth.setMonth(thisMonth);
            dayOfMonth.setDay(thisDay);
            dayOfMonth.setWeek(dateTime.dayOfWeekEnum().toChinese().replace("星期", ""));
            dayOfMonth.setWeekend(dateTime.isWeekend());

            if (singleList.contains(dateTime)) {
                dayOfMonth.setSingleDay(true);
            }

            Map<Integer, Holiday> holidayDayMap = holidayMonthMap.get(thisMonth);
            if (holidayDayMap != null) {
                Holiday holiday = holidayDayMap.get(thisDay);
                if (holiday != null) {
                    dayOfMonth.setHoliday(holiday);
                }
            }

            list.add(dayOfMonth);
        }
        return list;
    }

    private static void changeWuKaStyle(String templatePath) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        int columnCount = writer.getColumnCount();
        int rowCount = writer.getRowCount();

        // 创建新样式
        writer.getWorkbook().createCellStyle();

        // 修改 无卡 的颜色
        for (int y = 3; y < rowCount; y++) {
            for (int x = 3; x < columnCount; x++) {
                Cell cell = writer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (!CellType.STRING.equals(cell.getCellType())) {
                    continue;
                }
                String value = cell.getStringCellValue();
                if ("无卡".equals(value)) {
                    // 获取当前样式
                    StyleSet style = writer.getStyleSet();

                    // 设置背景颜色
//                    style.setBackgroundColor(IndexedColors.LIGHT_YELLOW, false);

                    //设置字体
                    Font font = writer.createFont();
                    font.setFontName("宋体");
                    font.setBold(false);
                    font.setColor(Font.COLOR_NORMAL);
                    font.setItalic(false);
                    style.setFont(font, true);

                    // 必须搭配写值，否则全部变红
                    cell.setCellValue(value);
                    writer.writeCellValue(x, y, cell.getStringCellValue());
                }
            }
        }

        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static void changeFStyle(String templatePath) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        int columnCount = writer.getColumnCount();
        int rowCount = writer.getRowCount();

        // 创建新样式
        writer.getWorkbook().createCellStyle();

        // 修改 F 的颜色
        for (int y = 3; y < rowCount; y++) {
            for (int x = 3; x < columnCount; x++) {
                Cell cell = writer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (!CellType.STRING.equals(cell.getCellType())) {
                    continue;
                }
                String value = cell.getStringCellValue();
                if ("F".equalsIgnoreCase(value)) {
                    // 获取当前样式
                    StyleSet style = writer.getStyleSet();

                    //设置字体
                    Font font = writer.createFont();
                    font.setFontName("宋体");
                    font.setBold(true);
                    font.setColor(Font.COLOR_NORMAL);
                    font.setItalic(false);
                    style.setFont(font, true);

                    // 必须搭配写值，否则全部变红
                    cell.setCellValue(value);
                    writer.writeCellValue(x, y, cell.getStringCellValue());
                }
            }
        }

        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static void changeHolidayStyle(String templatePath) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        int columnCount = writer.getColumnCount();
        int rowCount = writer.getRowCount();

        List<String> holidayNameList = HOLIDAY_LIST.stream().map(Holiday::getName).collect(Collectors.toList());

        // 创建新样式
        writer.getWorkbook().createCellStyle();

        // 修改 节假日 的颜色
        for (int y = 3; y < rowCount; y++) {
            for (int x = 3; x < columnCount; x++) {
                Cell cell = writer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (!CellType.STRING.equals(cell.getCellType())) {
                    continue;
                }
                String value = cell.getStringCellValue();
                if (holidayNameList.contains(value)) {
                    // 必须搭配写值，否则全部变红
                    cell.setCellValue(value);
                    writer.writeCellValue(x, y, cell.getStringCellValue());

                    CellStyle cellStyle = cell.getCellStyle();

                    // 获取字体对象
                    Workbook workbook = cell.getSheet().getWorkbook();
                    Font font = workbook.getFontAt(cellStyle.getFontIndexAsInt());//有问题，一直都是0
                    // 设置字体颜色
                    font.setColor(IndexedColors.RED.getIndex()); // 这里将字体颜色设置为红色，你可以根据需要修改颜色
                    // 设置粗体
                    font.setBold(false);
                }
            }
        }

        // 保存修改后的Excel文件
        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static void changeDayAndWeekStyle(String templatePath, List<DayOfMonth> dayList) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        // 创建新样式
        writer.getWorkbook().createCellStyle();

        // 修改大小周日期的颜色，添加单休批注
        for (int x = 3; x < dayList.size() + 3; x++) {
            int y = 2;
            Cell cell = writer.getCell(x, y);
            if (cell == null) {
                continue;
            }
            if (!CellType.NUMERIC.equals(cell.getCellType())) {
                continue;
            }
            int value = (int) cell.getNumericCellValue();
            Optional<DayOfMonth> optional = dayList.stream()
                .filter(dayOfMonth -> dayOfMonth.getDay() == value)
                .findFirst();
            if (optional.isPresent()) {
                DayOfMonth dayOfMonth = optional.get();
                if (dayOfMonth.isWeekend() && !dayOfMonth.isSingleDay()) {
                    // ================================= 修改日期=================================
                    StyleSet style = writer.getStyleSet();
                    // 设置背景颜色
                    style.setBackgroundColor(IndexedColors.GREY_25_PERCENT, false);

                    //设置字体
                    Font font = writer.createFont();
                    font.setFontName("宋体");
                    font.setBold(true);
                    font.setColor(Font.COLOR_RED);
                    font.setItalic(false);
                    style.setFont(font, true);

                    // 必须搭配写值，否则全部变红
                    cell.setCellValue(value);
                    writer.writeCellValue(x, y, value);

                    // ================================= 修改星期 =================================
                    y = 1;
                    // 必须搭配写值，否则全部变红
                    cell = writer.getCell(x, y);
                    String value2 = cell.getStringCellValue();
                    cell.setCellValue(value2);
                    writer.writeCellValue(x, y, value2);

                    // 获取当前样式
                    style = writer.getStyleSet();

                    // 设置背景颜色
                    style.setBackgroundColor(IndexedColors.GREY_25_PERCENT, false);

                    //设置字体
                    font = writer.createFont();
                    font.setFontName("宋体");
                    font.setBold(true);
                    font.setColor(Font.COLOR_RED);
                    font.setItalic(false);
                    style.setFont(font, true);

                    // 必须搭配写值，否则全部变红
                    cell.setCellValue(value2);
                    writer.writeCellValue(x, y, value2);
                }
            }
        }

        // 保存修改后的Excel文件
        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static void addComment(String templatePath, List<DayOfMonth> dayList) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        int columnCount = writer.getColumnCount();
        int rowCount = writer.getRowCount();

        // 修改备注列: 内容 -> 批注
        for (int y = 3; y < rowCount; y++) {
            int x = columnCount - 1;
            Cell cell = writer.getCell(x, y);
            if (cell == null) {
                continue;
            }
            String value = cell.getStringCellValue();
            if (StringUtils.hasText(value)) {
                addCellComment(cell, value, true);
//                cell.setCellValue("");
                writer.writeCellValue(x, y, "");
            }
        }

        // 入职，离职备注
        for (int y = 3; y < rowCount; y++) {
            for (int x = 3; x < columnCount; x++) {
                Cell cell = writer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (!CellType.STRING.equals(cell.getCellType())) {
                    continue;
                }
                String value = cell.getStringCellValue();
                if ("入职".equals(value) || "离职".equals(value)) {
//                cell.setCellValue("");
                    writer.writeCellValue(x, y, "F");
                    addCellComment(cell, value, true);
                }
            }
        }

        // 添加单休批注
        for (int x = 3; x < dayList.size() + 3; x++) {
            int y = 2;
            Cell cell = writer.getCell(x, y);
            if (cell == null) {
                continue;
            }
            if (!CellType.NUMERIC.equals(cell.getCellType())) {
                continue;
            }
            int value = (int) cell.getNumericCellValue();
            Optional<DayOfMonth> optional = dayList.stream()
                .filter(dayOfMonth -> dayOfMonth.getDay() == value)
                .findFirst();
            if (optional.isPresent()) {
                DayOfMonth dayOfMonth = optional.get();

                // ================================= 添加‘补班’批注 =================================
                Optional<Holiday> holiday = HOLIDAY_LIST.stream()
                    .filter(item -> item.isOvertime()
                        && item.getMonth().equals(dayOfMonth.getMonth())
                        && item.getDay().equals(dayOfMonth.getDay())
                    )
                    .findFirst();
                if (holiday.isPresent()) {
                    addCellComment(cell, holiday.get().getName() + "补班", true);
                }

                // ================================= 添加‘单休’批注 =================================
                if (dayOfMonth.isWeekend() && dayOfMonth.isSingleDay()) {
                    y = 1;
                    cell = writer.getCell(x, y);
                    addCellComment(cell, "单休", true);
                }
            }
        }

        // 保存修改后的Excel文件
        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    private static void changeRStyle(String templatePath) {
        // 读取 Excel 文件
        cn.hutool.poi.excel.ExcelWriter writer = ExcelUtil.getReader(templatePath).getWriter();

        int columnCount = writer.getColumnCount();
        int rowCount = writer.getRowCount();

        // 创建新样式
        writer.getWorkbook().createCellStyle();

        // 修改 F 的颜色
        for (int y = 3; y < rowCount; y++) {
            for (int x = 3; x < columnCount; x++) {
                Cell cell = writer.getCell(x, y);
                if (cell == null) {
                    continue;
                }
                if (!CellType.STRING.equals(cell.getCellType())) {
                    continue;
                }
                String value = cell.getStringCellValue();
                if ("R".equalsIgnoreCase(value)) {
                    // 获取当前样式
                    StyleSet style = writer.getStyleSet();

                    //设置字体
                    Font font = writer.createFont();
                    font.setFontName("宋体");
                    font.setBold(true);
                    font.setColor(Font.COLOR_RED);
                    font.setItalic(false);
                    style.setFont(font, true);

                    // 必须搭配写值，否则全部变红
                    cell.setCellValue(value);
                    writer.writeCellValue(x, y, cell.getStringCellValue());
                }
            }
        }

        writer.setDestFile(new File(templatePath));
        writer.flush();
        writer.close();
    }

    /**
     * 给Cell添加批注
     *
     * @param cell   单元格
     * @param value  批注内容
     * @param isXlsx 是否是xlsx格式的文档
     */
    private static void addCellComment(Cell cell, String value, boolean isXlsx) {
        Sheet sheet = cell.getSheet();
        cell.removeCellComment();
        Drawing<?> drawing = sheet.createDrawingPatriarch();
        Comment comment;
        if (isXlsx) {
            // 创建批注
            comment = drawing.createCellComment(new XSSFClientAnchor(1, 1, 1, 1, 1, 1, 1, 1));
            // 输入批注信息
            comment.setString(new XSSFRichTextString(value));
            // 将批注添加到单元格对象中
        } else {
            // 创建批注
            comment = drawing.createCellComment(new HSSFClientAnchor(1, 1, 1, 1, (short) 1, 1, (short) 1, 1));
            // 输入批注信息
            comment.setString(new HSSFRichTextString(value));
            // 将批注添加到单元格对象中
        }
        cell.setCellComment(comment);
    }

    /**
     * 模板注意 用{} 来表示你要用的变量 如果本来就有"{","}" 特殊字符 用"\{","\}"代替
     * {} 代表普通变量 {.} 代表是list的变量
     */
    private static void fillData(String outPath, String templatePath, List<DayOfMonth> dayList,
        List<EmployeeClock> employeeClockList, DateTime dateTime) {
        List<Map<String, Object>> dayMapList = Lists.newArrayList();
        for (int i = 0; i < dayList.size(); i++) {
            DayOfMonth dayOfMonth = dayList.get(i);
            HashMap<String, Object> map = new HashMap<>();
            int index = i + 1;
            map.put("day" + index, dayOfMonth.getDay());
            map.put("week" + index, dayOfMonth.getWeek());
            dayMapList.add(map);
        }

        List<Map<String, Object>> rowsMapList = Lists.newArrayList();
        for (int i = 0; i < employeeClockList.size(); i++) {
            EmployeeClock employeeClock = employeeClockList.get(i);
            Employee employee = employeeClock.getEmployee();
            List<ClockByDay> clockList = employeeClock.getClockList();

            HashMap<String, Object> map = new HashMap<>();
            int index = i + 1;
            map.put("index", index);
            map.put("department", employee.getDepartment());
            map.put("name", employee.getName());
            map.put("blank", "");

            int count = 0;
            String remark = "";
            List<String> missingOn = Lists.newArrayList();
            List<String> missingOff = Lists.newArrayList();

            for (int j = 0, clockListSize = clockList.size(); j < clockListSize; j++) {
                int clockIndex = j + 1;
                String clockKey = "clock" + clockIndex;
                String clockValue = "无卡";
                ClockByDay clockByDay = clockList.get(j);
                if (clockByDay != null) {
                    Clock clock = clockByDay.getClock();
                    if (clock != null) {
                        if (!StringUtils.hasText(clock.getOn()) && !StringUtils.hasText(clock.getOff())) {
                            clockValue = clock.getRaw();
                            if ("入职".equals(clock.getRaw()) || "离职".equals(clock.getRaw())) {
                                clockValue = clock.getRaw();
                                count++;
                            }
                        } else if (!StringUtils.hasText(clock.getOn())) {
                            missingOn.add(clockByDay.getDayOfMonth().getDay().toString());
                        } else if (!StringUtils.hasText(clock.getOff())) {
                            missingOff.add(clockByDay.getDayOfMonth().getDay().toString());
                        }
                        if (StringUtils.hasText(clock.getOn()) || StringUtils.hasText(clock.getOff())) {
//                            clockValue = clock.getRaw();
                            clockValue = "F";
                            count++;
                        }
                    } else {
                        DayOfMonth dayOfMonth = clockByDay.getDayOfMonth();
                        if (dayOfMonth != null) {
                            Optional<DayOfMonth> optional = dayList.stream()
                                .filter(item -> item.getMonth().equals(dayOfMonth.getMonth()))
                                .filter(item -> item.getDay().equals(dayOfMonth.getDay())).findFirst();
                            if (optional.isPresent()) {
                                DayOfMonth date = optional.get();
                                if (date.getHoliday() != null) {
                                    Holiday holiday = date.getHoliday();
                                    if (holiday.isOvertime()) {
//                                        clockValue = "补班";
//                                        clockValue = "补班";
                                    } else {
                                        clockValue = holiday.getName();
                                    }
                                } else if (Week.SATURDAY.toChinese().replace("星期", "").equals(date.getWeek())) {
                                    clockValue = "R";
                                } else if (Week.SUNDAY.toChinese().replace("星期", "").equals(date.getWeek())
                                    && !date.isSingleDay()) {
                                    clockValue = "R";
                                }
                            } else {
                                //29,30,31
                                clockValue = "";
                            }
                        }
                    }
                }
                map.put(clockKey, clockValue);
            }
            if (!missingOn.isEmpty()) {
                remark += String.join(",", missingOn) + "上班没打卡\n";
            }
            if (!missingOff.isEmpty()) {
                remark += String.join(",", missingOff) + "下班没打卡\n";
            }

            map.put("count", count == 0 ? "" : count);
            map.put("remark", remark);
            rowsMapList.add(map);
        }

        TextObject textObject = new TextObject();
        String title = String.format("%s广州农信%s月考勤表", dateTime.year(), dateTime.monthStartFromOne());
        textObject.setTitle(title);

        // 方案1
        try (ExcelWriter excelWriter = EasyExcel.write(outPath).withTemplate(templatePath).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet().build();

            FillConfig fillConfig = FillConfig.builder()
                .direction(WriteDirectionEnum.HORIZONTAL)
                .forceNewRow(true)
                .build();

            excelWriter.fill((new FillWrapper("date", dayMapList)), fillConfig, writeSheet);
            excelWriter.fill((new FillWrapper("user", rowsMapList)), writeSheet);
            excelWriter.fill((new FillWrapper("text", Collections.singleton(textObject))), writeSheet);
        }
    }

    private static void print(List<?> list) {
        for (Object object : list) {
            log.info("{}", object);
        }
    }

    private static List<EmployeeClock> readEmployeeClock(ExcelReader reader, List<EmployeeEntity> dataList,
        DateTime dateTime) {
        List<EmployeeClock> list = Lists.newArrayList();
        for (EmployeeEntity employee : dataList) {
            EmployeeClock employeeClock = parseEmployeeClock(employee, reader, dateTime);
            if (employeeClock != null) {
                list.add(employeeClock);
            }
        }
        return list;
    }

    private static EmployeeClock parseEmployeeClock(EmployeeEntity entity, ExcelReader reader, DateTime dateTime) {
        EmployeeClock employeeClock = new EmployeeClock();

        Employee employee = new Employee();
        employee.setId(entity.getEmployeeId());
        employee.setName(entity.getName());
        employee.setDepartment(entity.getDepartment());
        employeeClock.setEmployee(employee);

        List<ClockByDay> clockList = Lists.newArrayList();

        reader.setIgnoreEmptyRow(false);
        int rowCount = reader.getRowCount();
        List<List<Object>> dataRows = Lists.newArrayList();
        for (int startRowIndex = 4; startRowIndex < rowCount; startRowIndex++) {
            dataRows = reader.read(startRowIndex, Math.min(startRowIndex + 3, rowCount));

            List<Object> row1 = dataRows.get(0);
            Employee thisEmployee = parseEmployee(row1);
            if (thisEmployee != null && entity.getEmployeeId().equals(thisEmployee.getId())) {
                break;
            }
        }

        if (CollectionUtils.isEmpty(dataRows)) {
            return null;
        }

        boolean isActive = false;

        List<Object> row2 = dataRows.get(1);
        List<Object> row3 = dataRows.get(2);
        for (int i = 0, row2Size = row2.size(); i < row2Size; i++) {
            Object date = row2.get(i);
            if (date == null) {
                continue;
            }
            ClockByDay clockByDay = new ClockByDay();
            DayOfMonth dayOfMonth = new DayOfMonth();
            if (StringUtils.hasText(date.toString())) {
                dayOfMonth.setDay(Integer.parseInt(date.toString()));
                dayOfMonth.setMonth(dateTime.monthStartFromOne());
            }
            clockByDay.setDayOfMonth(dayOfMonth);

            boolean isSpecialDate = false;
            if (entity != null && dayOfMonth.getDay() != null) {
                // 入职前的数据
                Date joinTime = entity.getJoinTime();
                if (joinTime != null) {
                    dateTime.setDate(dayOfMonth.getDay());
                    if (dateTime.before(joinTime)) {
                        Clock clock = new Clock();
                        clock.setRaw("/");
                        clockByDay.setClock(clock);
//                        isActive = true;
                        isSpecialDate = true;
                    } else if (DateUtil.isSameDay(dateTime, joinTime)) {
                        Clock clock = new Clock();
                        clock.setRaw("入职");
                        clockByDay.setClock(clock);
                        isActive = true;
                        isSpecialDate = true;
                    }
                }
                // 离职后的数据
                Date leaveTime = entity.getLeaveTime();
                if (leaveTime != null) {
                    dateTime.setDate(dayOfMonth.getDay());
                    if (dateTime.after(leaveTime)) {
                        Clock clock = new Clock();
                        clock.setRaw("/");
                        clockByDay.setClock(clock);
//                        isActive = true;
                        isSpecialDate = true;
                    } else if (DateUtil.isSameDay(dateTime, leaveTime)) {
                        Clock clock = new Clock();
                        clock.setRaw("离职");
                        clockByDay.setClock(clock);
                        isActive = true;
                        isSpecialDate = true;
                    }
                }
            }

            if (!isSpecialDate && !row3.isEmpty()) {
                Object clockTime = row3.get(i);
                if (clockTime != null && StringUtils.hasText(clockTime.toString())) {
                    Clock clock = new Clock();
                    String str = clockTime.toString();
                    clock.setRaw(str);
                    String[] list = str.split("\n");
                    clock.setList(Arrays.asList(list));
                    clock.setOn(list[0]);
                    clock.setOff(list[list.length - 1]);
                    clockByDay.setClock(clock);

                    isActive = true;
                }
            }
            clockList.add(clockByDay);
        }
        if (!isActive) {
            return null;
        }

        employeeClock.setClockList(clockList);
        return employeeClock;
    }

    public static List<Employee> readEmployee(String dataFilePath) {
        // Read excel
        ExcelReader reader = ExcelUtil.getReader(dataFilePath);
        List<Employee> employeeList = readEmployee(reader);

        DateTime dateTime = getExcelDate(reader);

        List<EmployeeEntity> dataList = Lists.newArrayList();
        for (Employee employee : employeeList) {
            EmployeeEntity entity = new EmployeeEntity();
            entity.setEmployeeId(employee.getId());
            entity.setName(employee.getName());
            entity.setDepartment(employee.getDepartment());
            dataList.add(entity);
        }

        // Read employee clock
        List<EmployeeClock> employeeClockList = readEmployeeClock(reader, dataList, dateTime);
        print(employeeClockList);

        reader.close();
        return employeeClockList.stream().map(EmployeeClock::getEmployee).collect(Collectors.toList());
    }

    private static List<Employee> readEmployee(ExcelReader reader) {
        List<Employee> employeeList = Lists.newArrayList();
        List<List<Object>> dataRows = reader.read(4);
        for (List<Object> row : dataRows) {
            Employee employee = parseEmployee(row);
            if (employee == null) {
                continue;
            }
            employeeList.add(employee);
        }
        return employeeList;
    }

    private static final Pattern PATTERN = Pattern.compile("工号：, (\\d+).*?,.*?姓名：, (.*?),.*?部门：, (.*?),.*");

    private static Employee parseEmployee(List<Object> row) {
        String rowString = row.toString();
        Matcher matcher = PATTERN.matcher(rowString);
        if (matcher.find()) {
            String id = matcher.group(1);
            String name = matcher.group(2);
            String department = matcher.group(3);

            if (StringUtils.isEmpty(name)) {
//                return null;
            }

            Employee employee = new Employee();
            employee.setId(id);
            employee.setName(name);
            employee.setDepartment(department);

            return employee;
        }
        return null;
    }

    private static List<String> readHeadInfo(ExcelReader reader) {
        List<String> infoList = Lists.newArrayList();
        List<List<Object>> dataRows = reader.read(0, 3);
        Pattern pattern = Pattern.compile("(.*), (.*?：\\d{4}\\.\\d{2}\\.\\d{2}.*?),");
        for (List<Object> row : dataRows) {
            String rowString = row.toString();
            Matcher matcher = pattern.matcher(rowString);
            if (matcher.find()) {
                String info = matcher.group(2);
                infoList.add(info);
            }
        }
        return infoList;
    }
}
