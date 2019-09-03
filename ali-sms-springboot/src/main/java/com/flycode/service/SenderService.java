package com.flycode.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.flycode.config.ReadConfigration;
import com.flycode.entity.SoleTemplate;
import com.flycode.entity.StringWraper;

import com.flycode.filter.SenderFilter;
import com.flycode.sms.SmsClient;
import com.flycode.sms.SmsTemplate;
import com.flycode.utils.ErrorAliCode;
import com.flycode.utils.ErrorCode;
import com.flycode.utils.IpUtil;
import com.flycode.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 *
 * @author 程序猿DD
 * @version 1.0.0
 * @blog http://blog.didispace.com
 *
 */
@Component
public class SenderService {
    private final static Logger logger = LoggerFactory.getLogger(SenderService.class);

    public static boolean test=false;
    @Autowired
    public SenderFilter senderFilter;

    public String send(HttpServletRequest request)  {
        ReadConfigration config=new ReadConfigration();
        String templateId = StringUtil.nvl(request.getParameter("templateId"));
        logger.info("templateId:{}",templateId);
        SoleTemplate soleTemplate = config.getSoleTemplate(templateId);

        //合法性校验
        if (soleTemplate == null) {
            logger.info("templateId:{}", ErrorCode.NO_TEMPLATE_CONFIG);
            return ErrorCode.NO_TEMPLATE_CONFIG;
        }
        String projectId = StringUtil.nvl(request.getParameter("projectId"));
        if (!projectId.equals(soleTemplate.getProjectId())) {
            logger.info("projectId:{}",projectId);
            return ErrorCode.NO_TEMPLATE_CONFIG_PROJECT;
        }

        //参数准备
        List<String> params=soleTemplate.getParams();
        Map<String,String> paramMap=new HashMap<>();
        if(params!=null&&params.size()>0){
            for(String sw:params){
                String temp = StringUtil.nvl(request.getParameter(sw));
                paramMap.put(sw,temp);
            }
        }
        String numbers = StringUtil.nvl(request.getParameter("numbers"));
        List numbs = Arrays.asList(numbers.split(","));
        String clientIp = StringUtil.nvl(request.getParameter("clientIp"));

        //恶意发送校验
        String info=senderFilter.filter(numbs,clientIp,new Date());
        if (!ErrorCode.SUCESS.equals(info)) {
            return info;
        }

        String projectIP=IpUtil.getIpAddr(request);
        if (test){
            logger.info("短信模板：{} ; 接收号码：{} ; 发送来源：{} ; 接收内容：{} ; 发送结果：{}",templateId,numbers, projectIP+":"+projectId,paramMap, ErrorAliCode.getCodeHint("OK"));
            return ErrorCode.SUCESS;
        }
        //开始发送短信
        SmsClient smsClient = new SmsClient(soleTemplate.getAccessKeyId(), soleTemplate.getAccessKeySecret());
        SmsTemplate smsTemplate = SmsTemplate.builder()
                .signName(soleTemplate.getSign())
                .templateCode(soleTemplate.getTemplateCode())
                .addTemplateParams(paramMap)
                .phoneNumbers(numbs)
                .build();

        SendSmsResponse sendSmsResponse = smsClient.send(smsTemplate);
        String code = sendSmsResponse.getCode();
        logger.info("短信模板：{} ; 接收号码：{} ; 发送来源：{} ; 接收内容：{} ; 发送结果：{}",templateId,numbers, projectIP+":"+projectId,paramMap, ErrorAliCode.getCodeHint(code));
        return  ErrorAliCode.getCodeHint(code);
    }


}