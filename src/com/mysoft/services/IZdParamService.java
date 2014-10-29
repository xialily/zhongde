package com.mysoft.services;

import com.mysoft.entities.ZdParam;

public interface IZdParamService extends IBaseService<ZdParam> {
public ZdParam findByParamName(String paramName);
}
