package com.example.cornellnote.cornellnote.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.cornellnote.domain.model.Output;
import com.example.cornellnote.domain.model.OutputForm;
import com.example.cornellnote.domain.service.CornellnoteService;

@Controller
@RequestMapping("/cornellnote")
public class CornellnoteController {

	@Autowired
	CornellnoteService cornellnoteService;
	
	// アウトプット一覧画面表示用GETコントローラー
	@GetMapping("/index")
	public String getOutputList(Model model) {

		model.addAttribute("contents", "cornellnote/index :: index_contents");

		// アウトプット一覧の生成
		// 「Output」は、「Content」型の要素を格納した【contentList】を含む
		List<Output> outList = cornellnoteService.outputList();
		model.addAttribute("outList", outList);

		return "layout/cornellnoteLayout";
	}
	
	// アウトプット「登録」画面表示用GETコントローラー
	@GetMapping("/entry")
	public String getOutputRegister(@ModelAttribute OutputForm form, Model model) {
		
		model.addAttribute("contents", "cornellnote/entry :: entry_contents");
		
		return "layout/cornellnoteLayout";
	}
	
	// アウトプット「登録」処理用POSTコントローラー
	@PostMapping("/entry")
	public String postOutputRegister(@ModelAttribute @Validated OutputForm form, BindingResult bindingResult, Model model) {
		
		// 入力値チェック
		if (bindingResult.hasErrors() ) {
			// NG：アウトプット「登録」画面に戻る
			return getOutputRegister(form, model);
		}
		
		// 現在のログインユーザー名をSpringセキュリティで取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		// ユーザーIDを取得
		// selectでDBから検索する処理を追加
		
		// アウトプット日時(本日の日付)を取得
		LocalDateTime nowDateTime = LocalDateTime.now();
		
		// 最新の「outId」を取得
		// selectでDBから検索する処理を追加
		
		// 「Content」へフォーム画面の入力値を挿入
		// その際、上記で取得した「outId + 1」も「Content」へ挿入
		
		// 「Output」へフォーム画面の入力値を挿入
		
		// 登録処理
		
		return "redirect:/cornellnote/index";
		
	}
	
	// アウトプット「更新」画面表示用GETコントローラー
	@GetMapping("/edit/{id}")
	public String getOutputDetail(@ModelAttribute OutputForm form, Model model, @PathVariable("id") int outId) {
		
		model.addAttribute("contents", "cornellnote/edit :: edit_contents");
		
		// 「アウトプット」情報の取得（「outId」を使用）
		Output output = cornellnoteService.outputEdit(outId);
		// OutputクラスをOutputFormクラスに変換
		form.setOutTitle(output.getOutTitle());			// タイトル
		form.setOutSummary(output.getOutSummary());		// 要約
		form.setContentList(output.getContentList());	// 見出しと内容
		model.addAttribute("outputForm", form);
		
		// 「outId」をModelに登録 (更新時に使用するため)
		model.addAttribute("outId", outId);
		
		return "layout/cornellnoteLayout";
	}
	
}
