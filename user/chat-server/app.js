const app = require('express')();
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
const server = require('http').createServer(app);
const io = require('socket.io')(server);

app.post('/chat/notice', async (request, response) => {
    console.log(request.body)
    let sendAddress = request.body.sendAddress;
    let price = request.body.price;
    io.emit("sendMessage", { name: '管理bot', text: sendAddress + "に" + price + "送信しました。"});
    response.statusCode = 200;
    response.json(request.body);
});

app.post('/chat/complete', async (request, response) => {
    console.log(request.body)
    let sendAddress = request.body.sendAddress;
    io.emit("sendMessage", { name: '管理bot', text: sendAddress + "の送金ステータスが完了になりました"});
    response.statusCode = 200;
    response.json(request.body);
});

io.on('connection', (socket) => {
    console.log('A client connected.');
    socket.on('send', (payload) => {
        console.log(payload);
        socket.broadcast.emit('broadcast', payload);
    });
    socket.on('disconnect', () => {
        console.log('Conenction closed.');
    });
});

server.listen(3001, () => {
    console.log('Listening..');
});

/*
 * 日付から文字列に変換する関数
 */
function getStringFromDate(date) {

    var year_str = date.getFullYear();
    //月だけ+1すること
    var month_str = 1 + date.getMonth();
    var day_str = date.getDate();
    var hour_str = date.getHours();
    var minute_str = date.getMinutes();
    var second_str = date.getSeconds();


    format_str = 'YYYY-MM-DD hh:mm:ss';
    format_str = format_str.replace(/YYYY/g, year_str);
    format_str = format_str.replace(/MM/g, month_str);
    format_str = format_str.replace(/DD/g, day_str);
    format_str = format_str.replace(/hh/g, hour_str);
    format_str = format_str.replace(/mm/g, minute_str);
    format_str = format_str.replace(/ss/g, second_str);

    return format_str;
};
