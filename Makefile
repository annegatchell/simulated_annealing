JCC = javac
JFLAGS = -g
JLINT = -Xlint

MAIN_SRCS = src/main/*.java
MAIN_CLASSES = $(MAIN_SRCS:.java=.class)

TEST_SRCS = src/test/*.java
TEST_CLASSES = $(TEST_SRCS:.java=.class)
TEST_EXE = $(TEST_SRCS:.java=)

ALL_SRCS = $(MAIN_SRCS) $(TEST_SRCS)
ALL_CLASSES = $(MAIN_CLASSES) $(TEST_CLASSES)

.PHONY: all clean test main testsRun lint

all: test main
runExp: main runExperiment 
testsRun: test main runTests
test: $(TEST_CLASSES)
main: $(MAIN_CLASSES)

runTests: $(TEST_EXE)
	java src.test.AllTests

runExperiment: $(MAIN_CLASSES)
	java src.main.Experiment

$(TEST_CLASSES): $(TEST_SRCS)
	$(JCC) $(JFLAGS) $(TEST_SRCS)

$(MAIN_CLASSES): $(MAIN_SRCS)
	$(JCC) $(JFLAGS) $(MAIN_SRCS)

clean:
	$(RM) src/main/*.class src/test/*.class