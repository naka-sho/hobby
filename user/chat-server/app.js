const app = require('express')();
const bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(bodyParser.json());
const server = require('http').createServer(app);
const io = require('socket.io')(server,  {
    cors: {
        origin: '*'
    }
});

app.post('/chat/notice', async (request, response) => {
    console.log(request.body)
    let name = request.body.name;
    let text = request.body.message;
    io.emit("sendMessage", { name: name, text: text});
    // io.emit("sendMessage", { name: '管理bot', text: "アドレス : C28BF5E63BE3825F52FE140FD449F9415E491EB2B055D29C047FA217EED0D3AD の送金ステータスが完了になりました"});
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
