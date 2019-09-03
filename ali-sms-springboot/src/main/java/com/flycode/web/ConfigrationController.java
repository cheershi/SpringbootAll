package com.flycode.web;



import com.flycode.config.ReadConfigration;

import com.flycode.config.WriteConfigration;
import com.flycode.entity.Access;
import com.flycode.entity.Project;
import com.flycode.entity.Template;
import com.flycode.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author FLY
 * @version 1.0.0
 * @blog http://uniontech.top
 *
 */
@Controller
@RequestMapping("/config")
public class ConfigrationController {
    private final static Logger logger = LoggerFactory.getLogger(ConfigrationController.class);

    @RequestMapping("/read")
    public String index(ModelMap map) {
        return "index";
    }

    @RequestMapping("/reload")
    public String reload(String accessId, ModelMap map) {
        ReadConfigration config=new ReadConfigration();
        config.repload();
        map.put("list",config.getAccesss());
        logger.info("project list :{}",config.getAccesss());
        return "index";
    }
    @RequestMapping("/access")
    public String access(ModelMap map) {
        ReadConfigration config=new ReadConfigration();
        map.put("list",config.getAccesss());
        logger.info("access list :{}",config.getAccesss());
        return "access";
    }
    @RequestMapping("/project")
    public String project(String accessId, ModelMap map) {
        ReadConfigration config=new ReadConfigration();
        map.put("list",config.getProjects(accessId));
        logger.info("project list :{}",config.getProjects(accessId));
        return "project";
    }
    @RequestMapping("/template")
    public String template(String projectId,ModelMap map) {
        ReadConfigration config=new ReadConfigration();
        map.put("list",config.getTemplates(projectId));
        logger.info("template list :{}",config.getTemplates(projectId));
        return "template";
    }


    @RequestMapping("/adddAccess")
    public String adddAccess(String accessKeyId, String accessKeySecret, String accessDescription,ModelMap map) {
        WriteConfigration wconfig=new WriteConfigration();
        Access access=new Access(accessKeyId, accessKeySecret, accessDescription, new ArrayList<Project>());
        wconfig.writeAccess(access);
        ReadConfigration config=new ReadConfigration();
        map.put("list",config.getAccesss());
        logger.info("access list :{}",config.getAccesss());
        return "access";
    }
    @RequestMapping("/addProject")
    public String addProject(String accessId, String projectName, String projectDescription, ModelMap map) {
        WriteConfigration wconfig=new WriteConfigration();
        Project project=new Project(UUIDUtil.randomUUID32(), projectName, projectDescription, new ArrayList<Template>());
        wconfig.writeProject(project,accessId);
        ReadConfigration rconfig=new ReadConfigration();
        map.put("list",rconfig.getProjects(accessId));
        logger.info("project list :{}",rconfig.getProjects(accessId));
        return "project";
    }
    @RequestMapping("/addTemplate")
    public String addTemplate(String projectId, String templateCode, String sign, String templateDescription,List<String> paramKeys,ModelMap map) {
        WriteConfigration wconfig=new WriteConfigration();
        Template template=new Template(UUIDUtil.randomUUID32(), templateCode, sign, templateDescription, paramKeys);
        wconfig.writeTemplate(template,projectId);
        ReadConfigration config=new ReadConfigration();
        map.put("list",config.getTemplates(projectId));
        logger.info("template list :{}",config.getTemplates(projectId));
        return "template";
    }
}