package com.flycode.entity;

import lombok.*;

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
public class SoleTemplate {

   private String accessKeyId;
   private String projectId;
   private String accessKeySecret;
   private String templateCode;
   private String sign;
//   private Map<String,String> params;
   private List<String> params;

}
