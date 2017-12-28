package com.charityconnector.controller;

import com.charityconnector.entity.Activity;
import com.charityconnector.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
public class ActivityController {

    @Resource
    private ActivityService activityService;

    @RequestMapping(path = "/activities/{charityId}", method = RequestMethod.GET)
    @ResponseBody
    Activity[] getActivitiesByCharityId(@PathVariable("charityId") Long charityId) {
        return activityService.findArticlesByCharityId(charityId);
    }

    @RequestMapping(path = "/activity/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Activity getArticleById(@PathVariable("id") Long id) {
        return activityService.findById(id);
    }

    @RequestMapping(path = "/activity/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteArticleById(@PathVariable("id") Long id) {
        activityService.deleteById(id);
    }

    @RequestMapping(path = "/activity", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Activity> addArticle(@RequestBody Activity activity) {
        Activity res = activityService.addActivity(activity);
        return new ResponseEntity<Activity>(res, HttpStatus.OK);
    }

    @RequestMapping(path = "/activity", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<String> updateArticle(@RequestBody Activity activity) {
        activityService.updateSelective(activity);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(path = "/activity/volunteer", method = RequestMethod.PATCH)
    @ResponseBody
    public int volunteer(@RequestBody Activity activity, Principal principal) {
        if (principal == null) {
            return -1;
        }
        String id = principal.getName();
        System.out.println(id);
        if (id == null) {
            return -1;
        }
        return activityService.volunteer(activity.getId(), Long.valueOf(id));
    }


}