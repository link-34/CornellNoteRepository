package com.example.cornellnote.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.example.cornellnote.domain.model.User;
import com.example.cornellnote.domain.model.UserForm;
import com.example.cornellnote.domain.service.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	LoginService loginService;
	
	// ログイン画面表示用GETコントローラー
	@GetMapping("/index")
	public String getLogin(Model model) {

		return "login/login";
	}
	
	// ログアウト用POSTコントローラー
	@PostMapping("/logout")
	public String postLogout(Model model) {

		return "redirect:/login/index";
	}
	
	// ユーザー「登録」画面表示用GETコントローラー
	@GetMapping("/signup")
	public String getUserSignup(@ModelAttribute UserForm form, Model model) {
		
		return "login/signup";
	}
	
	// ユーザー「登録」処理用POSTコントローラー
	@PostMapping("/signup")
	public String postUserSignup(@ModelAttribute @Validated UserForm form, BindingResult bindingResult,Model model) {
		
		// 入力値チェック
		if (bindingResult.hasErrors()) {
			// NG：ユーザー「登録」画面に戻る
			return getUserSignup(form, model);
		}
		
		// パスワード暗号化
		String userPassEncode = passwordEncoder.encode(form.getUserPass());
		
		// 「UserForm」クラスをUserクラスに変換
		User user = new User();
		user.setUserName(form.getUserName());
		user.setUserPass(userPassEncode);
		
		// 登録処理
		boolean userSignupResult = loginService.userSignup(user);
		if(userSignupResult==true) {
			System.out.println("ユーザー情報の登録成功！");
		} else if(userSignupResult==false) {
			System.out.println("ユーザー情報の登録失敗…");
		}
		
		return "redirect:/login/index";
	}
	
	// ユーザー「詳細」画面表示用GETコントローラー
	@GetMapping("/userDetail/{id}")
	public String getUserDetail(@PathVariable("id") int userId, @ModelAttribute UserForm form, Model model) {
		
		// ユーザー「詳細」情報の取得（「userId」を使用）
		User user = loginService.userDetail(userId);
		
		// Userクラスを「UserForm」クラスに変換
		// パスワード欄は空欄とするため、変換しない。
		form.setUserName(user.getUserName());
		model.addAttribute("userForm", form);
		
		// 「outId」をModelに登録 (更新時に使用するため)
		model.addAttribute("userId", userId);
		
		return "login/userDetail";
	}
	
	// ユーザー「詳細」更新処理用POSTコントローラー
	@PostMapping(value = "/userDetail", params = "update")
	public String postUserUpdate(@RequestParam("update")int userId, @ModelAttribute @Validated UserForm form, BindingResult bindingResult, Model model) {
		
		// 入力値チェック
		if (bindingResult.hasErrors()) {
			// NG：ユーザー「登録」画面に戻る
			return getUserDetail(userId, form, model);
		}
				
		// パスワード暗号化
		String userPassEncode = passwordEncoder.encode(form.getUserPass());
		
		// 「UserForm」クラスをUserクラスに変換
		User user = new User();
		user.setUserId(userId);
		user.setUserName(form.getUserName());
		user.setUserPass(userPassEncode);
		
		// 更新処理
		boolean userUpdateResult = loginService.userUpdate(user);
		if(userUpdateResult==true) {
			System.out.println("ユーザー情報の更新成功！");
		} else if(userUpdateResult==false) {
			System.out.println("ユーザー情報の更新失敗…");
		}
		
		return "redirect:/login/index";
		
	}
	
	// ユーザー「詳細」削除処理用POSTコントローラー
	@PostMapping(value = "/userDetail", params = "delete")
	public String postUserDelete(@RequestParam("delete")int userId, Model model) {
		
		// 削除処理
		boolean userDeleteResult = loginService.userDelete(userId);
		if(userDeleteResult==true) {
			System.out.println("ユーザー情報の削除成功！");
		} else if(userDeleteResult==false) {
			System.out.println("ユーザー情報の削除失敗…");
		}
		
		return "redirect:/login/index";
		
	}
	
}
