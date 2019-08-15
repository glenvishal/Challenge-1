## Minimum requirements to execute the code
- Java jdk 8 or higher
- Maven 3.6.0

## Project structure
- src
  - main
    - java/com
    - resources
  - test
    - java/com
    - resources

## Steps to execute the project
1. Clone the project to a local directory using the command `git clone https://github.com/glenvishal/Challenge-1.git`
2. Go to the directory src -> main -> resources and edit the application.properties file. *More about application.properties file in the below section*
3. Go to the directory src -> main -> resources and edit the log.properties file. *More about log.properties file in the below section* 
4. Go to the directory src -> test -> resources and edit the application.properties file
5. Go to the  Root folder which contains the pom.xml file and execute the command `mvn clean install`. Maven will build the project and execute the test cases. **This command will not produce a log file**
6. Another command to execute the code is `mvn exec:java`. This command should be executed from the root folder which contains the pom.xml file. **This command will produce a log file in the given directory**


## Structure of application.properties file

| Properties     |               |    |
| -------------  |-------------  | ---|
| file.path.input| This property specifies the input file path and the file name. Path should be an absolute path. | Example of linux path: `/home/user/temp/Input.txt`. Example of windows path: `C:\\Users\\temp\\Input.txt` |
| file.path.output| This property specifies the output file path and the file name. Path should be absolute path. | Example of linux path: `/home/user/temp/Output.csv`. Example of windows path: `C:\\Users\\temp\\Output.csv` |

## Structure of log.properties file
log.properties file contains lot of configurations. The most important of these to edit are as below

| Properties     |               |
| -------------  |-------------  |
| log4j.rootLogger | This property specifies the root log level and the appender. Different log levels are INFO, DEBUG and ERROR| 
| log4j.appender.file.File | This property specifies the log file path and the file name. Path should be absolute path. Example of linux path: `/home/user/temp/Output.csv`. Example of windows path: `C:\\Users\\temp\\Output.csv`|


