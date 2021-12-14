package com.zigzag.auction.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
    @GetMapping
    @ApiOperation(value = "Method for very small testing")
    public String index() {
        return "Hello world";
    }
}
