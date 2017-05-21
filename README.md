[![Build Status](https://travis-ci.org/codejanovic/java-filesearch.svg?branch=develop)](https://travis-ci.org/codejanovic/java-filesearch)
[![Coverage Status](https://coveralls.io/repos/github/codejanovic/java-filesearch/badge.svg?branch=develop)](https://coveralls.io/github/codejanovic/java-filesearch?branch=develop)
[![License](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)]()

# java-filesearch
Java filesearch reimagined.

## Maven
Release artifact
```xml
<dependency>
    <groupId>io.github.codejanovic</groupId>
    <artifactId>java-filesearch</artifactId>
    <version>0.1.0</version>
</dependency>
```
Snapshot artifact
```xml
<dependency>
    <groupId>io.github.codejanovic</groupId>
    <artifactId>java-filesearch</artifactId>
    <version>0.2.0-SNAPSHOT</version>
</dependency>
```

## Motivation
This is just a fun project to reimplement the horrible file walking implementation from the JDK, trying to combine the two Java APIs of `File.listFiles()` and `Files.walkFileTree()` into a new API using Java's new `Stream<T>` feature of version 8.

To search for files simply start with the static method `Search.search()` and search for executable (.exe) files ...

... not **recursively** using the **Path API**
```java
    import static io.github.codejanovic.java.filesearch.Search.search;

    public void doSomeFileSearchStuff() {
        final List<Path> executables = search().directory("S:\Earch\Path").notRecursively().byPath()
            .stream()
            .filter(p -> p.getFileName().toString().endsWith(".exe"))            
            .collect(Collectors.toList()); 
        // do something with found files
    }
```
... or **recursively** using the **Path API**
```java
    import static io.github.codejanovic.java.filesearch.Search.search;

    public void doSomeFileSearchStuff() {
        final List<Path> executables = search().directory("S:\Earch\Path").recursively().byPath()
            .stream()
            .filter(p -> p.getFileName().toString().endsWith(".exe"))            
            .collect(Collectors.toList()); 
        // do something with found files
    }
```
... or **not recursively** using the **File API**
```java
    import static io.github.codejanovic.java.filesearch.Search.search;

    public void doSomeFileSearchStuff() {
        final List<File> executables = search().directory("S:\Earch\Path").notRecursively().byFile()
            .stream()
            .filter(f -> f.getName().endsWith(".exe"))            
            .collect(Collectors.toList()); 
        // do something with found files
    }
```
... or **recursively** using the **File API**
```java
    import static io.github.codejanovic.java.filesearch.Search.search;

     public void doSomeFileSearchStuff() {
            final List<File> executables = search().directory("S:\Earch\Path").recursively().byFile()
                .stream()
                .filter(f -> f.getName().endsWith(".exe"))            
                .collect(Collectors.toList()); 
            // do something with found files
    }
```
