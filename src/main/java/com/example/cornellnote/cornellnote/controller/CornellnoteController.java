package com.example.cornellnote.cornellnote.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.cornellnote.domain.model.Content;
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
		
		// コンテンツ部分にアウトプット一覧画面を表示するための文字列を登録
		model.addAttribute("contents", "cornellnote/index :: index_contents");
		
		// 現在のログインユーザー名をSpringセキュリティで取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		// 現在のログインユーザーのユーザーIDを取得
		int registerUserId = cornellnoteService.registerUserId(userName);
		// 「userId」をModelに登録(ユーザー詳細画面の表示に使用するため)
		model.addAttribute("userId", registerUserId);
		
		// アウトプット一覧の生成
		// 「Output」は、「Content」型の要素を格納した【contentList】を含む
		List<Output> outList = cornellnoteService.outputList(registerUserId);
		
		model.addAttribute("outList", outList);

		return "layout/cornellnoteLayout";
	}
	
	// アウトプット「登録」画面表示用GETコントローラー
	@GetMapping("/entry")
	public String getOutputRegister(@ModelAttribute OutputForm form, Model model) {
		
		// コンテンツ部分にアウトプット「登録」画面を表示するための文字列を登録
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
		// 現在のログインユーザーのユーザーIDを取得
		int registerUserId = cornellnoteService.registerUserId(userName);
		
		// アウトプット日時(本日の日付)を取得
		Date registerDate = new Date();
		
		// 新規登録で割り当てられる「outId」を取得
		// 最新の「outId」を取得後に「+ 1」して得る
		int registerOutId = cornellnoteService.registerOutId() + 1;
		
		// 「OutputForm」クラスの値を各「Content」の組(対となる見出しと内容)に格納
		// その際、上記で取得した「registerOutId」も合わせて格納
		Content content1 = new Content();
		content1.setOutId(registerOutId);
		content1.setContCaption(form.getContCaption1());
		content1.setContContent(form.getContContent1());
		
		Content content2 = new Content();
		content2.setOutId(registerOutId);
		content2.setContCaption(form.getContCaption2());
		content2.setContContent(form.getContContent2());
		
		Content content3 = new Content();
		content3.setOutId(registerOutId);
		content3.setContCaption(form.getContCaption3());
		content3.setContContent(form.getContContent3());
		
		// 各「Content」の組(対となる見出しと内容)をListに格納
		List<Content> contentList = new ArrayList<>();
		contentList.add(content1);
		contentList.add(content2);
		contentList.add(content3);
		
		// 「OutputForm」クラスをOutputクラスに変換
		Output output = new Output();
		output.setOutId(registerOutId);
		output.setUserId(registerUserId);
		output.setOutDate(registerDate);
		output.setOutTitle(form.getOutTitle());
		output.setOutSummary(form.getOutSummary());
		
		// 登録処理
		boolean outputRegisterResult = cornellnoteService.outputRegister(output, contentList);
		if(outputRegisterResult==true) {
			System.out.println("アウトプットの登録成功！");
		} else if(outputRegisterResult==false) {
			System.out.println("アウトプットの登録失敗…");
		}
		
		return "redirect:/cornellnote/index";
		
	}
	
	// アウトプット「更新」画面表示用GETコントローラー
	@GetMapping("/edit/{id}")
	public String getOutputDetail(@ModelAttribute OutputForm form, Model model, @PathVariable("id") int outId) {
		
		// コンテンツ部分にアウトプット「更新」画面を表示するための文字列を登録
		model.addAttribute("contents", "cornellnote/edit :: edit_contents");
		
		// 現在のログインユーザー名をSpringセキュリティで取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		
		// 「アウトプット」情報の取得（「outId」を使用）
		Output output = cornellnoteService.outputEdit(outId);
		
		// Outputクラスを「OutputForm」クラスに変換
		form.setOutTitle(output.getOutTitle());
		form.setOutSummary(output.getOutSummary());
		
			// 「contentList」から要素を取り出し「OutputForm」クラスに変換
			Content content1 = new Content();
			Content content2 = new Content();
			Content content3 = new Content();
			
			List<Content> contentList = output.getContentList();
			
			if(contentList.size() != 0) {
				content1 = contentList.get(0);
				content2 = contentList.get(1);
				content3 = contentList.get(2);
			}
			
			form.setContCaption1(content1.getContCaption());
			form.setContContent1(content1.getContContent());
			form.setContCaption2(content2.getContCaption());
			form.setContContent2(content2.getContContent());
			form.setContCaption3(content3.getContCaption());
			form.setContContent3(content3.getContContent());
			
		model.addAttribute("outputForm", form);
		
		// 「outId」をModelに登録 (更新時に使用するため)
		model.addAttribute("outId", outId);
		
		return "layout/cornellnoteLayout";
	}
	
	// アウトプット「更新」更新処理用POSTコントローラー
	@PostMapping(value = "/edit", params = "update")
	public String postOutputUpdate(@RequestParam("update")int outId, @ModelAttribute @Validated OutputForm form, BindingResult bindingResult, Model model) {
		
		// 入力値チェック
		if (bindingResult.hasErrors() ) {
			// NG：アウトプット「登録」画面に戻る
			return getOutputDetail(form, model, outId);
		}
		
		// 「OutputForm」クラスの値を各「Content」の組(対となる見出しと内容)に格納
		Content content1 = new Content();
		content1.setOutId(outId);
		content1.setContCaption(form.getContCaption1());
		content1.setContContent(form.getContContent1());
		
		Content content2 = new Content();
		content2.setOutId(outId);
		content2.setContCaption(form.getContCaption2());
		content2.setContContent(form.getContContent2());
		
		Content content3 = new Content();
		content3.setOutId(outId);
		content3.setContCaption(form.getContCaption3());
		content3.setContContent(form.getContContent3());
		
		// 各「Content」の組(対となる見出しと内容)をListに格納
		List<Content> contentList = new ArrayList<>();
		contentList.add(content1);
		contentList.add(content2);
		contentList.add(content3);
		
		// 「OutputForm」クラスをOutputクラスに変換
		Output output = new Output();
		output.setOutId(outId);
		output.setOutTitle(form.getOutTitle());
		output.setOutSummary(form.getOutSummary());
		
		// 更新処理
		boolean outputUpdateResult = cornellnoteService.outputUpdate(output, contentList);
		if(outputUpdateResult==true) {
			System.out.println("アウトプットの更新成功！");
		} else if(outputUpdateResult==false) {
			System.out.println("アウトプットの更新失敗…");
		}
		
		return "redirect:/cornellnote/index";
		
	}
	
	// アウトプット「更新」削除処理用POSTコントローラー
	@PostMapping(value = "/edit", params = "delete")
	public String postOutputDelete(@RequestParam("delete")int outId, Model model) {
		
		// 削除処理
		boolean outputDeleteResult = cornellnoteService.outputDelete(outId);
		if(outputDeleteResult==true) {
			System.out.println("アウトプットの削除成功！");
		} else if(outputDeleteResult==false) {
			System.out.println("アウトプットの削除失敗…");
		}
		
		return "redirect:/cornellnote/index";
		
	}
	
	// アウトプット「検索結果」画面表示用GETコントローラー
	@GetMapping(value = "/search", params = "search")
	public String getOutputSearch(@RequestParam("searchName")String searchName, Model model) {
		
		// コンテンツ部分にアウトプット「検索結果」画面を表示するための文字列を登録
		model.addAttribute("contents", "cornellnote/search :: search_contents");
		
		// 現在のログインユーザー名をSpringセキュリティで取得
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		model.addAttribute("userName", userName);
		// 現在のログインユーザーのユーザーIDを取得
		int registerUserId = cornellnoteService.registerUserId(userName);
		
		// 検索ワードをModelへの登録 (検索窓へ検索文字を表示のため)
		model.addAttribute("searchName", searchName);
		
		// 「Output」クラスへ「検索ワード」と「ユーザーID」を格納
		Output output = new Output();
		output.setUserId(registerUserId);
		output.setSearchName(searchName);
		
		// 検索結果の一覧の生成
		List<Output> searchResultList = cornellnoteService.outputSearch(output);
		model.addAttribute("searchResultList", searchResultList);
		
		return "layout/cornellnoteLayout";
		
	}
	
	// アウトプット「検索結果」から一覧画面へ戻る用GETコントローラー(リダイレクト)
	@GetMapping(value = "/search", params = "back")
	public String getOutputSearchBack() {
		// 検索結果の一覧からアウトプット一覧画面へリダイレクト
		return "redirect:/cornellnote/index";
	}
	
}
