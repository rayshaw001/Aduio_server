package ray.shaw.Thread;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;

public class ConnectionThread extends Thread{
	private Set<String> IPs = null;
	private boolean flag = true;
	
	@Override
	public void run()
	{
		DatagramPacket dp = null;
		DatagramSocket ds = null;
		String data = null;
		byte[] ip = null;
		String iptosave = null;
		try{
			ip = new byte[20];
			dp = new DatagramPacket(ip, 20);
			ds = new DatagramSocket(1342);
			ip = new byte[1024];
			while(flag)
			{
				ds.receive(dp);
				ip = dp.getData();
				data = new String(ip);
				iptosave = data.substring(0,data.lastIndexOf("."));
				
				System.out.println("IP: " + iptosave + " recived...");
				if(IPs.add(iptosave)==false)
				{
					IPs.remove(iptosave);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public Set<String> getIPs() {
		return IPs;
	}

	public void setIPs(Set<String> iPs) {
		IPs = iPs;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public ConnectionThread(Set<String> IPs){
		this.IPs = IPs;
	}
	
}
