package com.example.cornellnote.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cornellnote.domain.model.Output;
import com.example.cornellnote.domain.repository.mybatis.CornellnoteMapper2;

@Transactional
@Service
public class CornellnoteService {

	@Autowired
	CornellnoteMapper2 cornellnoteMapper2;

	// アウトプット一覧画面表示用
	public List<Output> outputList() {
		return cornellnoteMapper2.outputList();
	}
	
	// アウトプット「更新」画面表示用
	public Output outputEdit(int outId) {
		return cornellnoteMapper2.outputEdit(outId);
	}

}
