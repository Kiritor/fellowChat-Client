package util.tools;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Message {
	/**
	 * ���������ж�ȡһ��xml��Ϣ,��</msg>��β����
	 * 
	 * @return:�����ж�ȡ��һ��xml��Ϣ
	 */
	public static String readString(InputStream in) throws Exception {
		String msg = "";
		int i = in.read();// �������������ж�ȡ
		StringBuffer stb = new StringBuffer();// �ַ���������
		boolean end = false;
		while (!end) {
			char c = (char) i;
			stb.append(c);
			msg = stb.toString().trim();// ȥ����Ϣβ�Ŀո�
			if (msg.endsWith("</msg>")) {
				break;
			}
			i = in.read();// ������ȡ�ֽ�
		}
		msg = new String(msg.getBytes("ISO-8859-1"), "GBK");
		return msg;
	}

	/**
	 * ��һ��xmlMsg��Ϣ������ȡflagName��ǵ�ֵ,
	 * 
	 * @param flagName
	 *            :Ҫ��ȡ�ı�ǵ�����
	 * @param xmlMsg
	 *            :Ҫ������xml��Ϣ�ַ���
	 * @return:��ȡ��flagName��Ƕ�Ӧ��ֵ
	 * @throws:�������ʧ�ܣ�����xml��Ϣ��ʽ����Э��淶���׳��쳣
	 */
	public static String getXMLValue(String flagName, String xmlMsg) throws Exception {
		try {
			// 1.<���>ͷ���ֵ�λ��
			int start = xmlMsg.indexOf("<" + flagName + ">");
			start += flagName.length() + 2;// �������ƫ�Ƴ���
			// 2.</���>���������ֵ�λ��
			int end = xmlMsg.indexOf("</" + flagName + ">");
			// 3.��ȡ������������Ϣ��ֵ
			String value = xmlMsg.substring(start, end).trim();
			System.out.println("��ȡ"+value);
			return value;
		} catch (Exception ef) {
			throw new Exception("����" + flagName + "ʧ�ܣ�" + xmlMsg);
		}
	}
	/**
	 * ������Ϣ
	 * @param msg ��Ϣ����
	 * @param out �����
	 */
	public static void sendMsg(String msg,OutputStream out){
    	String completeMsg = "<msg>"+msg+"</msg>";
    	try{
    		out.write(completeMsg.getBytes());
    		out.flush();
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	/**
	 * �����ж�ȡ����
	 * @param in
	 */
	public static Object getObject(ObjectInputStream in){
		try{
			Object obj = in.readObject();
			return obj;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendObject(Object obj,ObjectOutputStream out){
		try{
			out.writeObject(obj);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
