package com.za.finger;

public class ZAandroid {
	
	public final int VendorId0 = 0x2109;
	public final int ProductId0 = 0x7638;
	public final int VendorId1 = 0x2009;
	public final int ProductId1 = 0x7638;
	
	
	public final int PS_OK = 0x00;
	public final int PS_COMM_ERR = 0x01;
	public final int PS_NO_FINGER = 0x02;
	public final int PS_GET_IMG_ERR = 0x03;
	public final int PS_FP_TOO_DRY = 0x04;
	public final int PS_FP_TOO_WET = 0x05;
	public final int PS_FP_DISORDER = 0x06;
	public final int PS_LITTLE_FEATURE = 0x07;
	public final int PS_NOT_MATCH = 0x08;
	public final int PS_NOT_SEARCHED = 0x09;
	public final int PS_MERGE_ERR = 0x0a;
	public final int PS_ADDRESS_OVER = 0x0b;
	public final int PS_READ_ERR = 0x0c;
	public final int PS_UP_TEMP_ERR = 0x0d;
	public final int PS_RECV_ERR = 0x0e;
	public final int PS_UP_IMG_ERR = 0x0f;
	public final int PS_DEL_TEMP_ERR = 0x10;
	public final int PS_CLEAR_TEMP_ERR = 0x11;
	public final int PS_SLEEP_ERR = 0x12;
	public final int PS_INVALID_PASSWORD = 0x13;
	public final int PS_RESET_ERR = 0x14;
	public final int PS_INVALID_IMAGE = 0x15;
	public final int PS_HANGOVER_UNREMOVE = 0x17;

	public final int CHAR_BUFFER_A = 0x01;
	public final int CHAR_BUFFER_B = 0x02;
	public final int MODEL_BUFFER = 0x03;
	
	public native String init();
	public native int  card_power_on();
	public native int  card_power_off(int x,int y);
	public native int  finger_power_on();
	public native int  finger_power_off();
	
	/*****************************
	*打开设备函数
	*参数：
	*nDeviceType 设备类型（0：有驱动 USB 设备， 1：串口设备， 2：无驱 UDISK 设备,3：有驱动 USB 设备+0x30， 4：串口设备+0x30， 5：无驱 UDISK 设备+0x30）；(0x30  4.0版本)
	*nDeviceType (兼容050)设备类型（10：有驱动 USB 设备， 11：串口设备， 12：无驱 UDISK 设备,13：有驱动 USB 设备+0x30， 14：串口设备+0x30， 15：无驱 UDISK 设备+0x30）
	*nDeviceType 设备类型新（20：有驱动 USB 设备， 21：串口设备， 22：无驱 UDISK 设备,23：有驱动 USB 设备+0x30， 24：串口设备+0x30， 25：无驱 UDISK 设备+0x30）；(0x30  4.0版本)
	*nDeviceType (兼容050新)设备类型（30：有驱动 USB 设备， 31：串口设备， 32：无驱 UDISK 设备,33：有驱动 USB 设备+0x30， 34：串口设备+0x30， 35：无驱 UDISK 设备+0x30）
	*iCom 串口号（1-16）（USB 设备和无驱 UDISK 设备该参数为 0）；(0-7   "/dev/ttySx",  10-17   "/dev/ttyMTx"  (x为0-7),  20-27   "/dev/ttysWKx"  (x为0-7),  30-37   "/dev/ttyHSLx"  (x为0-7)),	40-47   "/dev/ttyMAXx"  (x为0-7)),	50-57  "/dev/ttyAMAx"  (x为0-7)),	60-67
	*iBaud 波特率（9600-57600）（USB 设备和无驱 UDISK 设备该参数为 0）波特率=iBaud*9600；
	*nPackageSize 通讯包大小（默认:2）
	*iDevNum 通讯端口号(默认 0)
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	*****************************/
	public  int ZAZOpenDevice(int fd,int nDeviceType,int iCom,int iBaud,int nPackageSize/*=2*/,int iDevNum/*=0*/)
	{
		int ret = -1;
		ret = ZAZOpenDeviceEx(fd,nDeviceType,iCom,iBaud,nPackageSize,iDevNum);
		if(ret == 1)
			return 0;
		else
			return -2;
	}
	
