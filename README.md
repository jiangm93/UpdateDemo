# UpdateDemo
How to

To get a Git project into your build:

GRADLE Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

allprojects {
	repositories {
		...
		maven { url 'https://www.jitpack.io' }
	}
}

Copy

Step 2. Add the dependency

dependencies {
        implementation 'com.github.jiangm93:UpdateDemo:1.0.0'
}

MAVEN:

Step 1. Add the JitPack repository to your build file maven：

<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://www.jitpack.io</url>
	</repository>
</repositories>

Copy

Step 2. Add the dependency

<dependency>
    <groupId>com.github.jiangm93</groupId>
    <artifactId>UpdateDemo</artifactId>
    <version>1.0.0</version>
</dependency>

Copy
