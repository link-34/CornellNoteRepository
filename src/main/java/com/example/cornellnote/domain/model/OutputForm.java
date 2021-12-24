package com.example.cornellnote.domain.model;

import java.util.List;

import lombok.Data;

@Data
public class OutputForm {
	
	private int				outId;			// ID
	private String			outTitle;		// タイトル
	private String 			outSummary;		// 要約
//	private String 			contCaption1;	// 見出し1
//	private String 			contContent1;	// 内容1
//	private String 			contCaption2;	// 見出し2
//	private String 			contContent2;	// 内容2
//	private String 			contCaption3;	// 見出し3
//	private String 			contContent3;	// 内容3
	private List<Content>	contentList;	// 見出しと内容
	
}
