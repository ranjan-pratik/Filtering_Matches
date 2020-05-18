# Filtering_Matches


### **Introduction :**
			A stand-alone spring-boot microservice to enable users filter matches based on some applied filters

### **Dependency :**
			1. JAVA (8 or higher)
			2. Maven (3.3.0 or higher)
						
### **Run Instructions :**

		1. DEV Mode
			1. Download the source-code from the master branch
			2. Move the downloaded package to a desired *project* folder
			3. Start your CLI on the *project* folder with JAVA JDK and Maven on Path
			4. Run *'mvn clean install'* on project root path. Wait for the project to build.
			5. Navigate to *<<project-root>>/target* and locate the MatchFilter-0.0.1.jar.
			6. The default port is 8081, to change it configure the *application.properties* in *src/resources* folder. Further configurations may be changed from here. **If the port is changed, the same has to be updated in the *angular-ui project >> src >> environments >> environement fles* as well.** And the project wouldneed to be re-built using command *ng build* or *ng build --prod*
			7. To start the application run *java -jar MatchFilter-0.0.1.jar*
			8. Probe health of application by hitting *http://localhost:8081/MatchFilter/actuator/health*
		
		2. PROD Mode
			1. Download the jar file from *'dist'* folder.
			2. Move the downloaded package to a desired *project* folder
			3. Start your CLI on the *project* folder with JAVA on Path
			4. To check your JAVA installation run *java -version*
			5. To start the application run *java -jar MatchFilter-0.0.1.jar*
			6. Probe health of application by hitting *http://localhost:8081/MatchFilter/actuator/health*
			
			
### **Assumptions**
			1. No user login feature required, no secuity configurations added
			2. Assuming data does not need to be stored outside the application scope, hence using embeded Mongo DB
			
### **Known Limitations and Bugs**

### **References**
			1. Mongo DB Standalone : *https://www.mongodb.com/dr/fastdl.mongodb.org/win32/mongodb-win32-x86_64-2012plus-4.2.6-signed.msi/download* 

