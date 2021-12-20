package com.baseeasy.commonlibrary.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.baseeasy.commonlibrary.R;


/**
 * @author：Mr.Zan
 * @date： 2020/10/16 17:44
 * email：644962006@qq.com
 * detail：数字输入，并限制小数点后位数
 */
@SuppressLint("AppCompatCustomView")
public class NumberEditText extends EditText {
	private  int maxDecimalPoint=2;
	 public NumberEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public NumberEditText(Context context, AttributeSet attrs) {
		super(context,attrs);
		changeText(context,attrs);
		setTextWatcher();
		setFocusChange();
	}

	public void changeText(Context context, AttributeSet attrs) {

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberEditText);
		maxDecimalPoint = array.getInt(R.styleable.NumberEditText_maxDecimalPoint, 2);
		array.recycle();

	}
	public void setTextWatcher() {
		addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String s1 = s.toString().trim();
				if (s1.length()>1 &&s1.startsWith("0")){
					String s2 = s1.substring(1);
					setText(s2);
					setSelection(s2.length());
				}
				if (start >= 0) {//从一输入就开始判断，
						try {
							if (s1.startsWith(".")){
								setText("0.");
								setSelection(s.length()+1);
							}else if (s1.contains(".")&&(s1.length()- s1.indexOf(".")>(maxDecimalPoint+1))){
								setText(s1.substring(0,s.length()-1));
								setSelection(s.length()-1);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						return;
					}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	public void setFocusChange(){
	 	setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					String trim = getText().toString().trim();
					while (trim.contains(".")&&(trim.endsWith("0") ||trim.endsWith("."))){
						trim=trim.substring(0,trim.length()-1);
						setText(trim);
					}
				}
			}
		});
	}
}