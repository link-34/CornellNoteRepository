package com.example.cornellnote.domain.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class OutputForm {
	
	private int				outId;			// ID
	
	@NotBlank
	private String			outTitle;		// タイトル
	
	@NotBlank
	@Length(min = 0, max = 140)
	private String 			outSummary;		// 要約
	
	private String 			contCaption1;	// 見出し1
	private String 			contContent1;	// 内容1
	private String 			contCaption2;	// 見出し2
	private String 			contContent2;	// 内容2
	private String 			contCaption3;	// 見出し3
	private String 			contContent3;	// 内容3
	private List<Content>	contentList;	// 見出しと内容
	
}
