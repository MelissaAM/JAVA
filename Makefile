.PHONY: compile

compile:
	javac Main.java

.PHONY: run

run:
	java Main

.PHONY: clean

clean:
	rm -rf *.class
