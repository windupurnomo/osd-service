package com.sciencom.osd.controller;

import com.sciencom.osd.entity.Response;
import com.sciencom.osd.enums.RoleEnum;
import com.sciencom.osd.helper.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    @Autowired
    private JwtUtil jwtUtil;

    protected ResponseEntity getHttpResponse(Response response){
        if (response.getMessage() != null){
            Map map = new HashMap();
            map.put("message", response.getMessage());
            return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(response.getData(), HttpStatus.OK);
        }
    }


    protected boolean authenticate(String token){
        return jwtUtil.authenticate(token);
    }

    protected boolean authorize(RoleEnum roleRequired, String token){
        List<RoleEnum> userRoles = getRoles(token);
        for (RoleEnum re : userRoles){
            if (re == RoleEnum.ADMIN) return true;
            if (re == roleRequired) return true;
        }
        return false;
    }

    private List<RoleEnum> getRoles(String token){
        Integer[] roleIds;
        List<RoleEnum> roleEnums = new ArrayList<RoleEnum>();
        try{
            roleIds = jwtUtil.getRoles(token);
            for (Integer n : roleIds){
                roleEnums.add(RoleEnum.parse(n));
            }
        }catch (Exception ex){ }
        return roleEnums;
    }


}
