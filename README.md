# vf-loader

vf-loader is very fast file uploder and donwloader. (experimental, hobby project)

Images
![Demo](/images/demo-image.png)

#### 1. Technical verification point

* File Upload
  * Asynchronous file upload using ajax and [javascript's file interface](https://developer.mozilla.org/en-US/docs/Web/API/File).
  * Without `multipart/form` data in HTTP request.
    * `multipart/form` have overhead of encode and decode, and the amount of data increases.

* File Download
  * HTTP Streaming file download using Transfer-chunked in HTTP response.

* SPA（Single Page Application)

If you want to know details, please see blog.(japanese only)

* [ajaxでマルチパートを使わずにファイルアップロードする方法](https://qiita.com/5zm/items/43d78236a295819c239a)
* [TERASOLUNA5.x(=SpringMVC)で巨大ファイルアップロードを実現する方法](https://qiita.com/5zm/items/4319782745830491bd9b)
* [TERASOLUNA5.x(=SpringMVC)で巨大ファイルダウンロードを実現する方法](https://qiita.com/5zm/items/0dd239bde353cf6dddae)

#### 2. Framework and library used

| library | remarks |
|:-------:|:--------|
| spring boot (1.5.17.RELEASE)  | java application framework |
| h2 | embedded h2 database |
| jquery-3.3.1.min.js | ajax and dom manipulation |
| mustache.min.js | template engine with javascript |

Since `JQuery` does not support `File`, use `XMLHttpRequest` in part.

#### 3. How to run

1. `git clone https://github.com/5zm/vf-loader.git`
2. `cd vf-loader`
3. `mvn clean package -Dmaven.test.skip=true`
4. `java -jar target\demo-0.0.1-SNAPSHOT.jar`
  * if you change port number, add `--server.port=7777` options (spring boot's feature)
5. access `http://localhost:8888/index.html` on your web browser 
6. stop application is `Ctrl+C` (windows)

#### 4. Data save directory

```console
vf-loader
├─target
│  └─demo-0.0.1-SNAPSHOT.jar
├─config
│  └─storeConfigs.json # config file
├─filedata # uploaded files are stored under this directory
│  ├─tools
│  ├─presentations
│  └─reference
├─h2db # h2db's files are stored under this directory
└─logs # log file is stored under this directory
```

If you change default, add `-D` system porperties.
Or direct change `src/main/resources/application.properties`.

* `-Dapplication.storeConfig=C:/somewhere/storeConfigs.json`
* `-Dapplication.saveBaseDirectory=C:/somewhere/uploadedfiles`

If you add upload file directory, change storeConfigs.json.

```json
{
  "storeConfigs" : {
    "tools" : {
      "order" : 1,
      "fileType" : "tools",
      "fileTypeName" : "my development tools"
    },
    "presentations" : {
      "order" : 2,
      "fileType" : "presentations",
      "fileTypeName" : "my reports, presentations, documents"
    },
    "reference" : {
      "order" : 3,
      "fileType" : "reference",
      "fileTypeName" : "reference information"
    }
  }
}
```

