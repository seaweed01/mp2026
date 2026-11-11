package com.example.mp0803

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.round

class MainActivity : AppCompatActivity() {
    private lateinit var tvResult: TextView
    private lateinit var tvInput: TextView
    private var tvResultCheck = false
    private var tvInputCheck = false
    //계산에 필요한 숫자 변수들
    private var FirstNum: Double = 0.0
    private var SecondNum: Double = 0.0
    private var ResultNum: Double = 0.0
    private var ArithOper: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val button: Button = findViewById(R.id.button)
        //버튼 클릭 리스너 설정
        button.setOnClickListener {
            //대화상자 객체 생성
            val builder = AlertDialog.Builder(this)
            //대화상자 레이아웃 설정
            val dialogView = layoutInflater.inflate(R.layout.dialog_calculate, null)
            builder.setView(dialogView)
            //대화상자 활성화
            val dialog = builder.show()
            tvResult = dialogView.findViewById(R.id.tvResult)
            tvInput = dialogView.findViewById(R.id.tvIntput)
            val buttonclose: Button = dialogView.findViewById(R.id.buttonclose)
            //대화상자 닫기 버튼 클릭 리스너
            buttonclose.setOnClickListener {
                dialog.dismiss()
            }
        }
    }
    //숫자 버튼 클릭 시 동작하는 메서드
    fun numClick(view : View){
        if(!tvResultCheck){
            tvResult.text = ""
        }
        tvResult.append(view.tag?.toString())
        tvResultCheck = true
        val str = tvResult.text.toString()
        SecondNum = str.toDouble()
    }
    //백스페이스 버튼 클릭 시 동작하는 메서드
    fun BackClick(view: View){
        var str = tvResult.text.toString()
        if(str.isNotEmpty()){
            str = str.substring(0, str.length - 1)
            tvResult.text = str
        }
    }
    //Clear(초기화) 버튼 클릭 시 동작하는 메서드
    fun ClearClick(view: View){
        tvResult.text = ""
        tvResultCheck = false
        SecondNum = 0.0
    }
    //AC(전체 초기화) 버튼 클릭 시 동작하는 메서드
    fun ACClick(view: View){
        tvResult.text = ""
        tvInput.text = ""
        tvResultCheck = false
        tvInputCheck = false
        FirstNum = 0.0
        SecondNum = 0.0
        ArithOper = ""
    }
    //소수점 버튼 클릭 시 동작하는 메서드
    fun DotClick(view: View) {
        var dotCheck = false
        tvResult.text.let {
            if(it.contains(".")) {
                dotCheck = true
            }
        }
        if (!tvResultCheck) {
            tvResult.text = "0."
            tvResultCheck = true
        }else if (!dotCheck){
            tvResult.append(".")
        }
    }
    //연산자 버튼 클릭 시 동작하는 메서드
    fun OperClick(view: View){
        val btn = view.tag?.toString()
        if(!tvInputCheck){
            if(SecondNum % 1.0 == 0.0)
                tvInput.text = SecondNum.toLong().toString() + " "+ btn
            else tvInput.text = SecondNum.toString() + " "+ btn
            FirstNum = SecondNum
            tvInputCheck = true
            tvResultCheck = false
        }else{
            if(!tvResultCheck){
                if(FirstNum % 1.0 == 0.0)
                    tvInput.text = FirstNum.toLong().toString() + " "+ btn
                else tvInput.text = FirstNum.toString() + " "+ btn
            }else{
                when(ArithOper){
                    "+" -> ResultNum = FirstNum + SecondNum
                    "-" -> ResultNum = FirstNum - SecondNum
                    "x" -> ResultNum = FirstNum * SecondNum
                    else -> ResultNum = FirstNum / SecondNum
                }
                ResultNum = round(ResultNum * 1000000000) / 1000000000
                if(FirstNum % 1.0 == 0.0)
                    tvInput.text = ResultNum.toLong().toString() + " "+ btn
                else tvInput.text = ResultNum.toString() + " "+ btn
                tvResultCheck = false
                FirstNum = ResultNum
            }
        }
        ArithOper = btn.toString()
    }
    //결과 버튼 클릭 시 동작하는 메서드
    fun EqualClick(view: View){
        tvInput.text = ""
        if(FirstNum % 1.0 == 0.0)
            tvInput.append(FirstNum.toLong().toString() + " "+ ArithOper)
        else tvInput.append(FirstNum.toString() + " "+ ArithOper)
        if(SecondNum % 1.0 == 0.0)
            tvInput.append(SecondNum.toLong().toString() + " =")
        else tvInput.append(SecondNum.toString() + " =")

        when(ArithOper){
            "+" -> ResultNum = FirstNum + SecondNum
            "-" -> ResultNum = FirstNum - SecondNum
            "x" -> ResultNum = FirstNum * SecondNum
            else -> ResultNum = FirstNum / SecondNum
        }
        ResultNum = round(ResultNum * 1000000000) / 1000000000
        if(ResultNum % 1.0 == 0.0)
            tvInput.append(ResultNum.toLong().toString() )
        else tvInput.append(ResultNum.toString())
        FirstNum = ResultNum
        tvResultCheck = false
    }
}