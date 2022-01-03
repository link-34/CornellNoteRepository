package com.example.cornellnote.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cornellnote.domain.model.User;
import com.example.cornellnote.domain.repository.mybatis.CornellnoteMapper1;

@Transactional
@Service
public class LoginService {
	
	@Autowired
	CornellnoteMapper1 cornellnoteMapper1;
	
	// ユーザー「登録」処理用
	public boolean userSignup(User user) {
		
		boolean userSignupResult1 = cornellnoteMapper1.userSignup(user);
		if(userSignupResult1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	// ユーザー「詳細」画面表示用
	public User userDetail(int userId) {
		return cornellnoteMapper1.userDetail(userId);
		
	}
	
	// ユーザー「詳細」更新処理用
	public boolean userUpdate(User user) {
		return cornellnoteMapper1.userUpdate(user);
	}
	
	// ユーザー「詳細」削除処理用
	public boolean userDelete(int userId) {
		return cornellnoteMapper1.userDelete(userId);
	}
	
}
