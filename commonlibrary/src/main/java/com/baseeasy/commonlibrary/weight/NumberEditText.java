package com.baseeasy.commonlibrary.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
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
				if (start >= 0) {//从一输入就开始判断，
						try {
							if (s.toString().startsWith(".")){
								setText("0.");
								setSelection(s.length()+1);
							}else if (s.toString().contains(".")&&(s.toString().length()-s.toString().indexOf(".")>(maxDecimalPoint+1))){
								setText(s.toString().substring(0,s.length()-1));
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



}