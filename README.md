# okDns

Set dns for android okHttp.

## Installation

Add it in your root build.gradle at the end of repositories:

```bash
implementation 'com.github.ResulSilay:okDns:1.0.0'
```

## Usage

```java
val okDns = OkDns.init(baseUrl)
okDns.set("8.8.8.8")
/*okDns.set(listOf("8.8.8.8", "8.8.4.4"))*/

this.okHttpClient = OkHttpClient().newBuilder()
    .dns(okDns)
    .build()
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
