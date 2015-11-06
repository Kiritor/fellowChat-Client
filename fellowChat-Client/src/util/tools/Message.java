package util.tools;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Message {
	/**
	 * 从输入流中读取一条xml消息,以</msg>结尾即是
	 * 
	 * @return:从流中读取的一条xml消息
	 */
	public static String readString(InputStream in) throws Exception {
		String msg = "";
		int i = in.read();// 从输入流对象中读取
		StringBuffer stb = new StringBuffer();// 字符串缓冲区
		boolean end = false;
		while (!end) {
			char c = (char) i;
			stb.append(c);
			msg = stb.toString().trim();// 去除消息尾的空格
			if (msg.endsWith("</msg>")) {
				break;
			}
			i = in.read();// 继续读取字节
		}
		msg = new String(msg.getBytes("ISO-8859-1"), "GBK");
		return msg;
	}

	/**
	 * 从一条xmlMsg消息串中提取flagName标记的值,
	 * 
	 * @param flagName
	 *            :要提取的标记的名字
	 * @param xmlMsg
	 *            :要解析的xml消息字符串
	 * @return:提取到flagName标记对应的值
	 * @throws:如果解析失败，则是xml消息格式不符协议规范，抛出异常
	 */
	public static String getXMLValue(String flagName, String xmlMsg) throws Exception {
		try {
			// 1.<标记>头出现的位置
			int start = xmlMsg.indexOf("<" + flagName + ">");
			start += flagName.length() + 2;// 计算向后偏移长度
			// 2.</标记>结束符出现的位置
			int end = xmlMsg.indexOf("</" + flagName + ">");
			// 3.截取标记所代表的消息的值
			String value = xmlMsg.substring(start, end).trim();
			System.out.println("截取"+value);
			return value;
		} catch (Exception ef) {
			throw new Exception("解析" + flagName + "失败：" + xmlMsg);
		}
	}
	/**
	 * 发送消息
	 * @param msg 消息内容
	 * @param out 输出流
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
	 * 从流中读取对象
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
