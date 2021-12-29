package com.example.cornellnote.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cornellnote.domain.model.Content;
import com.example.cornellnote.domain.model.Output;
import com.example.cornellnote.domain.repository.mybatis.CornellnoteMapper2;
import com.example.cornellnote.domain.repository.mybatis.CornellnoteMapper3;

@Transactional
@Service
public class CornellnoteService {

	@Autowired
	CornellnoteMapper2 cornellnoteMapper2;
	
	@Autowired
	CornellnoteMapper3 cornellnoteMapper3;

	// アウトプット一覧画面表示用
	public List<Output> outputList() {
		return cornellnoteMapper2.outputList();
	}
	
	// アウトプット「更新」画面表示用
	public Output outputEdit(int outId) {
		return cornellnoteMapper2.outputEdit(outId);
	}
	
	// アウトプット「登録」処理に使用する「userId」取得用
	public int registerUserId(String userName) {
		return cornellnoteMapper2.registerUserId(userName);
	}
	
	// アウトプット「登録」処理に使用する最新の「outId」取得用
	public int registerOutId() {
		return cornellnoteMapper2.registerOutId();
	}
	
	// アウトプット「登録」処理用(「outputsテーブル」/「contentsテーブル」へのINSERT)
	public boolean outputRegister(Output output, List<Content> contentList) {
		
		boolean outputRegisterResult2 = cornellnoteMapper2.outputRegister(output);
		boolean outputRegisterResult3 = cornellnoteMapper3.outputRegister(contentList);
		
		if(outputRegisterResult2 && outputRegisterResult3) {
			return true;
		} else {
			return false;
		}
		
	}

}
