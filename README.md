async-demo/
 pom.xml
 src/main/java/com/example/asyncdemo/
    AsyncDemoApplication.java       ← @EnableAsync lives here
    config/
     AsyncConfig.java            ← Custom thread pool
    service/
     NotificationService.java    ← @Async methods
    controller/
        DemoController.java         ← 3 REST endpoints
