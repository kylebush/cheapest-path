# Cheapest Path Brain Teaser
 Given a rectangular grid, each cell contains a specific cost. Starting
 from the upper-left cell, the objective is to determine the least
 expensive path to the bottom-right cell. You are only allowed to move
 down and to the right.

## Instructions

#### Clone Repository
```bash
git clone https://github.com/kylebush/cheapest-path.git
cd cheapest-path
```

#### Build
Mac OSX:
```bash
./gradlew shawdowJar
```
Windows:
```bash
gradlew.bat shawdowJar
```

#### Run
Mac OSX:
```bash
java -jar build/libs/cheapest-path-all.jar CheapestPath.class --help
```
Windows:
```bash
java -jar build\libs\cheapest-path-all.jar CheapestPath.class --help
```

#### Example (Mac OSX)

```
$ java -jar build/libs/cheapest-path-all.jar CheapestPath.class --rows=10 --columns=10 --max-value=9
```
![Image](screenshot.png?raw=true)
