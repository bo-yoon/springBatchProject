package com.batchprogram.dao;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.batchprogram.model.Member;


import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
public class MemberDao {

	 @Resource(name = "sqlSessionMemberdb")
	 private SqlSessionTemplate sqlSessionMemberdb;

	 public List<Member> selectMember() {
	      return this.sqlSessionMemberdb.selectList("selectMember");
	 }
	 
	 public Member selectOne(Long member_id) {
		 return this.sqlSessionMemberdb.selectOne("selectMember3",member_id);
	 }

}



