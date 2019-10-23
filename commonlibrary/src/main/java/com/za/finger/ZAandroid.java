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
	*���豸����
	*������
	*nDeviceType �豸���ͣ�0�������� USB �豸�� 1�������豸�� 2������ UDISK �豸,3�������� USB �豸+0x30�� 4�������豸+0x30�� 5������ UDISK �豸+0x30����(0x30  4.0�汾)
	*nDeviceType (����050)�豸���ͣ�10�������� USB �豸�� 11�������豸�� 12������ UDISK �豸,13�������� USB �豸+0x30�� 14�������豸+0x30�� 15������ UDISK �豸+0x30��
	*nDeviceType �豸�����£�20�������� USB �豸�� 21�������豸�� 22������ UDISK �豸,23�������� USB �豸+0x30�� 24�������豸+0x30�� 25������ UDISK �豸+0x30����(0x30  4.0�汾)
	*nDeviceType (����050��)�豸���ͣ�30�������� USB �豸�� 31�������豸�� 32������ UDISK �豸,33�������� USB �豸+0x30�� 34�������豸+0x30�� 35������ UDISK �豸+0x30��
	*iCom ���ںţ�1-16����USB �豸������ UDISK �豸�ò���Ϊ 0����(0-7   "/dev/ttySx",  10-17   "/dev/ttyMTx"  (xΪ0-7),  20-27   "/dev/ttysWKx"  (xΪ0-7),  30-37   "/dev/ttyHSLx"  (xΪ0-7)),	40-47   "/dev/ttyMAXx"  (xΪ0-7)),	50-57  "/dev/ttyAMAx"  (xΪ0-7)),	60-67
	*iBaud �����ʣ�9600-57600����USB �豸������ UDISK �豸�ò���Ϊ 0��������=iBaud*9600��
	*nPackageSize ͨѶ����С��Ĭ��:2��
	*iDevNum ͨѶ�˿ں�(Ĭ�� 0)
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
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
	*��ȡͼ����
	*��������0:256x288 1:256x360��
	*nAddr�� 0xffffffff
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZSetImageSize(int imagesize);
	
	/*******************************
	*�ر��豸����
	*������
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************/
	public native int ZAZCloseDeviceEx();
	/*********************************
	*��ȡͼ����
	*������
	*nAddr�� 0xffffffff
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZGetImage(int nAddr);
	/*********************************
	*���������뺯��
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID�� 0x01�� 0x02(����/��ѧ) 0x01�� 0x02�� 0x03�� 0x04(�β�)
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZGenChar(int nAddr,int iBufferID);
	/*********************************
	*5����ȷ�ȶԺ������ȶ� CharBufferA �� CharBufferB��
	*������
	*nAddr�� 0xffffffff ��
	*iScore��iscore[0] �ȶԺ�ķ���ֵ
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZMatch(int nAddr,int[] iScore);
	
	
	
	/*********************************
	*6�������ȶԺ������� CharBufferA �� CharBufferB �е������ļ����������򲿷�ָ�ƿ⣩
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID�� 0x01�� 0x02
	*iStartPage����ʼ ID��
	*iPageNum������ ID��
	*iMbAddress��iMbAddress[0]�����ɹ���ģ�� ID �ţ�
	*iscore��Ĭ�ϲ�����NULL��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZSearch(int nAddr,int iBufferID, int iStartPage, int iPageNum, int[] iMbAddress/*int *iscore=NULL*/);

	/*********************************
	*7���ϳ�ģ�庯��(�� CharBufferA �� CharBufferB �е������ļ��ϲ�����ģ����� ModelBuffer)
	*������
	*nAddr�� 0xffffffff ��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZRegModule(int nAddr);

	/********************************************************************
	*8���洢ģ�庯��(�� ModelBuffer �е��ļ����浽 flash ָ�ƿ���)
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID�� 0x01�� 0x02
	*iPageID��ģ��洢��ָ�ƿ��е� ID ��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	*********************************************************************/
	public native int ZAZStoreChar(int nAddr,int iBufferID, int iPageID);
	/*******************************************************************
	*9������ģ�庯��(�� flash ָ�ƿ��ж�ȡһ��ģ�嵽 ModelBuffer)
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID����ָ�ƿ��ж�����ģ������ŵ�����������(0x01�� 0x02)��
	*iPageID����ָ�ƿ��н�Ҫ������ָ��ģ�� ID ��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZLoadChar(int nAddr,int iBufferID,int iPageID);
	
	/*********************************
	*��������ֵ��С����
	*��������Ĭ��512��
	*
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	***********************************/
	public native int ZAZSetCharLen(int charLen);
	
	/*******************************************************************
	*10���ϴ���������
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID����Ҫ�ϴ���ģ������������(0x01�� 0x02)��
	*pTemplet��ָ��ģ�������ϴ���ŵĵ�ַ��
	*iTemplet[0]��ָ��ģ�����ݳ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZUpChar(int nAddr,int iBufferID, byte[] pTemplet,int[] iTempletLength);
	/*******************************************************************
	*11��������������
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID����ָ��ģ���������ص�������������(0x01�� 0x02)��
	*pTemplet�����ص�ָ��ģ�����ݵ�ַ��
	*iTempletLength�����ص�ָ��ģ�����ݳ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZDownChar(int nAddr,int iBufferID, byte[] pTemplet, int iTempletLength);
	/*******************************************************************
	*12���ϴ�ͼ����
	*������
	*nAddr�� 0xffffffff ��
	*pImageData���ϴ���ָ��ͼ�����ݵ�ַ��
	*pImageData[0]���ϴ���ָ��ͼ�����ݳ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZUpImage(int nAddr,byte[] pImageData,int[] iTempletLength);
	/*******************************************************************
	*13������ͼ����
	*������
	*nAddr�� 0xffffffff ��
	*pImageData�����ص�ָ��ͼ�����ݵ�ַ��
	*iImageLength�����ص�ָ��ͼ�����ݳ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZDownImage(int nAddr,byte[] pImageData, int iLength);
	/*******************************************************************
	*14��ͼ�����ݱ���� BMP ͼƬ����
	*������
	*pImageData���豣���ָ��ͼ�����ݵ�ַ��
	*pImageFile(str)�������ָ��ͼ���ļ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/

	public native int ZAZImgData2BMP(byte[] pImgData, String str);
	/*******************************************************************
	*15����ȡ BMP ͼ����ȡͼ�����ݺ���
	*������
	*pImageData����ȡ���ָ��ͼ�����ݵ�ַ��
	*pImageFile(str)����ȡ��ָ��ͼ���ļ�����
	*pnImageLen��ָ��ͼ�����ݳ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZGetImgDataFromBMP(String str, byte[] pImageData, int[] pnImageLen);

	/*******************************************************************
	*16��ɾ��ģ�庯��
	*������
	*nAddr�� 0xffffffff ��
	*iStartPageID����ɾ��ָ���������ʼ ID �ţ�
	*nDelPageNum����ɾ���Ĵ���ʼ ID ��ʼ��ģ�����
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZDelChar(int nAddr,int iStartPageID,int nDelPageNum);
	/*******************************************************************
	*17�����ָ�ƿ⺯��
	*������
	*nAddr�� 0xffffffff
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZEmpty(int nAddr);
	/*******************************************************************
	*18������������
	*������
	*nAddr�� 0xffffffff ��
	*pParTable��ϵͳ�������ݵĴ�ŵ�ַ
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZReadParTable(int nAddr,byte[] pParTable);
	/*******************************************************************
	*19�����ٱȶԺ���
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID�� (0x01�� 0x02)�Դ�������������ָ�������ļ��ȶ�ָ��ģ��⣻
	*iStartPage���ȶԵ�ָ��ģ�����ʼ ID��
	*iPageNum������ʼ ID ��ʼ��Ҫ�ȶԵ�ָ�ƿ��ָ��ģ�������
	*iMbAddress��id_iscore[0]�ȶԳɹ��󷵻صıȶԳɹ� ID �ţ� id_iscore[1](iscore)��Ĭ��ֵ
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZHighSpeedSearch(int nAddr,int iBufferID, int iStartPage, int iPageNum, int[] id_iscore);
	/*******************************************************************
	*20����ȡ��Чģ����������
	*������
	*nAddr�� 0xffffffff ��
	*iMbNum��ģ�����
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZTemplateNum(int nAddr,int[] iMbNum);
	/*******************************************************************
	*21�������豸���ֿ����
	*������
	*nAddr�� 0xffffffff ��
	*pPassword�����ֿ�������
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZSetPwd(int nAddr,byte[] pPassword);
	/*******************************************************************
	*22����֤�豸���ֿ����
	*������
	*nAddr�� 0xffffffff ��
	*pPassword�����ֿ�������
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZVfyPwd(int nAddr,byte[] pPassword);
	/*******************************************************************
	*23����ȡ�û���Ϣ����
	*������
	*nAddr�� 0xffffffff ��
	*nPage���û���Ϣҳ���� 512 ҳ��ÿҳ 32 �ֽڣ���
	*UserContent���û�������Ϣ��ַ
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZReadInfo(int nAddr,int nPage,byte[] UserContent);
	/*******************************************************************
	*24��д���û���Ϣ����
	*������
	*nAddr�� 0xffffffff ��
	*nPage���û���Ϣҳ���� 512 ҳ��ÿҳ 32 �ֽڣ���
	*UserContent����д����û�������Ϣ��ַ
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZWriteInfo(int nAddr,int nPage,byte[] UserContent);
	/*******************************************************************
	*25�����ò����ʺ���
	*������
	*nAddr�� 0xffffffff ��
	*nBaudNum�������õĲ����ʴ�С��9600-57600��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZSetBaud(int nAddr,int nBaudNum);
	/*******************************************************************
	*26�����ð�ȫ�ȼ�����
	*������
	*nAddr�� 0xffffffff ��
	*nLevel�������õİ�ȫ�ȼ���С��1-5��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZSetSecurLevel(int nAddr,int nLevel);
	/*******************************************************************
	*27���������ݰ���С����
	*������
	*nAddr�� 0xffffffff ��
	*nSize�������õ����ݰ���С��32/64/128/256��
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZSetPacketSize(int nAddr,int nSize);
	/*******************************************************************
	*28��ָ�������ϴ�������һ�� DAT �ļ�����
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID�� (0x01�� 0x02)�ϴ���ָ��������������
	*pFileName�������ļ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZUpChar2File(int nAddr,int iBufferID, byte[] pFileName);

	/*******************************************************************
	*29�� DAT �ļ����غ���
	*������
	*nAddr�� 0xffffffff ��
	*iBufferID�� (0x01�� 0x02)���ص�ָ��ģ���豸��ָ��������������
	*pFileName�����ص������ļ���
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZDownCharFromFile(int nAddr,int iBufferID, byte[] pFileName);
	/*******************************************************************
	*30����ȡ���������
	*������
	*nAddr�� 0xffffffff ��
	*pRandom���������ŵ�ַ
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZGetRandomData(int nAddr,byte[] pRandom);

	/*******************************************************************
	*31������оƬ��ַ����
	*������
	*nAddr�� 0xffffffff ��
	*pChipAddr�������õ�оƬ��ַ����
	*����ֵ��
	*0 Ϊ�ɹ�����������ֵ��ο����󷵻���
	********************************************************************/
	public native int ZAZSetChipAddr(int nAddr,byte[] pChipAddr);
	
	/**************BT_REV******************/
	public native int ZAZBT_rev(byte[] pTemplet, int iTempletLength);
	
	/*******************************************************************
	*
	*��ȡ�汾��
	*
	********************************************************************/
	public native int ZAZReadInfPage(int nAddr,byte[] pVersion);
	
	
	static {
		System.loadLibrary("ZAandroid");
    }
}
