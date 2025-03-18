# bigGLSP - Java Server Framework

The bigGLSP Java-Server package provides a generic framework for Java that extends the capabilities of the Graphical Language Server Platform (GLSP). It enhances GLSP by offering additional features, flexibility, and tools for creating graphical modeling editors in a server-client architecture. Designed to integrate seamlessly with GLSP, this framework simplifies the development of complex diagramming applications.

- **Java 17 JDK**: Java 17 is required
- **Gradle**: We are using [Gradle](https://gradle.org/)

## Building (CLI)

Execute the command:

- Linux: `./gradlew prepare`
- Windows: `./gradlew.bat prepare`

## IDE

The development environment has been tested with Visual Studio Code (VSCode), and it is recommended for quick and lightweight development. However, since the project uses Gradle for building and managing dependencies, support for other IDEs such as IntelliJ IDEA and Eclipse should still be possible. You can import the project into these environments using their respective Gradle integration tools.

### VSCode

It is mandatory to open the server folder as root, otherwise, Gradle can not be detected by VSCode.

Recommended Extensions:

- [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
