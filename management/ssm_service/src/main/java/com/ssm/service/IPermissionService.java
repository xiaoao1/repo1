package com.ssm.service;

import com.ssm.domain.Permission;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IPermissionService {
    public List<Permission> findAll() throws Exception;

    void save(Permission permission) throws Exception;

    Permission findById(String id) throws Exception;



    void deletePermission(String id) throws Exception;

}
