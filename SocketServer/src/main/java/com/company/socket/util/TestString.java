package com.company.socket.util;

public class TestString {
	public static void main(String[] args) {
		int a = format(1);
		System.out.println(a);
		
	}
	public static int format(int i){
		switch(i){
		case 1:
			return i+1;
		case 2:
			return i+2;
		default:
				return i+3;
		
		}
	}
}
