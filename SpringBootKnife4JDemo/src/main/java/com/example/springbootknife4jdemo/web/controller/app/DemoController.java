package com.example.springbootknife4jdemo.web.controller.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yipengup
 * @date 2021/9/27
 */
// @Api(tags = "测试工具类")
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/demo")
    // @ApiOperation("测试方法")
    public String demo() {
        return "app demo";
    }


}
