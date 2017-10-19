package com.charityconnector.controller;

import com.charityconnector.bean.Charity;
import com.charityconnector.service.CharityService;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;




@Controller
public class CharityController {

    @Resource
    private CharityService charityService;

    @RequestMapping(path = "/charities/{name}", method = RequestMethod.GET)
    @ResponseBody
    Charity[] getCharitiesByName(@PathVariable("name") String name) {
        return charityService.findByName(name);
    }

    @RequestMapping(path = "/charity/{id}", method = RequestMethod.GET)
    @ResponseBody
    Charity getCharityById(@PathVariable("id") Long id) {
        return  charityService.findById(id);}

    @RequestMapping(path = "/charity", method = RequestMethod.POST)
    @ResponseBody
    public Charity addCharity(@RequestBody Charity charity) {
        Charity res = charityService.addCharity(charity);
        return res;
    }

    @RequestMapping(path = "/charity/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteCharityById(@PathVariable("id") Long id) {
        charityService.deleteById(id);
    }

    @RequestMapping(path = "/charity", method = RequestMethod.PATCH)
    @ResponseBody
    public void updateCharity(@RequestBody Charity charity) {
        charityService.update(charity);
    }
    
    @RequestMapping("/charityPage/{id}")
    public String getCharityPage(Map<String, Object> model, @PathVariable("id") Long id) {
	    model.put("charity", charityService.findById(id));
        return "charityPage";
    }


    // This method is used for test the upload functionality, should be delete later!!!
    @RequestMapping(path = "/uploadImage", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                String classPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
                String logoPath =classPath.replace("out/production/classes/","")+"src/main/logo-images/";
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(logoPath+file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "fail," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "faile," + e.getMessage();
            }

        } else {
            return "Update File, the File is empty.";
        }
        return ("redirect:/charityPage/1");
    }



}
