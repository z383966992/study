package Elevator;

//java 模拟电梯
public class Elevator {
	
	
	private int totalFloor;
	
	private int currentFloor;

	private int destFloor;
	
	public Elevator(int totalFloor) {
		this.totalFloor = totalFloor;
		this.currentFloor = 1;
	}
	
	//上行
	public void up() throws Exception{
		
		for (int i=currentFloor; i<=destFloor; i++) {
			Thread.sleep(1000);
			System.out.println("电梯运行到 ： " + i + "层");
			setCurrentFloor(i);
		}
		if (currentFloor == destFloor) {
			open();
			Thread.sleep(1000);
			close();
		}
	}
	
	//下行
	public void down() throws Exception{
		
		for(int i=currentFloor; i>=destFloor; i--) {
			Thread.sleep(1000);
			System.out.println("电梯运行到 ： " + i + "层");
			setCurrentFloor(i);
		}
		if (currentFloor == destFloor) {
			open();
			Thread.sleep(1000);
			close();
		}
	}
	
	public void run(int destFloor) throws Exception{
		if (destFloor > totalFloor || destFloor < 1) {
			System.out.println("楼层选择错误");
		}
		setDestFloor(destFloor);
		if (getCurrentFloor() < destFloor) {
			up();
		} else {
			down();
		}
	}
	
	public void open(){
		System.out.println("电梯开门");
	}
	
	public void close(){
		System.out.println("电梯关门");
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}

	public int getDestFloor() {
		return destFloor;
	}

	public void setDestFloor(int destFloor) {
		this.destFloor = destFloor;
	}

}
