package com.app.comparator;

public class ASortModel extends Contact {


	public ASortModel(String name, String number, String sortKey) {
		super(name, number, sortKey);
	}

	public String sortLetters; //显示数据拼音的首字母

	public ASortToken sortToken=new ASortToken();
}
