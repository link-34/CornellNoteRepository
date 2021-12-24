package com.example.cornellnote.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.cornellnote.domain.model.Output;

@Mapper
public interface CornellnoteMapper2 {

	// 「outputsテーブル」と「contentsテーブル」を左外部結合をして全件SELECT
	public List<Output> outputList();
	
	// 「outputsテーブル」と「contentsテーブル」を左外部結合をして1件SELECT
	public Output outputEdit(int outId);
	
}
