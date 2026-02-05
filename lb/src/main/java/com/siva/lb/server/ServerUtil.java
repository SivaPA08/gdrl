package com.siva.lb.server;

public class ServerUtil {
	private String serverAddress;
	private Integer currLoad;

	public ServerUtil(String serverAddress, Integer currLoad) {
		this.serverAddress = serverAddress;
		this.currLoad = currLoad;
	}

	public String getServerAddress() {
		return serverAddress;
	}

	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	public Integer getCurrLoad() {
		return currLoad;
	}

	public void setCurrLoad(Integer currLoad) {
		this.currLoad = currLoad;
	}

	public void increaseLoad() {
		this.currLoad++;
	}

	public void decreaseLoad() {
		this.currLoad--;
	}

}
