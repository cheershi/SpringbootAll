package com.flycode.config;

import com.flycode.entity.*;
import com.flycode.utils.XmlUtil;
import com.flycode.web.SmsSenderController;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configration implements Configure{
    private final static Logger logger = LoggerFactory.getLogger(SmsSenderController.class);
    @Getter
    @Setter
    private static Root root=new Root();
    private static String filePath="classpath:config.xml";
    @Getter
    private static Map<String,SoleTemplate> templateMap=new HashMap<String,SoleTemplate>();

    static {
        //读取XML文件
        repload();
        //初始化templateMap
        reflesh();
    }

    public static void reflesh() {
        logger.info("根据加载的XML数据，更新短信模板");
        if(root!=null){
            List<Access> accesss=root.getAccesss();
            if(accesss!=null&&accesss.size()>0){
                for(Access access:accesss){
                    String accessKeyId=access.getAccessKeyId();
                    String accessKeySecret=access.getAccessKeySecret();
                    List<Project> projects=access.getProjects();
                    if(projects!=null&&projects.size()>0){
                        for(Project project:projects){
                            String projectId=project.getProjectId();
                            List<Template> templates=project.getTemplates();
                            if(templates!=null&&templates.size()>0){
                                for(Template template:templates){
                                    String templateId=template.getTemplateId();
                                    SoleTemplate soleTemplate=new SoleTemplate();
                                    soleTemplate.setAccessKeyId(accessKeyId);
                                    soleTemplate.setAccessKeySecret(accessKeySecret);
                                    soleTemplate.setProjectId(projectId);
                                    soleTemplate.setTemplateCode(template.getTemplateCode());
                                    soleTemplate.setSign(template.getSign());
                                    soleTemplate.setParams(template.getParamKeys());

                                    templateMap.put(templateId,soleTemplate);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    public static void repersist() {
        logger.info("持久化XML数据");
        try {
            File file = ResourceUtils.getFile(filePath);
            StringWriter sw=XmlUtil.beanToXml(file,root);
        }catch (Exception e) {
            logger.info("持久化XML数据出错：{}",e.getStackTrace());
        }
    }

    public static void repload(){
        logger.info("读取XML数据");
        try {
            File file = ResourceUtils.getFile(filePath);
            if(file.exists()) {
                root=XmlUtil.xmlToBean(file,root);
            }
        }catch (Exception e){
            logger.info("读取XML数据出错：{}",e.getStackTrace());
        }

    }
}

