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
    // https://testnet.symbol.tools/?recipient=TARK3GGU52N6FMAHAAGOWGOCDZ7WEKFKYVTNWPA&amount=10000
    const GENERATION_HASH = '7FCCD304802016BEBBCD342A332F91FF1F3BB5E902988B352697BE245F48E836';
    const EPOCH_ADJUSTMENT = 1637848847;
    const PRIVATE_KEY = "54CB7AA88F46CB140D3B9341835DAF61F6F4B1EC93D6270BE53C53B769072487";
    const MOSAIC_ID = "3A8416DB2D53B6C8";
    const NODE = "https://sym-test.opening-line.jp:3001";
    const TRANSACTION_STATUS = NODE + "/transactionStatus/";
    const NETWORK_TYPE = symbol.NetworkType.TEST_NET;

    console.log(request.body)
    let sendAddress = request.body.sendAddress;
    let price = request.body.price;
    let message = request.body.message;

    console.log(sendAddress)
    console.log(price)
    console.log(message)

    // let sendAddress = "TDMYLKCTEVPSRPTG4UXW47IQPCYNLW2OVWZMLGY";
    // let price = 100000;
    // let message = "send test";

    let alice = symbol.Account.createFromPrivateKey(PRIVATE_KEY, NETWORK_TYPE);
    let tx = symbol.TransferTransaction.create(
        symbol.Deadline.create(EPOCH_ADJUSTMENT),
        symbol.Address.createFromRawAddress(sendAddress),
        [
            new symbol.Mosaic(
                new symbol.MosaicId(MOSAIC_ID),
                symbol.UInt64.fromUint(price)
            )
        ],
        symbol.PlainMessage.create(message),
        NETWORK_TYPE,
        symbol.UInt64.fromUint(100000)
    );
    let signedTx = alice.sign(tx, GENERATION_HASH);
    new symbol.TransactionHttp(NODE)
        .announce(signedTx)
        .subscribe(
            (x) => {
                const transaction = new Transaction(signedTx.hash);
                const data = {
                    hash: transaction.hash,
                };

                console.log(TRANSACTION_STATUS + transaction.hash)

                // axios
                //     .post('http://localhost:3000/users', data)
                //     .then(response => {
                //         // console.log(response);
                //     })
                //     .catch(reason => {
                //         // console.log(transaction)
                //         // console.log(reason)
                //     });
            },
            (err) => {
                console.log(err)
            }
        );
    response.statusCode = 200;
    response.json({"message": "OK"});
});

class Transaction {
    constructor(hash) {
        this.hash = hash;
    }
}

app.listen('3000', () => {
    console.log('Application started');
});
