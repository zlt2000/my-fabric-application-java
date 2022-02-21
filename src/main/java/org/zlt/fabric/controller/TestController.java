package org.zlt.fabric.controller;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.ContractException;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.sdk.Peer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.concurrent.TimeoutException;

/**
 * TODO
 *
 * @author zlt
 * @version 1.0
 * @date 2022/2/21
 * <p>
 * Blog: https://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */
@RestController
public class TestController {
    @Resource
    private Contract contract;

    @Resource
    private Network network;

    @GetMapping("/getUser")
    public String getUser(String userId) throws ContractException {
        byte[] queryAResultBefore = contract.evaluateTransaction("getUser",userId);
        return new String(queryAResultBefore, StandardCharsets.UTF_8);
    }

    @GetMapping("/addUser")
    public String addUser(String userId, String userName, String money) throws ContractException, InterruptedException, TimeoutException {
        byte[] invokeResult = contract.createTransaction("addUser")
                .setEndorsingPeers(network.getChannel().getPeers(EnumSet.of(Peer.PeerRole.ENDORSING_PEER)))
                .submit(userId, userName, money);
        String txId = new String(invokeResult, StandardCharsets.UTF_8);
        return txId;
    }

    @GetMapping("/queryAll")
    public String queryAll() throws ContractException {
        byte[] queryAResultBefore = contract.evaluateTransaction("queryAll");
        return new String(queryAResultBefore, StandardCharsets.UTF_8);
    }

    @GetMapping("/getHistory")
    public String getHistory(String userId) throws ContractException {
        byte[] queryAResultBefore = contract.evaluateTransaction("getHistory", userId);
        return new String(queryAResultBefore, StandardCharsets.UTF_8);
    }
}