	public native int ZAZOpenDeviceEx(int fd,int nDeviceType,int iCom,int iBaud,int nPackageSize/*=2*/,int iDevNum/*=0*/);
	/*********************************
	*获取图像函数
	*参数：（0:256x288 1:256x360）
	*nAddr： 0xffffffff
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZSetImageSize(int imagesize);
	
	/*******************************
	*关闭设备函数
	*参数：
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************/
	public native int ZAZCloseDeviceEx();
	/*********************************
	*获取图像函数
	*参数：
	*nAddr： 0xffffffff
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZGetImage(int nAddr);
	/*********************************
	*生成特征码函数
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID： 0x01、 0x02(电容/光学) 0x01、 0x02、 0x03、 0x04(刮擦)
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZGenChar(int nAddr,int iBufferID);
	/*********************************
	*5、精确比对函数（比对 CharBufferA 与 CharBufferB）
	*参数：
	*nAddr： 0xffffffff ；
	*iScore：iscore[0] 比对后的分数值
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZMatch(int nAddr,int[] iScore);
	
	
	
	/*********************************
	*6、搜索比对函数（以 CharBufferA 或 CharBufferB 中的特征文件搜索整个或部分指纹库）
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID： 0x01、 0x02
	*iStartPage：起始 ID；
	*iPageNum：结束 ID；
	*iMbAddress：iMbAddress[0]搜索成功后模板 ID 号；
	*iscore：默认参数（NULL）
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZSearch(int nAddr,int iBufferID, int iStartPage, int iPageNum, int[] iMbAddress/*int *iscore=NULL*/);

	/*********************************
	*7、合成模板函数(将 CharBufferA 与 CharBufferB 中的特征文件合并生成模板存于 ModelBuffer)
	*参数：
	*nAddr： 0xffffffff ；
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZRegModule(int nAddr);

	/********************************************************************
	*8、存储模板函数(将 ModelBuffer 中的文件储存到 flash 指纹库中)
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID： 0x01、 0x02
	*iPageID：模板存储到指纹库中的 ID 号
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	*********************************************************************/
	public native int ZAZStoreChar(int nAddr,int iBufferID, int iPageID);
	/*******************************************************************
	*9、读出模板函数(从 flash 指纹库中读取一个模板到 ModelBuffer)
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID：从指纹库中读出的模板所存放的特征缓冲区(0x01、 0x02)；
	*iPageID：在指纹库中将要读出的指纹模板 ID 号
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZLoadChar(int nAddr,int iBufferID,int iPageID);
	
	/*********************************
	*设置特征值大小函数
	*参数：（默认512）
	*
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	***********************************/
	public native int ZAZSetCharLen(int charLen);
	
	/*******************************************************************
	*10、上传特征函数
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID：将要上传的模板特征缓冲区(0x01、 0x02)；
	*pTemplet：指纹模板数据上传存放的地址；
	*iTemplet[0]：指纹模板数据长度
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZUpChar(int nAddr,int iBufferID, byte[] pTemplet,int[] iTempletLength);
	/*******************************************************************
	*11、下载特征函数
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID：将指纹模板数据下载到的特征缓冲区(0x01、 0x02)；
	*pTemplet：下载的指纹模板数据地址；
	*iTempletLength：下载的指纹模板数据长度
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZDownChar(int nAddr,int iBufferID, byte[] pTemplet, int iTempletLength);
	/*******************************************************************
	*12、上传图像函数
	*参数：
	*nAddr： 0xffffffff ；
	*pImageData：上传的指纹图像数据地址；
	*pImageData[0]：上传的指纹图像数据长度
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZUpImage(int nAddr,byte[] pImageData,int[] iTempletLength);
	/*******************************************************************
	*13、下载图像函数
	*参数：
	*nAddr： 0xffffffff ；
	*pImageData：下载的指纹图像数据地址；
	*iImageLength：下载的指纹图像数据长度
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZDownImage(int nAddr,byte[] pImageData, int iLength);
	/*******************************************************************
	*14、图像数据保存成 BMP 图片函数
	*参数：
	*pImageData：需保存的指纹图像数据地址；
	*pImageFile(str)：保存的指纹图像文件名
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/

	public native int ZAZImgData2BMP(byte[] pImgData, String str);
	/*******************************************************************
	*15、读取 BMP 图像提取图像数据函数
	*参数：
	*pImageData：读取后的指纹图像数据地址；
	*pImageFile(str)：读取的指纹图像文件名；
	*pnImageLen：指纹图像数据长度
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZGetImgDataFromBMP(String str, byte[] pImageData, int[] pnImageLen);

	/*******************************************************************
	*16、删除模板函数
	*参数：
	*nAddr： 0xffffffff ；
	*iStartPageID：需删除指纹区域的起始 ID 号；
	*nDelPageNum：需删除的从起始 ID 开始的模板个数
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZDelChar(int nAddr,int iStartPageID,int nDelPageNum);
	/*******************************************************************
	*17、清空指纹库函数
	*参数：
	*nAddr： 0xffffffff
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZEmpty(int nAddr);
	/*******************************************************************
	*18、读参数表函数
	*参数：
	*nAddr： 0xffffffff ；
	*pParTable：系统参数数据的存放地址
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZReadParTable(int nAddr,byte[] pParTable);
	/*******************************************************************
	*19、快速比对函数
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID： (0x01、 0x02)以此特征缓冲区的指纹特征文件比对指纹模板库；
	*iStartPage：比对的指纹模板库起始 ID；
	*iPageNum：从起始 ID 开始将要比对的指纹库的指纹模板个数；
	*iMbAddress：id_iscore[0]比对成功后返回的比对成功 ID 号； id_iscore[1](iscore)：默认值
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZHighSpeedSearch(int nAddr,int iBufferID, int iStartPage, int iPageNum, int[] id_iscore);
	/*******************************************************************
	*20、读取有效模板数量函数
	*参数：
	*nAddr： 0xffffffff ；
	*iMbNum：模板个数
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZTemplateNum(int nAddr,int[] iMbNum);
	/*******************************************************************
	*21、设置设备握手口令函数
	*参数：
	*nAddr： 0xffffffff ；
	*pPassword：握手口令数据
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZSetPwd(int nAddr,byte[] pPassword);
	/*******************************************************************
	*22、验证设备握手口令函数
	*参数：
	*nAddr： 0xffffffff ；
	*pPassword：握手口令数据
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZVfyPwd(int nAddr,byte[] pPassword);
	/*******************************************************************
	*23、读取用户信息函数
	*参数：
	*nAddr： 0xffffffff ；
	*nPage：用户信息页（共 512 页，每页 32 字节）；
	*UserContent：用户数据信息地址
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZReadInfo(int nAddr,int nPage,byte[] UserContent);
	/*******************************************************************
	*24、写入用户信息函数
	*参数：
	*nAddr： 0xffffffff ；
	*nPage：用户信息页（共 512 页，每页 32 字节）；
	*UserContent：需写入的用户数据信息地址
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZWriteInfo(int nAddr,int nPage,byte[] UserContent);
	/*******************************************************************
	*25、设置波特率函数
	*参数：
	*nAddr： 0xffffffff ；
	*nBaudNum：需设置的波特率大小（9600-57600）
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZSetBaud(int nAddr,int nBaudNum);
	/*******************************************************************
	*26、设置安全等级函数
	*参数：
	*nAddr： 0xffffffff ；
	*nLevel：需设置的安全等级大小（1-5）
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZSetSecurLevel(int nAddr,int nLevel);
	/*******************************************************************
	*27、设置数据包大小函数
	*参数：
	*nAddr： 0xffffffff ；
	*nSize：需设置的数据包大小（32/64/128/256）
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZSetPacketSize(int nAddr,int nSize);
	/*******************************************************************
	*28、指纹数据上传并生成一个 DAT 文件函数
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID： (0x01、 0x02)上传的指纹特征缓冲区；
	*pFileName：特征文件名
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZUpChar2File(int nAddr,int iBufferID, byte[] pFileName);

	/*******************************************************************
	*29、 DAT 文件下载函数
	*参数：
	*nAddr： 0xffffffff ；
	*iBufferID： (0x01、 0x02)下载到指纹模块设备的指纹特征缓冲区；
	*pFileName：下载的特征文件名
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZDownCharFromFile(int nAddr,int iBufferID, byte[] pFileName);
	/*******************************************************************
	*30、获取随机数函数
	*参数：
	*nAddr： 0xffffffff ；
	*pRandom：随机数存放地址
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZGetRandomData(int nAddr,byte[] pRandom);

	/*******************************************************************
	*31、设置芯片地址函数
	*参数：
	*nAddr： 0xffffffff ；
	*pChipAddr：需设置的芯片地址数据
	*返回值：
	*0 为成功，其它返回值请参考错误返回码
	********************************************************************/
	public native int ZAZSetChipAddr(int nAddr,byte[] pChipAddr);
	
	/**************BT_REV******************/
	public native int ZAZBT_rev(byte[] pTemplet, int iTempletLength);
	
	/*******************************************************************
	*
	*获取版本号
	*
	********************************************************************/
	public native int ZAZReadInfPage(int nAddr,byte[] pVersion);
	
	
	static {
		System.loadLibrary("ZAandroid");
    }
}
