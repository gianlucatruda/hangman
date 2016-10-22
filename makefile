# Makefile for Hangman Front End

LIB = ../lib
SRCDIR = src
BINDIR = bin

CLI = $(LIB)/cli/commons-cli-1.3.1.jar
ASM = $(LIB)/asm/asm-5.0.4.jar:$(LIB)/asm/asm-commons-5.0.4.jar:$(LIB)/asm/asm-tree-5.0.4.jar
TOOLS = $(LIB)/tools
ACM = $(LIB)/acm.jar

JAVAC = javac
JFLAGS = -g -d $(BINDIR)

# define general build rule for java sources
.SUFFIXES:  .java  .class

.java.class:
	$(JAVAC)  $(JFLAGS) $<

#default rule - will be invoked by make
all: HangmanCanvas.class \ 
	 HangmanLexicon.class \
	 Hangman.class \
	 Play.class

clean:
	@echo "Removing class files..."
	@rm -f  $(BINDIR)/*.class
	@rm -f $(BINDIR)/*/*.class

