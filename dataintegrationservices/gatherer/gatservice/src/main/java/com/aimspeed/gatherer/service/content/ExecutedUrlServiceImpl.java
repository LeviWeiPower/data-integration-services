package com.aimspeed.gatherer.service.content;

import org.springframework.stereotype.Service;

import com.aimspeed.gatherer.entity.bean.mysql.content.ExecutedUrlMySqlBean;
import com.aimspeed.mysql.BaseMySqlServiceImpl;

/**
 * 执行过的URL
 * @author AimSpeed
 */
@Service
public class ExecutedUrlServiceImpl extends BaseMySqlServiceImpl<ExecutedUrlMySqlBean> implements ExecutedUrlService {

}
