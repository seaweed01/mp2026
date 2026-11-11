package com.example.mp0601

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 뷰 요소 가져오기
        val	btnLogin: Button = findViewById(R.id.button)
        val	etUsername: EditText = findViewById(R.id.editText01)
        val	etPassword:	EditText = findViewById(R.id.editText02)

        // 로그인 버튼 클릭 이벤트 설정
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString() // 아이디 입력값 가져오기
            val password = etPassword.text.toString() // 비밀번호 입력값 가져오기
            // 입력된 아이디와 비밀번호를 토스트 메시지로 출력
            Toast.makeText(
                applicationContext,
                "아이디 : $username\n비밀번호 : $password",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}