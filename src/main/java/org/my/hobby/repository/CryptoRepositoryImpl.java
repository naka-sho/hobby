package org.my.hobby.repository;


import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;

import java.math.BigInteger;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

import io.nem.symbol.sdk.api.RepositoryFactory;
import io.nem.symbol.sdk.api.TransactionRepository;
import io.nem.symbol.sdk.model.account.Account;
import io.nem.symbol.sdk.model.network.NetworkType;
import io.nem.symbol.sdk.model.message.PlainMessage;
import io.nem.symbol.sdk.model.mosaic.Mosaic;
import io.nem.symbol.sdk.model.mosaic.MosaicId;
import io.nem.symbol.sdk.model.transaction.Deadline;
import io.nem.symbol.sdk.model.transaction.SignedTransaction;
import io.nem.symbol.sdk.model.transaction.TransactionAnnounceResponse;
import io.nem.symbol.sdk.model.transaction.TransferTransaction;
import io.nem.symbol.sdk.model.transaction.TransferTransactionFactory;

@Singleton
public class CryptoRepositoryImpl implements CryptoRepository {
    private final String baseUrl = "http://ngl-dual-101.testnet.symboldev.network:3000";

//    private final RepositoryFactory repositoryFactory;

//    CryptoRepositoryImpl() {
//        this.repositoryFactory = new RepositoryFactoryVertxImpl(
//                baseUrl
//        );
//    }

    @Override
    public boolean send(Symbol symbol) {
        RepositoryFactory repositoryFactory = new RepositoryFactoryVertxImpl(
                baseUrl
        );

        Account alice = Account.createFromPrivateKey(
                "896E43895B908AF5847ECCB2645543751D94BD87E71058B003417FED512314AE",
                NetworkType.TEST_NET
        );

        System.out.printf("Alice address is: %s \n", alice.getAddress().plain());

        Account bob = Account.generateNewAccount(NetworkType.TEST_NET);
        System.out.printf("Bob address is: %s \n", bob.getAddress().plain());

        BigInteger amount = BigInteger.valueOf(1);

        Mosaic mosaic = new Mosaic(
                new MosaicId("091F837E059AE13C"),
                amount.multiply(
                        BigInteger.valueOf(10).pow(6)
                )
        );

        TransferTransaction tx = this.transferTransaction(repositoryFactory, bob, mosaic);
        String generationHash = this.generationHash(repositoryFactory);
        SignedTransaction signedTx = alice.sign(tx, generationHash);
        System.out.printf("SignedTransaction is: %s ", signedTx.getHash());
        return this.send(repositoryFactory, signedTx);
    }

    private TransferTransaction transferTransaction(RepositoryFactory repositoryFactory, Account account, Mosaic mosaic) {
        try {
            return TransferTransactionFactory.create(
                    NetworkType.TEST_NET,
                    Deadline.create(repositoryFactory.getEpochAdjustment().toFuture().get()),
                    account.getAddress(),
                    Collections.singletonList(mosaic)
            ).message(
                    PlainMessage.create("This is a test message")
            ).maxFee(BigInteger.valueOf(2000000)
            ).build();
        } catch (InterruptedException e) {
            throw new BadRequestException();
        } catch (ExecutionException e) {
            throw new BadRequestException();
        }
    }

    private String generationHash(RepositoryFactory repositoryFactory){
        try {
            return repositoryFactory.getGenerationHash().toFuture().get();
        } catch (InterruptedException e) {
            throw new BadRequestException();
        } catch (ExecutionException e) {
            throw new BadRequestException();
        }
    }

    private boolean send(RepositoryFactory repositoryFactory, SignedTransaction signedTx){
        try {
            TransactionRepository txRepo = repositoryFactory.createTransactionRepository();
            txRepo.announce(signedTx).toFuture().get();
            return true;
        } catch (InterruptedException e) {
            return false;
        } catch (ExecutionException e) {
            return false;
        }
    }
}
