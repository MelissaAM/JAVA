.PHONY: compile

compile:
	javac Main.java Clock.java

.PHONY: run

run:
	java Main

.PHONY: clean

clean:
	rm -rf *.class
