package com.siva.lb.lbinfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.siva.lb.server.ServerUtil;

import reactor.core.publisher.Mono;

public class LB {
	private final List<ServerUtil> serverUtilList;

	public LB() {
		serverUtilList = new CopyOnWriteArrayList<>();
	}

	public void addServer(ServerUtil server) {
		serverUtilList.add(server);
	}

	public Mono<ServerUtil> getServerWithMinimumLoad() {
		return Mono.defer(() -> {
			if (serverUtilList.isEmpty()) {
				return Mono.error(new IllegalArgumentException("No servers registered"));
			}
			ServerUtil server = serverUtilList.get(0);
			for (ServerUtil s : serverUtilList) {
				if (s.getCurrLoad() < server.getCurrLoad()) {
					server = s;
				}
			}
			return Mono.just(server);
		});

	}

	// public ServerUtil getServerWithMinimumLoad() {
	// if (serverUtilList.isEmpty()) {
	// return null;
	// }
	// ServerUtil serverUtil = serverUtilList.get(0);
	// for (int i = 1; i < serverUtilList.size(); i++) {
	// ServerUtil server = serverUtilList.get(i);
	// if (server.getCurrLoad() < serverUtil.getCurrLoad()) {
	// serverUtil = server;
	// }
	// }
	// return serverUtil;
	// }

	// public ServerUtil getNextServer() {
	// if (serverUtilList.isEmpty()) {
	// return null; // need to write some error exceptions
	// }
	// ServerUtil server = getServerWithMinimumLoad();
	// server.increaseLoad();
	// return server;
	// }
	// need to program the logic for reducing counter for the load when the job is
	// done

}
