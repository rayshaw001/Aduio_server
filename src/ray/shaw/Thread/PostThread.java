package ray.shaw.Thread;

import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

public class PostThread extends Thread{
	private Queue<String> queue = null;
	private boolean flag = true;
	private Set<String> IPs =null;
	public PostThread(Queue<String> queue)
	{
		this.queue = queue;
	}
	
	public PostThread(Queue<String> queue,Set<String> IPs)
	{
		this.queue = queue;
		this.IPs = IPs;
	}
	public PostThread(Set<String> IPs)
	{
		this.IPs = IPs;
	}
	
	@Override
	public void run()
	{
		//发送资源
		DatagramPacket dp = null;
		DatagramSocket ds = null;
		String filename = null;
		FileInputStream fis = null;
		byte[] data = null;
		int count = 0;
		
		//结束标记
		String endSign = null;
		GregorianCalendar gc = null;
		
		//读取播放时间	以毫秒记
		Properties pro = null;
		FileInputStream fin = null;
		int millseconds = 0;
		try
		{
			pro = new Properties();
			fin = new FileInputStream(this.getClass().getResource("/").getPath() + 
					("settings.properties"));
			pro.load(fin);
			millseconds = Integer.valueOf(pro.getProperty("interval"));
			ds = new DatagramSocket(1234);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		while(flag)
		{
			if(queue !=null && queue.size()>0)
			{
				try
				{
					filename = queue.peek();
					fis = new FileInputStream(filename);
					count = 0;
					data = new byte[1024];
					while(count!=-1)
					{
						System.out.println("posting... data size is " + data.length);
						count = fis.read(data);
						System.out.println("count is " + count);
						dp = new DatagramPacket(data, data.length);
						dp.setPort(1423);
						for(String ip:IPs)
						{
							dp.setAddress(InetAddress.getByName(ip));
							ds.send(dp);
						}
					}
					//eg: exit:2016:01:01:24:59:59:111111111
					gc = new GregorianCalendar();
					endSign = "exit:";
					endSign += gc.get(GregorianCalendar.YEAR) + ":";
					endSign += (gc.get(GregorianCalendar.MONTH)) + ":";
					endSign += gc.get(GregorianCalendar.DAY_OF_MONTH) + ":";
					endSign += gc.get(GregorianCalendar.HOUR_OF_DAY) + ":";
					endSign += gc.get(GregorianCalendar.MINUTE) + ":";
					endSign += gc.get(GregorianCalendar.SECOND) + ":";
					endSign += (gc.get(GregorianCalendar.MILLISECOND) + millseconds);
					data = endSign.getBytes();
					dp = new DatagramPacket(data,data.length);
					dp.setPort(1423);
					for(String ip:IPs)
					{
						dp.setAddress(InetAddress.getByName(ip));
						ds.send(dp);
					}
					queue.poll();
					fis.close();
					data = null;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public Queue<String> getQueue() {
		return queue;
	}

	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}
}
