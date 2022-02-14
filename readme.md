# 仮想通貨送信アプリ

## 概要

ユーザのアプリにAPIをリクエストする実装を作るだけで、簡単にsymbolを送金できます。
主にツイッターなどでよくあるsymbolのばらまき企画の際に、ゲーム感覚で使用できます。

現在macにしか対応しておりません。
docker for macをインストールして、該当のプロジェクトをcloneして以下のコマンドを実行してください。

システムがわかる人は、conohaなどレンタルサーバーを借りればチャット画面を公開することもできます。
その際は、プライベートキーの流出に気をつけてください。

```shell
make prod
```

## 使用方法

1. 管理画面より、アドレスを登録
   1. 改行区切りで登録できます。google アンケートなので集計して貼り付けて登録してください
2. 

- 管理画面 https://crypto.currency.com.127.0.0.1.nip.io
- チャット画面 https://user.chat.com.127.0.0.1.nip.io
- 送金API
    - http://localhost:8080/api/send
    - POST
    - application/json
    - パラメーター
        - price: 送金したい金額(symbolの場合、5000000 → 5XYMで計算されます)
        - message: トランザクションメッセージ
            - 例：{"price":5000000,"message":"test"}
            - 本番環境を作る場合は以下を実行してください。

ここからは開発者用です。

開発環境起動

```shell
make tool
make dev
make symbol-server
make user-client
make socket
```



### TODO
 - 現在、symbolしか対応していません。
 - アグリゲートには対応していません
 - エラー処理は省略しています
 - 過去のチャットが出てこない
