package com.example.cornellnote.domain.model;

import lombok.Data;

@Data
public class User {

	private int	userId;			// ID
	private String	userName;		// ユーザー名
	private String	userPass;		// パスワード
	private byte	isDeleted;		// 削除フラグ(0:未削除、  1:削除済み)
	private byte	isAdmin;		// 管理者権限(0:権限なし、1:権限あり)
}
