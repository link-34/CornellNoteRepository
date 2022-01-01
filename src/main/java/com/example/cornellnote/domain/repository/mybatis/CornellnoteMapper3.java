package com.example.cornellnote.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.cornellnote.domain.model.Content;
import com.example.cornellnote.domain.model.Output;

@Mapper
public interface CornellnoteMapper3 {
	
	// 「contentsテーブル」へ1件INSERT
	public boolean outputRegister(@Param("contentList")List<Content> contentList);
	
	// 「contentsテーブル」へ1件UPDATE (DELETE)
	public boolean outputUpdateDelete(Output output);
	
	// 「contentsテーブル」へ1件UPDATE (INSERT)
	public boolean outputUpdateInsert(@Param("contentList")List<Content> contentList);
	
	// 「contentsテーブル」へ1件DELETE
	public boolean outputDelete(int outId);
	
}
