package com.flycode.web;


import com.flycode.service.SenderService;
import com.flycode.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;


/**
 *
 * @author FLY
 * @version 1.0.0
 * @blog http://uniontech.top
 *
 */
@Controller
@RequestMapping("/sms")
public class SmsSenderController {
    private final static Logger logger = LoggerFactory.getLogger(SmsSenderController.class);

    @Autowired
    SenderService senderService;
    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap map) {
        return "index";
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request, ModelMap map) {
        String flag = StringUtil.nvl(request.getParameter("flag"));
        SenderService.test=Boolean.valueOf(flag);
        return "index";
    }

    @RequestMapping("/send")
    public String send(HttpServletRequest request, ModelMap map) throws Exception {
        String result=senderService.send(request);
        map.addAttribute("info", result);
        return "result";
    }

}