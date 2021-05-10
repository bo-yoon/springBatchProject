package com.batchprogram.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.batchprogram.model.MemberDormant;

@Repository
public class MemberDormantDao {


    @Resource(name = "sqlSessionMDdb")
    private SqlSessionTemplate sqlSessionMDdb;

    public List<MemberDormant> selectDormant() {
        return this.sqlSessionMDdb.selectList("selectMD");
    }
    
    
}
