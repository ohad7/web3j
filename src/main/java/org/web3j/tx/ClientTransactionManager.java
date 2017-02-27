package org.web3j.tx;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.exceptions.TransactionTimeoutException;

/**
 * <p>TransactionManager implementation for using an Ethereum node to transact.
 *
 * <p><b>Note</b>: accounts must be unlocked on the node for transactions to be successful.
 */
public class ClientTransactionManager implements TransactionManager {

    private final Web3j web3j;
    private final String fromAddress;
    
    AtomicInteger nonce = new AtomicInteger(new Random().nextInt());

    public ClientTransactionManager(
            Web3j web3j, String fromAddress) {
        this.web3j = web3j;
        this.fromAddress = fromAddress;
    }

    @Override
    public EthSendTransaction executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value)
            throws ExecutionException, InterruptedException, TransactionTimeoutException {

      BigInteger myNonce = BigInteger.valueOf(nonce.incrementAndGet());
      System.out.println("myNonce:" + myNonce);
        Transaction transaction = new Transaction(
                fromAddress, myNonce, gasPrice, gasLimit, to, value, data);

        return web3j.ethSendTransaction(transaction)
                .sendAsync().get();
    }
}
