package com.flycode.config;

import com.flycode.entity.Access;
import com.flycode.entity.Project;
import com.flycode.entity.Template;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.List;

public class WriteConfigration extends Configration implements WriteConfigure{


    public void writeAccess(Access access){
        List<Access> accesss=getRoot().getAccesss();
        accesss.add(access);
        repersist();
        reflesh();
    }
    public void writeProject(Project project,String accessId){
        List<Access> accesss=getRoot().getAccesss();
        for(Access access:accesss){
            if(accessId.equals(access.getAccessKeyId())){
                List<Project> projects=access.getProjects();
                projects.add(project);
            }
        }
        repersist();
        reflesh();
    }
    public void writeTemplate(Template template, String projectId){
        List<Access> accesss=getRoot().getAccesss();
        for(Access access:accesss){
           for(Project project:access.getProjects()){
               if(projectId.equals(project.getProjectId())){
                   List<Template> templates=project.getTemplates();
                   templates.add(template);
               }
           }
        }
        repersist();
        reflesh();
    }
}

