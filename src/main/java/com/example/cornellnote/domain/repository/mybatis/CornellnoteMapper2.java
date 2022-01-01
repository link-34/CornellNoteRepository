package com.example.cornellnote.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.cornellnote.domain.model.Output;

@Mapper
public interface CornellnoteMapper2 {

	// 「outputsテーブル」と「contentsテーブル」を左外部結合をして全件SELECT
	public List<Output> outputList(int registerUserId);
	
	// 「outputsテーブル」と「contentsテーブル」を左外部結合をして1件SELECT
	public Output outputEdit(int outId);
	
	// 「outputsテーブル」で1件SELECT
	public int registerUserId(String userName);
	
	// 「outputsテーブル」で1件SELECT
	public int registerOutId();
	
	// 「outputsテーブル」へ1件INSERT
	public boolean outputRegister(Output output);
	
	// 「outputsテーブル」へ1件UPDATE
	public boolean outputUpdate(Output output);
	
	// 「outputsテーブル」へ1件DELETE
	public boolean outputDelete(int outId);
	
	// 「outputsテーブル」と「contentsテーブル」を左外部結合をして複数件SELECT
	public List<Output> outputSearch(Output output);
	
}
