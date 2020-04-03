package com.cjh.common.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjh.common.entity.ReqLog;
import com.cjh.common.service.ReqLogService;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求日志表(ReqLog)表控制层
 *
 * @author cjh
 * @since 2020-04-03 13:43:53
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/reqLog")
public class ReqLogController {

    private ReqLogService reqLogService;

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param reqLog 查询实体
     * @return 所有数据
     */
    @GetMapping("/page")
    public R selectAll(Page<ReqLog> page, ReqLog reqLog) {
        return R.ok(reqLogService.page(page, new QueryWrapper<>(reqLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public R selectOne(@PathVariable Serializable id) {
        return R.ok(reqLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param reqLog 实体对象
     * @return 新增结果
     */
    @PostMapping("/insert")
    public R insert(@RequestBody ReqLog reqLog) {
        return R.ok(reqLogService.save(reqLog));
    }

    /**
     * 修改数据
     *
     * @param reqLog 实体对象
     * @return 修改结果
     */
    @PostMapping("/update")
    public R update(@RequestBody ReqLog reqLog) {
        return R.ok(reqLogService.updateById(reqLog));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @PostMapping("delete")
    public R delete(@RequestParam("id") Serializable id) {
        return R.ok(reqLogService.removeById(id));
    }
}