const express = require('express');
const bodyParser = require('body-parser');
const dotenv = require('dotenv').config();
const app = express();
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
const symbol = require('symbol-sdk');


app.get('/', async (request, response) => {
    console.log('OK');
    response.statusCode = 200;
    response.send('OK');
});

app.get('/net/type', async (request, response) => {
    console.log('OK');
    response.statusCode = 200;
    response.json(symbol.NetworkType);
});

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
    let sendAddrees = request.body.sendAddrees;
    let price = request.body.price;
    let message = request.body.message;

    // let sendAddrees = "TDMYLKCTEVPSRPTG4UXW47IQPCYNLW2OVWZMLGY";
    // let price = 100000;
    // let message = "send test";

    let alice = symbol.Account.createFromPrivateKey(PRIVATE_KEY, NETWORK_TYPE);
    let tx = symbol.TransferTransaction.create(
        symbol.Deadline.create(EPOCH_ADJUSTMENT),
        symbol.Address.createFromRawAddress(sendAddrees),
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
    let hash = new symbol.TransactionHttp(NODE)
        .announce(signedTx)
        .subscribe(
            (x) => {
                const transaction = new Transaction(signedTx.hash);
                console.log(transaction)
                console.log(TRANSACTION_STATUS + signedTx.hash)
                response.statusCode = 200;
                response.json(transaction);
            },
            (err) => {
                console.log(err)
                response.statusCode = 500;
                response.send('NG');
            }
        );
});

class Transaction {
    constructor(hash) {
        this.hash = hash;
        this.url = TRANSACTION_STATUS + hash;
    }
}

app.listen('3000', () => {
    console.log('Application started');
});
