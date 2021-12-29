package com.example.cornellnote.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.cornellnote.domain.model.Content;

@Mapper
public interface CornellnoteMapper3 {
	
	// 「contentsテーブル」へ1件INSERT
	public boolean outputRegister(@Param("contentList")List<Content> contentList);
	
}
