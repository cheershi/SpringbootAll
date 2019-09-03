package com.flycode.config;

import com.flycode.entity.SoleTemplate;

/**
 * @author FLY
 * @version 1.0.0
 * @date 2018-09-17
 * @blog http://uniontech.top
 */
public interface ReadConfigure extends Configure{

    SoleTemplate getSoleTemplate(String templateId);
}
