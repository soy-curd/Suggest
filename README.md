# Suggest

## Summary  
日本語文書校正ツール。誤変換を検出することができる。
内部的にYahooの[校正支援ツール](http://developer.yahoo.co.jp/webapi/jlp/kousei/v1/kousei.html)を
呼んでいるため、別途YahooのアプリケーションIDを取得する必要がある。

## Usage
+ 文字列を解析する
```
scala Suggest <yahooAppId> 文字列
```

## Examples
+ input

```
scala Suggest <yahooAppId> 彼らは小形飛行機の製造中の意心伝心に思考錯誤しています。

```

+ output
```
彼らは小形飛行機の製造中の意心伝心に思考錯誤しています。
　　　^小型飛行機
　　　　　　　　　　　　　^以心伝心
　　　　　　　　　　　　　　　　　　^試行錯誤
```
