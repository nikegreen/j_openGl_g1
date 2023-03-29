# j_openGl_g1
## java openGL
Проект на языке java v17. Проект для intellij IDEA. Сборщик MAVEN.
Использует 3D ускорение от видеокарт для отрисовки графических объектов.
Демонстрирует:
- создание окна
- отрисовку 3Д объектов 
- наложение текстуры
- движение объекта
- вращение объекта
## используемые технологии:

### OpenGL v.3.3.1
### Библиотека lwjgl

## Назначение программы
На экране рисуется 3 куба с текстурами. Используется проекция на экран с перспективой.
1. Один куб вращается по оси X и одновременно движется вдоль оси X.
2. Второй куб вращается вдоль оси Y.
3. Третий куб вращается вдоль оси Z.
4. Кнопками клавиатуры можно перемещать точку просмотра (камеру) 

## Управление в программе
Можно управлять положением камеры с помощью кнопок на дополнительной цифровой клавиатуре. <br>

4 - движение камеры влево<br>
5 - движение камеры назад<br>
6 - движение камеры вправо<br>
8 - движение камеры вперёд<br>
'+' - движение камеры наверх<br>
'ENTER' - движение камеры вниз<br>
'ESC' - выход из программы<br>

## Сборка проекта
```term
mvn clean
mvn compile
mvn install
```
В строке посмотреть куда установился jar. Пример:
```term
"C:\Program Files\Java\jdk-19\bin\java.exe" -Dmaven.multiModuleProjectDirectory=C:\projects\j_openGl_g1 -Djansi.passthrough=true "-Dmaven.home=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\plugins\maven\lib\maven3" "-Dclassworlds.conf=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\plugins\maven\lib\maven3\bin\m2.conf" "-Dmaven.ext.class.path=C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\plugins\maven\lib\maven-event-listener.jar" "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\lib\idea_rt.jar=14940:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\bin" -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8 -classpath "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\plugins\maven\lib\maven3\boot\plexus-classworlds-2.6.0.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2022.2.4\plugins\maven\lib\maven3\boot\plexus-classworlds.license" org.codehaus.classworlds.Launcher -Didea.version=2022.3.1 install
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------------< ru.nikegreen:openGlGame1 >----------------------
[INFO] Building openGlGame1 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ openGlGame1 ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 5 resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ openGlGame1 ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 19 source files to C:\projects\j_openGl_g1\target\classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ openGlGame1 ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ openGlGame1 ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ openGlGame1 ---
[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ openGlGame1 ---
[INFO] Building jar: C:\projects\j_openGl_g1\target\openGlGame1-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-install-plugin:2.4:install (default-install) @ openGlGame1 ---
[INFO] Installing C:\projects\j_openGl_g1\target\openGlGame1-1.0-SNAPSHOT.jar to C:\Users\nikez\.m2\repository\ru\nikegreen\openGlGame1\1.0-SNAPSHOT\openGlGame1-1.0-SNAPSHOT.jar
[INFO] Installing C:\projects\j_openGl_g1\pom.xml to C:\Users\nikez\.m2\repository\ru\nikegreen\openGlGame1\1.0-SNAPSHOT\openGlGame1-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  10.918 s
[INFO] Finished at: 2023-03-30T03:41:22+05:00
[INFO] ------------------------------------------------------------------------

Process finished with exit code 0

```
## Запуск
```term
java -jar openGlGame1-1.0-SNAPSHOT.jar
```

