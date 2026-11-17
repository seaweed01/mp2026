package com.example.mp1004

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val findFromTxt : TextView = findViewById(R.id.findFromTxt)
        val findToTxt : TextView= findViewById(R.id.findToTxt)
        val insertBtn : Button = findViewById(R.id.button_insert)
        val searchBtn : Button= findViewById(R.id.button_search)

        //검색 버튼 클릭 시 날짜 선택 후 해당 기간의 데이터 조회
        searchBtn.setOnClickListener {
            //MaterialDatePicker를 사용해 날짜 범위 선택 대화상자 생성
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            val picker = builder.build()
            //날짜 범위 선택 후 처리
            picker.addOnPositiveButtonClickListener {
                //선택된 기간의 시작 및 종료 날짜를 yyyy-MM-dd 형식으로 변환
                val fromDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.first)
                val toDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.second)
                findFromTxt.text = fromDate
                findToTxt.text = toDate
                //DBHelper를 통해 해당 날짜 범위에 맞는 데이터를 조회
                val dbHelper = DBHelper(this)
                val list = dbHelper.findTime(fromDate, toDate)
                //조회 결과를 RecyclerView에 표시하기 위한 어댑터와 레이아웃 매니저 설정
                val FindRecyclerView: RecyclerView = findViewById(R.id.FindRecyclerView)
                val mAdapter = RecyclerViewAdapter(this, list)
                FindRecyclerView.adapter = mAdapter
                //선형 레이아웃 매니저를 사용하여 RecyclerView 리스트 형태로 출력
                val layout = LinearLayoutManager(this)
                FindRecyclerView.layoutManager = layout
                FindRecyclerView.setHasFixedSize(true)
            }
            picker.show(supportFragmentManager, picker.toString())  //날짜 선택 대화상자 표시
        }
        //입력 버튼 클릭 시 InsertActivity로 이동
        insertBtn.setOnClickListener {
            val intent = Intent(this, InsertActivity::class.java)
            startActivity(intent)
        }
    }
}