package com.kingwant.videoAnaly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.michaelch.mchtools.MCHException;

@Controller
public class ErrorController {

    @RequestMapping("error404")
    public String error404(Exception exception,Model model){
    	model.addAttribute("ex",exception);
        return "error404";
    }
    @RequestMapping("error500")
    public String error500(Exception exception,Model model){
    	model.addAttribute("ex",exception.getMessage());
        return "error500";
    }
    
    @RequestMapping("errorTest")
    public String error(Exception exception,Model model) throws MCHException{
    	throw new MCHException("异常信息");
        //return "error500";
    }
}