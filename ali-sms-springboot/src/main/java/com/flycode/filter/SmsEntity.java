package com.flycode.filter;

import com.flycode.config.Configration;
import com.flycode.config.WriteConfigure;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SmsEntity{
//    private String uuid;
    private String number;
    private String ip;
    private Date time;
//    private String captcha;
}


