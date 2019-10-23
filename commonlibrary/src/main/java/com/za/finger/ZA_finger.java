/**
 * 
 */
/**
 * @author zhaohongwei
 *
 */
package com.za.finger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//ZA_finger��ZAndroid ����ŵ�com.finger��
public class ZA_finger {
	
	final String HUB_RST_PATCH="/sys/zhwpower/zhw_hubrest";
	final String CARD_POWER_PATCH="/sys/zhwpower/zhw_power_card";
	final String FINGER_POWER_PATCH="/sys/zhwpower/zhw_power_finger";
	final String DOOR1_POWER_PATCH="/sys/zhwpower/zhw_power_door1";
	final String DOOR2_POWER_PATCH="/sys/zhwpower/zhw_power_door2";
	
	public int IO_Switch(String cardpowerPath, int on){
		try{
			
			File powerFile = new File(cardpowerPath);
			if(!powerFile.exists()){
				
				return 0;
				
			}
			BufferedWriter bufWriter = new BufferedWriter(new FileWriter(powerFile));
			bufWriter.write(Integer.toString(on));
			bufWriter.close();
			return 1;
			
		} catch (IOException e) {
			e.printStackTrace();  return 0;
		}
		
	}
	
	public static String SerialPortnum(){
	
		String cardpowerPath = "/sys/zhwpower/zhw_id";
		String str = null;
		
		try{
			
			File powerFile = new File(cardpowerPath);
			if(!powerFile.exists()){
				return null;
				
			}
			
			BufferedReader bufReader = new BufferedReader(new FileReader(powerFile));
			//bufWriter = new BufferedWriter(new FileWriter(powerFile));
			str=bufReader.readLine();
			bufReader.close();
			
			if(str.equals("0")){
				str="/dev/ttyS0";
				
			}else if(str.equals("1")){
				str="/dev/ttyS1";
				
			}else if(str.equals("2")){
				
				str="/dev/ttyS2";
				
			}else if(str.equals("3")){
				
				str="/dev/ttyS3";
				
			}else if(str.equals("4")){
				
				str="/dev/ttyS4";
				
			}else{
				str="/dev/ttyS0";
				
			}
			return str;
			
		} catch (IOException e) {
			
			e.printStackTrace();  return null;
			
		}
		
	}
	
	/**
	 * �����֤ģ���Դ
	 */
	public   int  card_power_on()
	{
		return IO_Switch(CARD_POWER_PATCH,1);

	}
	/**
	 * �ر����֤ģ���Դ
	 */
	public   int  card_power_off() 
	{
		return IO_Switch(CARD_POWER_PATCH,0);	
	}
	/**
	 * ��ָ��ģ���Դ
	 */
	public   int  finger_power_on()
	{
		return IO_Switch(FINGER_POWER_PATCH,1);
	}
	/**
	 * �ر�ָ��ģ���Դ
	 */
	public   int  finger_power_off()
	{
		return IO_Switch(FINGER_POWER_PATCH,0);
	}

	/**
	 * USB hub ��λ
	 */
	public   int  hub_rest(int Ms)
	{
		
		IO_Switch(HUB_RST_PATCH,0);
		try {
			Thread.sleep(Ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IO_Switch(HUB_RST_PATCH,1);
		return 0;	

	}
	
	/**
	 * ���Ž�1��Դ
	 */
	public   int  door1_power_on()
	{
		return IO_Switch(DOOR1_POWER_PATCH,1);
	}
	/**
	 * �ر��Ž�1��Դ
	 */
	public   int  door1_power_off()
	{
		return IO_Switch(DOOR1_POWER_PATCH,0);
	}
	
	/**
	 * ���Ž�2��Դ
	 */
	public   int  door2_power_on()
	{
		return IO_Switch(DOOR2_POWER_PATCH,1);
	}
	/**
	 * �ر��Ž�2��Դ
	 */
	public   int  door2_power_off()
	{
		return IO_Switch(DOOR2_POWER_PATCH,0);
	}
	
	
}