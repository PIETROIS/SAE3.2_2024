## Variable 

BUILD_DIR = build
SRC_DIR = src
CONTROLLER_DIR = $(SRC_DIR)/controller
ENTITY_DIR = $(SRC_DIR)/entity
EXCEPTION_DIR = $(SRC_DIR)/exception
MAIN_DIR = $(SRC_DIR)/main
MODEL_DIR = $(SRC_DIR)/model
VIEW_DIR = $(SRC_DIR)/view
MANIFEST_DIR = $(SRC_DIR)/manifest
DOC_DIR = doc
JC = javac
JV = java

## Cibles

build: tableur.jar

clean: 
	rm $(shell find $(BUILD_DIR) -name "*.jar")

doc:
	javadoc -d $(DOC_DIR) $(shell find $(CONTROLLER_DIR) -name "*.java") \
			$(shell find $(ENTITY_DIR) -name "*.java") \
			$(shell find $(EXCEPTION_DIR) -name "*.java") \
			$(shell find $(MAIN_DIR) -name "*.java") \
			$(shell find $(MODEL_DIR) -name "*.java") \
			$(shell find $(VIEW_DIR) -name "*.java") \
			$(JCFLAGS)

.PHONY: build clean doc classfiles

tableur: tableur.jar
	$(JV) -jar $(BUILD_DIR)/tableur.jar

run: tableur

all: clean build run

## Règles 

tableur.jar: classfiles
	jar cfm $(BUILD_DIR)/tableur.jar src/manifest/manifest_tableur.txt \
		$(shell find $(CONTROLLER_DIR) -name "*.class") \
		$(shell find $(ENTITY_DIR) -name "*.class") \
		$(shell find $(EXCEPTION_DIR) -name "*.class") \
		$(shell find $(MAIN_DIR) -name "*.class") \
		$(shell find $(MODEL_DIR) -name "*.class") \
		$(shell find $(VIEW_DIR) -name "*.class") 

classfiles:
	$(JC) $(shell find $(CONTROLLER_DIR) -name "*.java") \
		$(shell find $(ENTITY_DIR) -name "*.java") \
		$(shell find $(EXCEPTION_DIR) -name "*.java") \
		$(shell find $(MAIN_DIR) -name "*.java") \
		$(shell find $(MODEL_DIR) -name "*.java") \
		$(shell find $(VIEW_DIR) -name "*.java")
