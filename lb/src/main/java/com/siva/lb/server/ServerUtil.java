package com.siva.lb.server;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerUtil {
	private final String serverAddress;
	// private Integer currLoad;
	private final AtomicInteger currLoad;

	public ServerUtil(String serverAddress) {
		this.serverAddress = serverAddress;
		this.currLoad = new AtomicInteger(0);
	}

	public String getServerAddress() {
		return serverAddress;
	}

	// public void setServerAddress(String serverAddress) {
	// 	this.serverAddress = serverAddress;
	// }

	public int getCurrLoad() {
		return currLoad.get();
	}

	//For AtomicInteger we dont need set sutffs
	// public void setCurrLoad(Integer currLoad) {
	// 	this.currLoad = currLoad ;
	// }

	public void increaseLoad() {
		this.currLoad.incrementAndGet();
	}

	public void decreaseLoad() {
		this.currLoad.decrementAndGet();
	}

}
