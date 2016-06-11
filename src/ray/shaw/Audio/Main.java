package ray.shaw.Audio;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import ray.shaw.Thread.AddFilesThread;
import ray.shaw.Thread.ConnectionThread;
import ray.shaw.Thread.PostThread;

public class Main {

	public static void main(String[] args) {
		//需要传输的文件的队列
		Queue<String> filePath = new ArrayDeque<String>(); 
		//需要发送的ip的集合
		Set<String> IPs = new HashSet<String>();
		AddFilesThread  aft = new AddFilesThread(filePath);
		ConnectionThread ct = new ConnectionThread(IPs);
		PostThread pt = new PostThread(filePath,IPs);
		aft.start();
		ct.start();
		pt.start();
	}
}
