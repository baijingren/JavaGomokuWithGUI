#### 代码架构

```txt
GUI_Program/
│   Paper.md
│   README.md
│
├───Debug
│   └───GomokuGUI
│       └───src
│           ├───test
│           │   └───moonflowerr
│           │       └───test
│           │               ControllerTest.java
│           │               LogPrinter.java
│           │               ModelTest.java
│           │               NetworkTest.java
│           │
│           └───xyz.moonflowerr.GomokuWithGUI
│               │   LogPrinter.java
│               │   Main.java
│               │   Var.java
│               │
│               ├───Controller
│               │       Controller.java
│               │
│               ├───Model
│               │       Model.java
│               │
│               ├───Network
│               │       Codec.java
│               │       ConnectionListener.java
│               │       Controller.java
│               │       Message.java
│               │       Network.java
│               │
│               ├───resources
│               │       Black.png
│               │       White.png
│               │
│               └───View
│                       BoardPanel.java
│                       Cell.java
│                       ChatPanel.java
│                       ControlPanel.java
│                       IPConnectionPanel.java
│                       IPMessagePanel.java
│                       PlayerPanel.java
│                       View.java
│
└───lib
        apiguardian-api-1.1.2.jar
        junit-jupiter-api-5.8.2.jar
        junit-jupiter-params-5.8.2.jar
        junit-platform-commons-1.8.2.jar
        opentest4j-1.2.0.jar
```

Github仓库连接：[https://github.com/baijingren/JavaGomokuWithGUI](https://github.com/baijingren/JavaGomokuWithGUI)

代码100%由本人独立完成，未抄袭AI或其他部分代码。