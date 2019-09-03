package com.flycode.config;

import com.flycode.entity.Access;
import com.flycode.entity.Project;
import com.flycode.entity.SoleTemplate;
import com.flycode.entity.Template;

import java.util.ArrayList;
import java.util.List;

public class ReadConfigration extends Configration implements ReadConfigure{


    public  SoleTemplate getSoleTemplate(String templateId){
        return   getTemplateMap().get(templateId);
    }


    public List<Access> getAccesss(){
        return getRoot().getAccesss();
    }

    public List<Project> getProjects(String accessId){
        for(Access access:getRoot().getAccesss()){
            if(accessId.equals(access.getAccessKeyId())){
                return access.getProjects();
            }
        }
        return new ArrayList<>();
    }
    public List<Template> getTemplates(String projectId){
        for(Access access:getRoot().getAccesss()){
            List<Project> projects=access.getProjects();
          for(Project project:projects){
              if(projectId.equals(project.getProjectId())){
                return project.getTemplates();
              }
            }
        }
        return new ArrayList<>();
    }

}

