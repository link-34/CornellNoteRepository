package com.example.cornellnote.domain.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Output {

	private int				outId;				// ID
	private int				userId;				// ユーザーID
	private String			userName;			// ユーザー名
	private Date			outDate;			// アウトプット日時
	private String			outTitle;			// タイトル
	private String			outSummary;			// 要約
	private List<Content>	contentList;		// 見出しと内容
	private String			searchName;			// 検索ワード

}
