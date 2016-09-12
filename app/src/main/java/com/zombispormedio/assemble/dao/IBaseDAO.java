package com.zombispormedio.assemble.dao;

import com.zombispormedio.assemble.models.BaseModel;

/**
 * Created by Xavier Serrano on 12/09/2016.
 */
public interface IBaseDAO<M extends BaseModel> {


    M toModel() throws NoSuchFieldException, IllegalAccessException;

    IBaseDAO fromModel(M model) throws NoSuchFieldException, IllegalAccessException;

}
