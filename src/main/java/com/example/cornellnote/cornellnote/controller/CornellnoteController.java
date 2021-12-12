package com.example.cornellnote.cornellnote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.cornellnote.domain.model.Output;
import com.example.cornellnote.domain.service.CornellnoteService;

@Controller
@RequestMapping("/cornellnote")
public class CornellnoteController {

	@Autowired
	CornellnoteService cornellnoteService;

	// アウトプット一覧画面表示用GETコントローラー
	// 一時的に「POST」にしている。ログイン関連の実装時に「GET」に変更する事！
	@PostMapping("/index")
	public String getOutputList(Model model) {

		model.addAttribute("contents", "cornellnote/index :: index_contents");

		// アウトプット一覧の生成
		// 「Output」は、「Content」型の要素を格納した【contentList】を含む
		List<Output> outList = cornellnoteService.outputList();
		model.addAttribute("outList", outList);

			System.out.println(outList);

		return "layout/cornellnoteLayout";
	}

}
