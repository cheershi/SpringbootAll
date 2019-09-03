package com.flycode.filter;

import com.flycode.utils.ErrorCode;
import com.flycode.utils.RegexValidateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CountFilter implements SmsFilter  {
    /**
     * 发送次数, 单位: 次
     */
    @Value("${maxCount}")
    private long maxCount;
    private ConcurrentMap<String, Long> sendAddressMap = new ConcurrentHashMap<>();
    @Value("${cleanMapInterval.count}")
    private long cleanMapInterval;
    private Timer timer = new Timer("sms_count_filter_clear_data_thread");
    // 省略了部分无用代码

    @Override
    public String filter(SmsEntity smsEntity) {
        String number=smsEntity.getNumber();
        boolean isNumber=RegexValidateUtil.checkMobileNumber(number);
        if(!isNumber){
            return ErrorCode.ILLEGAL_PARAMETER_NUMBER+number;
        }
        boolean sendCountOfNumber=setSendCount(number);
        if(sendCountOfNumber){
            return ErrorCode.EXCEEDING_TIMES_LIMIT_NUMBER+number;
        }

        String ipAddress=smsEntity.getIp();
        boolean isIpAddress=RegexValidateUtil.checkIPAddress(ipAddress);
        if(!isIpAddress){
            return ErrorCode.ILLEGAL_PARAMETER_IP+ipAddress;
        }
        boolean sendCountOfIp=setSendCount(ipAddress);
        if(sendCountOfIp){
            return ErrorCode.EXCEEDING_TIMES_LIMIT_IP+ipAddress;
        }
        return ErrorCode.SUCESS;
    }

    /**
     *限定发送次数
     */
    private boolean setSendCount(String id) {
        Long count=sendAddressMap.get(id);
        if(count==null){
            sendAddressMap.put(id, 1L);
            return false;
        }
        if(count>maxCount){
            return true;
        }
        count=count+1;
        sendAddressMap.put(id, count);
        return false;
    }


    @Override
    @PostConstruct
    public void init() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                cleanSendAddressMap();
            }
        }, cleanMapInterval, cleanMapInterval);
    }

    /**
     * 将sendAddressMap中的所有过期数据删除
     */
    private void cleanSendAddressMap() {
        sendAddressMap.clear();
    }

    @Override
    public void destroy() {
        timer.cancel();
    }

}


