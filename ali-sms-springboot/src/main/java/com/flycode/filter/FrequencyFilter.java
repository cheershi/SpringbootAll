package com.flycode.filter;

import com.flycode.utils.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FrequencyFilter implements SmsFilter  {
    /**
     * 发送间隔, 单位: 毫秒
     */
    @Value("${sendInterval}")
    private long sendInterval;
    private ConcurrentMap<String, Long> sendAddressMap = new ConcurrentHashMap<>();
    @Value("${cleanMapInterval.frequency}")
    private long cleanMapInterval;
    private Timer timer = new Timer("sms_frequency_filter_clear_data_thread");
    // 省略了部分无用代码

    @Override
    public String filter(SmsEntity smsEntity) {
        if(!setSendTime(smsEntity.getNumber())){
            return ErrorCode.FREQUENCY_TOO_HIGH_NUMBER+smsEntity.getNumber();
        }
        if(!setSendTime(smsEntity.getIp())){
            return ErrorCode.FREQUENCY_TOO_HIGH_IP+smsEntity.getIp();
        }
        return ErrorCode.SUCESS;
    }

    /**
     * 将发送时间修改为当前时间.
     * 如果距离上次发送的时间间隔大于{@link #sendInterval}则设置发送时间为当前时间. 否则不修改任何内容.
     *
     * @param id 发送手机号 或 ip
     * @return 如果成功将发送时间修改为当前时间, 则返回true. 否则返回false
     */
    private boolean setSendTime(String id) {
        long currentTime = System.currentTimeMillis();

        Long sendTime = sendAddressMap.putIfAbsent(id, currentTime);
        if(sendTime == null) {
            return true;
        }

        long nextCanSendTime = sendTime + sendInterval;
        if(currentTime < nextCanSendTime) {
            return false;
        }

        return sendAddressMap.replace(id, sendTime, currentTime);
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
        long currentTime = System.currentTimeMillis();
        long expireSendTime = currentTime - sendInterval;

        for(String key : sendAddressMap.keySet()) {
            Long sendTime = sendAddressMap.get(key);
            if(sendTime < expireSendTime) {
                sendAddressMap.remove(key, sendTime);
            }
        }
    }

    @Override
    public void destroy() {
        timer.cancel();
    }

}


