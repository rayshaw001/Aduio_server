package ray.shaw.Thread;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.Scanner;

public class AddFilesThread extends Thread{
	private Queue<String> queue = null;
	private boolean flag = true;
	public AddFilesThread()
	{
		
	}
	public AddFilesThread(Queue<String> queue)
	{
		this.queue = queue;
	}
	
	@Override
	public void run()
	{
		Scanner s = new Scanner(System.in);	//filepath input
		String filepath = null;
		//目前通过console读取文件目录
		while(flag)
		{
			System.out.println("input a suitable path,eg: C:\\\\temp\\\\a.wav");
			try{
				filepath = s.nextLine();
				if(Files.isDirectory(
						Paths.get(
						filepath.substring(
						0,filepath.lastIndexOf(
						System.getProperty("file.separator"))))))
				{
					queue.add(filepath);
				}
				else
				{
					System.out.println(filepath + " is not a suitable path");
					
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				continue;
			}
		}
		s.close();
	}
	
	public Queue<String> getQueue() {
		return queue;
	}

	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
