CC = gcc
CFLAGS = -fPIC -g
LDFLAGS = -shared

JAVAC = javac
JAR = jar
JAVA_HOME = $(shell readlink -f /usr/bin/javac | sed "s:bin/javac::")
JAVA_INCLUDE = $(JAVA_HOME)include
JAVA_LINUX_INCLUDE = $(JAVA_INCLUDE)/linux
JNI_INCLUDE = -I$(JAVA_INCLUDE) -I$(JAVA_LINUX_INCLUDE)

MKDIR = mkdir -p

SOURCES = src
BINARIES = lib

JAVA_SOURCES = edu/binghamton/*.class edu/binghamton/**/*.class
JAVA_TARGET = foo-project.jar

.PHONY: %.o
%.o: %.c
	$(CC) -c -o $@ $^ $(CFLAGS) $(JNI_INCLUDE)

.PHONY: %.class
%.class: %.java
	# clean up beforehand or javac will be skipped
	rm -f $@
	$(JAVAC) $^

jar: $(JAVA_SOURCES)
	$(JAR) -cf $(JAVA_TARGET) $^
	# remove the built class files
	rm -f $^

libFoo.so: $(SOURCES)/foo.o
	$(MKDIR) $(BINARIES)
	$(CC) $(LDFLAGS) -o $(BINARIES)/$@ $^ -lc
	rm -f $^

libBar.so: $(SOURCES)/bar.o
	$(MKDIR) $(BINARIES)
	$(CC) $(LDFLAGS) -o $(BINARIES)/$@ $^ -lc
	rm -f $^

smoke_test: jar libFoo.so libBar.so
	java -cp $(JAVA_TARGET) edu.binghamton.Foo
	java -cp $(JAVA_TARGET) edu.binghamton.Bar

foo_experiment: smoke_test
	java -cp $(JAVA_TARGET) edu.binghamton.FooExperiment

clean:
	rm -f $(JAVA_TARGET) $(JAVA_SOURCES) $(SOURCES)/*.o $(BINARIES)/*.so
