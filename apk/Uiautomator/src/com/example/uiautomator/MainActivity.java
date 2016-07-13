package com.example.uiautomator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView tv_show;
	private Button bt_one;
	private Button bt_two;
	private Button bt_third;
	private Button errorbt;
	private int cout = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_show = (TextView) findViewById(R.id.tv_show);
		bt_one = (Button) findViewById(R.id.bt_one);
		bt_two = (Button) findViewById(R.id.bt_two);
		bt_third = (Button) findViewById(R.id.bt_third);
		// /
		bt_one.setOnClickListener(this);
		bt_two.setOnClickListener(this);
		bt_third.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		cout=cout+1;
		if(cout>=100){
			errorbt.setVisibility(View.GONE);//当点击按钮的次数总共超过100次后会出现空指针异常
		}
		switch (view.getId()) {
		case R.id.bt_one:
			tv_show.setText("FirstButton click------点击按钮总次数："+cout);
            
			break;

		case R.id.bt_two:
			tv_show.setText("第二个按钮 click------点击按钮总次数："+cout);

			break;

		case R.id.bt_third:
			tv_show.setText("thirdButton click------点击按钮总次数："+cout);
			break;

		default:
			break;
		}

	}

}
