const express = require('express');
const bodyParser = require('body-parser');
const axios = require('axios');
const app = express();
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
const symbol = require('symbol-sdk');

/**
 * ヘルスチェック
 */
app.get('/', async (request, response) => {
    response.statusCode = 200;
    response.send('OK');
});

/**
 * ネットタイプ
 */
app.get('/net/type', async (request, response) => {
    response.statusCode = 200;
    response.json(symbol.NetworkType);
});

/**
 * 非同期でシンボルを送金し、トランザクションIDを保存する
 * エラーの場合はconsoleログに出力するだけで、OKを返す
 */
app.post('/send', async (request, response) => {
    console.log(request.body)
    // https://testnet.symbol.tools/?recipient=TARK3GGU52N6FMAHAAGOWGOCDZ7WEKFKYVTNWPA&amount=10000
    // const generation_hash = '7FCCD304802016BEBBCD342A332F91FF1F3BB5E902988B352697BE245F48E836';
    // const epoch_adjustment = 1637848847;
    // const private_key = "54CB7AA88F46CB140D3B9341835DAF61F6F4B1EC93D6270BE53C53B769072487";
    // const mosaic_id = "3A8416DB2D53B6C8";
    // const node = "https://sym-test.opening-line.jp:3001";
    // const transaction_status = node + "/transactionStatus/";
    // const network_type = symbol.NetworkType.TEST_NET;
    // let sendAddress = "TDMYLKCTEVPSRPTG4UXW47IQPCYNLW2OVWZMLGY";
    // let price = 100000;
    // let message = "send test";

    let generation_hash = request.body.hash;
    let epoch_adjustment = request.body.epochAdjustment;
    let private_key = request.body.privateKey;
    let mosaic_id = request.body.mosaic;
    let node = request.body.node;
    let transaction_status = node + "/transactionStatus/";
    let network_type = symbol.NetworkType.TEST_NET;
    if(request.body.node === 'prod'){
        network_type = symbol.NetworkType.MAIN_NET;
    }
    let sendAddress = request.body.sendAddress;
    let price = request.body.price;
    let message = request.body.message;
    let urlAddLog = request.body.urlAddLog;
    let urlDeleteErrorAddress = request.body.urlDeleteErrorAddress;

    try {
        let alice = symbol.Account.createFromPrivateKey(private_key, network_type);
        let tx = symbol.TransferTransaction.create(
            symbol.Deadline.create(epoch_adjustment),
            symbol.Address.createFromRawAddress(sendAddress),
            [
                new symbol.Mosaic(
                    new symbol.MosaicId(mosaic_id),
                    symbol.UInt64.fromUint(price)
                )
            ],
            symbol.PlainMessage.create(message),
            network_type,
            symbol.UInt64.fromUint(100000)
        );
        let signedTx = alice.sign(tx, generation_hash);
        new symbol.TransactionHttp(node)
            .announce(signedTx)
            .subscribe(
                (x) => {
                    const transaction = new Transaction(signedTx.hash);
                    const data = {
                        address: sendAddress,
                        price : price,
                        transaction: transaction.hash,
                        url: transaction_status + transaction.hash,
                    };

                    console.log(transaction_status + transaction.hash);

                    axios
                        .post(urlAddLog, data)
                        .then(response => {
                        })
                        .catch(reason => {
                            console.log(transaction)
                            console.log(reason)
                        });
                },
                (err) => {
                    console.log(err)
                }
            );
        response.statusCode = 200;
        response.json({"message": "OK"});
        return
    } catch (error) {
        response.statusCode = 500;
        response.json({"message": "NG"});
        axios
            .post(urlDeleteErrorAddress, {"address": sendAddress})
            .then(response => {
                console.log("error address" +  sendAddress)
            })
            .catch(reason => {
                console.log(reason)
            });
    }
});

class Transaction {
    constructor(hash) {
        this.hash = hash;
    }
}

app.listen('3000', () => {
    console.log('Application started');
});
