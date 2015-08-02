package com.importadorabacco.web.service;

import com.importadorabacco.web.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by tanhengyi on 15-5-13.
 */
public abstract class BaseService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    protected UserCartDao userCartDao;

    @Resource
    protected OrderDao orderDao;

    @Resource
    protected OrderCartDao orderCartDao;

    @Resource
    protected UserAccountDao userAccountDao;

    @Resource
    protected ProductDao productDao;

    @Resource
    protected UserAccountResetDao userAccountResetDao;
}
