package com.flycode.filter;

import com.flycode.utils.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SenderFilter{

    @Autowired
    private List<SmsFilter> filters;

    public String filter(List<String> numbers,String ip,Date time){
        for(String number:numbers) {
            SmsEntity smsEntity = new SmsEntity(number, ip,time);
            for (SmsFilter filter : filters) {
                String code=filter.filter(smsEntity);
                if (!ErrorCode.SUCESS.equals(code)) {
                    return code;
                }
            }
        }
        return  ErrorCode.SUCESS;
    }

}


