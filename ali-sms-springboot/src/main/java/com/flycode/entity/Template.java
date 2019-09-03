package com.flycode.entity;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

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
public class Template {
   @XmlElement
   private String templateId;
   @XmlElement
   private String templateCode;
   @XmlElement
   private String sign;
   @XmlElement
   private String templateDescription;
   @XmlElement
   private List<String> paramKeys;

}
