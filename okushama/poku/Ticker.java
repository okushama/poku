package okushama.poku;

public abstract class Ticker implements Runnable{
	
	private Thread tickThread = new Thread(this);
	private boolean isRunning = false;
	private long threadSleep = 20;
	public void create(long sleepTime){
		if(isRunning) return;
		isRunning = true;
		threadSleep = sleepTime;
		tickThread.start();
		
	}
	
	public void destroy(){
		isRunning = false;
	}
	
	@Override
	public void run(){
		while(isRunning){			
			try{
				onTick();
				Thread.sleep(threadSleep);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public abstract void onTick();

}
