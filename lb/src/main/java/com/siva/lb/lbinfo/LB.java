package com.siva.lb.lbinfo;

import java.util.ArrayList;
import java.util.List;

import com.siva.lb.server.ServerUtil;

public class LB {
	private List<ServerUtil> serverUtilList;

	public LB() {
		serverUtilList = new ArrayList<>();
	}

	public void addServer(ServerUtil server) {
		serverUtilList.add(server);
	}

	public ServerUtil getServerWithMinimumLoad() {
		if (serverUtilList.isEmpty()) {
			return null;
		}
		ServerUtil serverUtil = serverUtilList.get(0);
		for (int i = 1; i < serverUtilList.size(); i++) {
			ServerUtil server = serverUtilList.get(i);
			if (server.getCurrLoad() < serverUtil.getCurrLoad()) {
				serverUtil = server;
			}
		}
		return serverUtil;
	}

	public ServerUtil getNextServer() {
		if (serverUtilList.isEmpty()) {
			return null; // need to write some error exceptions
		}
		ServerUtil server = getServerWithMinimumLoad();
		server.increaseLoad();
		return server;
	}
	// need to program the logic for reducing counter for the load when the job is
	// done

}
