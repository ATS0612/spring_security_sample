package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class LoginUserService implements UserDetailsService { // ユーザー情報の検索と取得するクラス

    private final UserRepository userRepository;

    // コンストラクタインジェクション
    @Autowired
    public LoginUserService(UserRepository userRepository) { // コンストラクタ、userRpositoryの代入に利用
        this.userRepository = userRepository;
    }


    @Override
    // emailはユーザーから送信されるメールアドレスを想定しています
    public LoginUser loadUserByUsername(String email) throws UsernameNotFoundException { // 引数を使ってUserオブジェクトを取得し、ユーザーが見つかればLoginUserを返却する
        // emailによりデータベースからユーザ情報の取得
        User user = this.userRepository.findByEmail(email);

        // ユーザー情報が見つからない場合、例外を発生させます
        if (user == null) {
            throw new UsernameNotFoundException("ユーザが見つかりません");
        }

        // ユーザ情報が見つかった場合は、UserDetailsを生成し返却します
        return new LoginUser(user);
    }
}