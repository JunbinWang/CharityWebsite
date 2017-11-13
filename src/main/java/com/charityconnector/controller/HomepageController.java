package com.charityconnector.controller;

import com.charityconnector.entity.Charity;
import com.charityconnector.service.CharityService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomepageController {
    private static int FEATURED_PAGE_SIZE = 6;

    @Resource
    private CharityService charityService;

    @RequestMapping("/")
    public String getHomePage(Map<String, Object> model) {
        List<Charity> featuredCharities = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "thumbUp");
        List<Charity> res = charityService.findAll(sort);
        while (featuredCharities.size() < FEATURED_PAGE_SIZE) {
            if(res.size() >= featuredCharities.size()){
                Charity c = res.get(featuredCharities.size());
                featuredCharities.add(c);
            }
        }
        model.put("featuredCharities", featuredCharities);
        return "index";
    }
}
