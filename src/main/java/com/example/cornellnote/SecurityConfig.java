package com.example.cornellnote;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// パスワードエンコーダーのBean定義
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// データソース
	@Autowired
	private DataSource dataSource;

	// ユーザー名とパスワードを取得するSQL文
	private static final String USER_SQL = "SELECT"
		+ " user_name,"
		+ " user_pass,"
		+ " true"											// 使用可否（Enabled）：true設定
	    + " FROM"
	    + " users"
	    + " WHERE"
	    + " user_name = ? and is_deleted = 0";

	// ユーザーのロールを取得するSQL文
	private static final String ROLE_SQL = "SELECT"
		+ " user_name,"
		+ " is_admin"
		+ " FROM"
		+ " users"
		+ " WHERE"
		+ " user_name = ? and is_deleted = 0";
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		// 静的リソースへのアクセスには、セキュリティを適用しない
		web.ignoring().antMatchers("/webjars/**", "/css/**", "/icon/**", "/image/**", "/js/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// ログイン不要ページの設定
		http.authorizeRequests()
			.antMatchers("/webjars/**").permitAll()            // webjarsへアクセス許可
	        .antMatchers("/css/**").permitAll()                // cssへアクセス許可
	        .antMatchers("/icon/**").permitAll()               // iconへアクセス許可
	        .antMatchers("/image/**").permitAll()              // imageへアクセス許可
	        .antMatchers("/js/**").permitAll()                 // jsへアクセス許可
	        .antMatchers("/login/index").permitAll()           // ログインページは直リンクOK
	        .antMatchers("/login/signup").permitAll()          // 新規登録ページは直リンクOK
	        .anyRequest().authenticated();                     // それ以外は直リンク禁止

		// ログイン処理
		http.formLogin()
		     .loginProcessingUrl("/cornellnote/index")          // ログイン処理のパス
		     .loginPage("/login/index")                         // ログインページの指定
			 .failureUrl("/login/index")                        // ログイン失敗時の遷移先
			 .usernameParameter("userName")                     // ログインページのユーザー名
			 .passwordParameter("userPass")                     // ログインページのパスワード
			 .defaultSuccessUrl("/cornellnote/index", true);    // ログイン成功後の遷移先

		// ログアウト処理
		http.logout()
			.logoutUrl("/login/logout")                          // ログアウト処理のパス(POST)
			.logoutSuccessUrl("/login/login");                   // ログアウト成功後の遷移先
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		// ログイン処理時のユーザー情報を、DBから取得する
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(USER_SQL)
			.authoritiesByUsernameQuery(ROLE_SQL)
			.passwordEncoder(passwordEncoder());			//ログイン時のパスワード復号

	}
	
}
