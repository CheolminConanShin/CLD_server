# Nexshop Traning Api Proxy Server

With play framework2 and Java. It is a proxy server connecting mobile device and Nexshop traning server.

## Prerequisites

* [SBT](http://www.scala-sbt.org/0.13/docs/index.html)
* [Play framework2](https://www.playframework.com/download)
* Proxy Settings

```
JAVA_OPTS= -Dhttp.proxyHost=70.10.15.10 -Dhttp.proxyPort=8080 -Dhttps.proxyHost=70.10.15.10 -Dhttps.proxyPort=8080
```

## Usage

### compile

```sh
$ sbt compile
```

### test

```sh
$ sbt test
```

### run

```sh
$ sbt run
```

```
--- (Running the application, auto-reloading is enabled) ---
[info] p.c.s.NettyServer - Listening for HTTP on /0:0:0:0:0:0:0:0:9000
Server started, use Cmd+P to stop
```