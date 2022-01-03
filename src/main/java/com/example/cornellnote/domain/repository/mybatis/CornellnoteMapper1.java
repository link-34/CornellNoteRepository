package com.example.cornellnote.domain.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;

import com.example.cornellnote.domain.model.User;

@Mapper
public interface CornellnoteMapper1 {
	
	// 「usersテーブル」へINSERT
	public boolean userSignup(User user);
	
	// 「usersテーブル」で1件SELECT
	public User userDetail(int userId);
	
	// 「usersテーブル」へUPDATE
	public boolean userUpdate(User user);
	
	// 「usersテーブル」へUPDATE(「is_deleted」を「1(削除済み)」にする)
	public boolean userDelete(int userId);
	
}
