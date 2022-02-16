# 簡易仮想通貨送信システム

## 概要

ユーザのアプリにAPIをリクエストする実装を作るだけで、簡単にsymbolを送金できます。
主にツイッターなどでよくあるsymbolのばらまき企画の際に、ゲーム感覚で使用できます。

システムがわかる人は、conohaなどレンタルサーバーを借りればチャット画面を公開することもできます。
その際は、プライベートキーの流出に気をつけてください。

## 事前準備

javaとdockerをインストールしてください。

※現在macにしか対応しておりません。

 - javaのインストール

```
curl -s "https://get.sdkman.io" | bash
sdk list java 
sdk install java 17.0.1-open 
sdk default java 17.0.1-open
```

 - docker のインストール
 
 https://matsuand.github.io/docs.docker.jp.onthefly/desktop/mac/install/

# 起動手順

```shell
make prod
```

## 使用方法

1. 管理画面より、アドレスを登録 https://crypto.currency.com.127.0.0.1.nip.io
   1. 改行区切りで登録できます。google アンケートなどで集計して貼り付けて登録してください
2. 管理画面より、送金を押すことで登録しているアドレス全てに一括で送金されます。
3. 送金状況はチャット画面で確認できます https://user.chat.com.127.0.0.1.nip.io
4. 以下のAPIをそのほかのゲームからリクエストすることで、簡単に送金ができるようになります。
- 送金API
    - http://localhost:8080/api/send
    - POST
    - application/json
    - パラメーター
        - price: 送金したい金額(symbolの場合、5000000 → 5XYMで計算されます)
        - message: トランザクションメッセージ
            - 例：{"price":5000000,"message":"test"}

![image](https://user-images.githubusercontent.com/43595281/154271249-d3c6f6bf-4c43-412d-974f-3a691161c6c6.png)
![image](https://user-images.githubusercontent.com/43595281/154271439-15c0bd8d-c4a9-4667-9bfa-5ab59007e97e.png)
![image](https://user-images.githubusercontent.com/43595281/154272233-7ea43499-5cd5-4233-b817-499d3ceadd72.png)
![image](https://user-images.githubusercontent.com/43595281/154272333-803fcb1d-9aa9-4341-aada-50fa9cb65ae9.png)

### ここからは開発者用です。

開発環境起動

```shell
make tool
make dev
make symbol-server
make user-client
make socket
```
