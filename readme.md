# 仮想通貨送信アプリ

本番環境を作る場合は以下を実行してください。

```shell
make prod
```

ここからは開発者用です。

開発環境起動

```shell
make tool
make dev
make symbol-server
make user-client
make socket
```


- 管理画面 https://crypto.currency.com.127.0.0.1.nip.io
- チャット画面　https://crypto.currency.com.127.0.0.1.nip.io

繋ぎ込み先API
 - https://localhost:8080/api/

### TODO
 - 現在、symbolしか対応していません。
 - アグリゲートには対応していません
 - エラー処理は省略しています
 - 過去のチャットが出てこない
