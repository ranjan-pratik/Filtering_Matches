# Filtering_Matches


### **Introduction :**
			A stand-alone spring-boot microservice to enable users filter matches based on some applied filters

### **Dependency :**
			1. JAVA (8 or higher)
			2. Maven (3.3.0 or higher)
						
### **Run Instructions :**
		1. DEV Mode
			1. Download the source-code from the master branch
			2. Move the downloaded package to a target folder
			3. Start your CLI on the target folder with JAVA and Maven on path
			4. 
			7. Open browser and go to *'localhost:8081/MatchFilter/info'*  (or localhost:*<<custom port number>>*)
		2. PROD Mode
			1. Download the jar file 
			2. Move the downloaded package to a target folder
			3. Start your CLI on the target folder with JAVA on path
			4. To check your JAVA installation run *java -version*
			5. To start the application run *java -jar MatchFilter-0.0.1.jar*
			5. Probe health of application by hitting *http://localhost:8081/MatchFilter/actuator/health*
			
			
### **Assumptions**
			1. No user login feature required, no secuity configurations added
			2. Assuming data does not need to be stored outside the application scope, hence using embeded Mongo DB
			
### **Known Limitations and Bugs**

### **References**
			1. Mongo DB Standalone : *https://www.mongodb.com/dr/fastdl.mongodb.org/win32/mongodb-win32-x86_64-2012plus-4.2.6-signed.msi/download*
			2. Mongo DB setup instructions  : *https://www.npmjs.com/get-npm*

