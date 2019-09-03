package com.flycode.entity;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author FLY
 * @version 1.0.0
 * @date 2018-09-17
 * @blog http://uniontech.top
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class Project {
   @XmlElement
   private String projectId;
   @XmlElement
   private String projectName;
   @XmlElement
   private String projectDescription;
   @XmlElement
   private List<Template> templates;

}
