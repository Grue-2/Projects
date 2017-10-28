package jc.padding;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class View {
	@FXML Button encrypt;
	@FXML Button decrypt;
	@FXML TextField keyYou;
	@FXML TextField keyThem;
	@FXML TextField eText;
	@FXML TextField dText;
	
	@FXML public Object encrypt(){
		String result=convertTextToBinary(eText.getText());
		int amount=result.length();
		result=pad(keyYou.getText(),result);
		keyYou.setText(keyYou.getText().substring(amount));
		eText.setText(binaryStringToShortFormat(result));	
		return null;
	}
	@FXML public Object decrypt(){
		String result=convertShortFormatToBinaryString(dText.getText());
		int amount=result.length();
		result=pad(keyThem.getText(),result);
		keyThem.setText(keyThem.getText().substring(amount));
		dText.setText(convertBinaryStringToText(result));
		return null;
	}
	
	private String convertBinaryStringToText(String text){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=text.length();i<len;i+=5){
			switch(text.substring(i,i+5).toLowerCase()){
				case "00000":
					result.append("a");
					break;
				case "00001":
					result.append("b");
					break;
				case "00010":
					result.append("c");
					break;
				case "00011":
					result.append("d");
					break;
				case "00100":
					result.append("e");
					break;
				case "00101":
					result.append("f");
					break;
				case "00110":
					result.append("g");
					break;
				case "00111":
					result.append("h");
					break;
				case "01000":
					result.append("i");
					break;
				case "01001":
					result.append("j");
					break;
				case "01010":
					result.append("k");
					break;
				case "01011":
					result.append("l");
					break;
				case "01100":
					result.append("m");
					break;
				case "01101":
					result.append("n");
					break;
				case "01110":
					result.append("o");
					break;
				case "01111":
					result.append("p");
					break;
				case "10000":
					result.append("q");
					break;
				case "10001":
					result.append("r");
					break;
				case "10010":
					result.append("s");
					break;
				case "10011":
					result.append("t");
					break;
				case "10100":
					result.append("u");
					break;
				case "10101":
					result.append("v");
					break;
				case "10110":
					result.append("w");
					break;
				case "10111":
					result.append("x");
					break;
				case "11000":
					result.append("y");
					break;
				case "11001":
					result.append("z");
					break;
				case "11010":
					result.append(" ");
					break;
				case "11011":
					result.append(".");
					break;
				case "11100":
					result.append(",");
					break;
			}
		}
		return result.toString();
	}
	
	
	private String convertShortFormatToBinaryString(String shortFormatString){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=shortFormatString.length();i<len;i++){
			switch(shortFormatString.substring(i,i+1)){
				case "0":
					result.append("00000");
					break;
				case "1":
					result.append("00001");
					break;
				case "2":
					result.append("00010");
					break;
				case "3":
					result.append("00011");
					break;
				case "4":
					result.append("00100");
					break;
				case "5":
					result.append("00101");
					break;
				case "6":
					result.append("00110");
					break;
				case "7":
					result.append("00111");
					break;
				case "8":
					result.append("01000");
					break;
				case "9":
					result.append("01001");
					break;
				case "a":
					result.append("01010");
					break;
				case "b":
					result.append("01011");
					break;
				case "c":
					result.append("01100");
					break;
				case "d":
					result.append("01101");
					break;
				case "e":
					result.append("01110");
					break;
				case "f":
					result.append("01111");
					break;
				case "g":
					result.append("10000");
					break;
				case "h":
					result.append("10001");
					break;
				case "i":
					result.append("10010");
					break;
				case "j":
					result.append("10011");
					break;
				case "k":
					result.append("10100");
					break;
				case "l":
					result.append("10101");
					break;
				case "m":
					result.append("10110");
					break;
				case "n":
					result.append("10111");
					break;
				case "o":
					result.append("11000");
					break;
				case "p":
					result.append("11001");
					break;
				case "q":
					result.append("11010");
					break;
				case "r":
					result.append("11011");
					break;
				case "s":
					result.append("11100");
					break;
				case "t":
					result.append("11101");
					break;
				case "u":
					result.append("11110");
					break;
				case "v":
					result.append("11111");
					break;
			}
		}
		return result.toString();
	}
	
	private String binaryStringToShortFormat(String binaryString){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=binaryString.length();i<len;i+=5){
			switch(binaryString.substring(i,i+5)){
				case "00000":
					result.append("0");
					break;
				case "00001":
					result.append("1");
					break;
				case "00010":
					result.append("2");
					break;
				case "00011":
					result.append("3");
					break;
				case "00100":
					result.append("4");
					break;
				case "00101":
					result.append("5");
					break;
				case "00110":
					result.append("6");
					break;
				case "00111":
					result.append("7");
					break;
				case "01000":
					result.append("8");
					break;
				case "01001":
					result.append("9");
					break;
				case "01010":
					result.append("a");
					break;
				case "01011":
					result.append("b");
					break;
				case "01100":
					result.append("c");
					break;
				case "01101":
					result.append("d");
					break;
				case "01110":
					result.append("e");
					break;
				case "01111":
					result.append("f");
					break;
				case "10000":
					result.append("g");
					break;
				case "10001":
					result.append("h");
					break;
				case "10010":
					result.append("i");
					break;
				case "10011":
					result.append("j");
					break;
				case "10100":
					result.append("k");
					break;
				case "10101":
					result.append("l");
					break;
				case "10110":
					result.append("m");
					break;
				case "10111":
					result.append("n");
					break;
				case "11000":
					result.append("o");
					break;
				case "11001":
					result.append("p");
					break;
				case "11010":
					result.append("q");
					break;
				case "11011":
					result.append("r");
					break;
				case "11100":
					result.append("s");
					break;
				case "11101":
					result.append("t");
					break;
				case "11110":
					result.append("u");
					break;
				case "11111":
					result.append("v");
					break;
			}
		}
		return result.toString();
	}
	
	private String pad(String key,String text){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=text.length();i<len;i++){
			if(key.substring(i,i+1).equals(text.substring(i,i+1)))result.append("0");
			else result.append("1");
		}
		return result.toString();
	}
	private String convertTextToBinary(String text){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=text.length();i<len;i++){
			switch(text.substring(i,i+1).toLowerCase()){
				case "a":
					result.append("00000");
					break;
				case "b":
					result.append("00001");
					break;
				case "c":
					result.append("00010");
					break;
				case "d":
					result.append("00011");
					break;
				case "e":
					result.append("00100");
					break;
				case "f":
					result.append("00101");
					break;
				case "g":
					result.append("00110");
					break;
				case "h":
					result.append("00111");
					break;
				case "i":
					result.append("01000");
					break;
				case "j":
					result.append("01001");
					break;
				case "k":
					result.append("01010");
					break;
				case "l":
					result.append("01011");
					break;
				case "m":
					result.append("01100");
					break;
				case "n":
					result.append("01101");
					break;
				case "o":
					result.append("01110");
					break;
				case "p":
					result.append("01111");
					break;
				case "q":
					result.append("10000");
					break;
				case "r":
					result.append("10001");
					break;
				case "s":
					result.append("10010");
					break;
				case "t":
					result.append("10011");
					break;
				case "u":
					result.append("10100");
					break;
				case "v":
					result.append("10101");
					break;
				case "w":
					result.append("10110");
					break;
				case "x":
					result.append("10111");
					break;
				case "y":
					result.append("11000");
					break;
				case "z":
					result.append("11001");
					break;
				case " ":
					result.append("11010");
					break;
				case ".":
					result.append("11011");
					break;
				case ",":
					result.append("11100");
					break;
			}
		}
		return result.toString();
	}
	
}

/*
public static void main(String...args){
	String key="0101010101010101010101010101010100110101010"+
				"111000100010000101010000100110100001000100"+
				"110001110000011111001001000100010000100010"+
				"1111000001010100101010010001000010101010010"+
				"1110001001000100001001010001000100010010101";
	String pt="ab";
	pt=new View().convertTextToBinary(pt);
	System.out.println(pt);
	pt=new View().pad(key,pt);
	System.out.println(pt);
	pt=new View().binaryStringToShortFormat(pt);
	System.out.println(pt);
}*/
