#  Webifycator - Framework used to migrate command line applications to web


There are a lot of command line applications, for which, at some point we want to demonstrate theirs functionalities. The most easy 
to used solution for the end user will be a web application. That's why we have developed a framework which can be used to migrate
any type of command line application (written in any programming language) to a web applications. Examples of webified applications can be seen at http://www.loose.upt.ro/Webmcc/ and https://dbyaclick.cs.upt.ro/WebTedi/.

## How Webifycator can be used ?

1. Download the **WebifycatorTemplate** folder content and import it as a **maven** project in any IDE.

2. Create the class that represents the UI for the command. This class must extend **LaunchCommandPageUI**. The only methods which 
must be overridden are ```doClone()``` (returns a similar object with the current one) and ```buildPageUI()``` (which will be the interface
for out command). Basically we must create ```WebElement``` objects and add those to the command UI. Also, we can specify for 
every ```WebElement```, a ```Verifier``` which validated the input/selection of the end user. Example:
  ```
    public void buildPageUI() {
            Verifier verifier = new Verifier() {
                @Override
                public boolean verify(String parameter) {
                    return parameter.startsWith("str");
                }
            };
            WebElement fileUpload = new FileUpload("UI Name here");
            WebElement inputText = new InputText("Some name",verifier,"The input text description");
            super.addWebElement(fileUpload);
            super.addWebElement(inputText);
    }
  ```
3. Create a class which specifies how the command is build. Here we must extend ```CommandLaunch```. We have also 2 methods to implement: ```doClone()``` and ```buildCommand()```. You can see a dummy example for a "ls" command webifycation. ParametersMap is a map which contains as keys the ```WebElements``` names, and as values the actual input inserted/selected by the end user. Example:
  
  ```
    public String buildCommand(Map<String, String> parametersMap,String workingDirectory) {
            String fileName = parametersMap.get("File name");
            return "ls " + fileName;
    }
  ```
4. Create a class which specifies how the command results are parsed. This class must extend ```CommandParser```. Similar with steps 2 and 3,
we must override 2 methods : **doClone()** and ```parseCommandResult()```. The following example shows how this method could be overridden.
The framework user must parse the command results here and then return a StringBuilder object containing the command results, which will be
saved in database.

  ```
  	public StringBuilder parseCommandResult(Process p, String workingDir, UserBean user, Map<String,String> parametersMap) {
            return new StringBuilder("Here are the parsed results");
    }
  ```

5. Create a class which will inject all the object created at previous steps. This class must extend ```CommandPageFactory```. Example:

  ```
    protected void setupPages() {
            CommandParser mccCommandParser = new MCCCommandParser();
            CommandLaunch mccCommandLaunch = new MCCCommandLaunch(mccCommandParser);
            LaunchCommandPageUI launchCommandPageUI = new MCCCommandPageUI("Launch MCC", mccCommandLaunch);

            super.addCommandPageUI(launchCommandPageUI);
    }
  ```

6. Now we must fill some configuration files. First there will be appConfig.xml from src/java/resources/ relative to WebifycatorTemplate folder.
The parameters name are self explanatory. For more details please read the full documentation.

  ```
    <properties>
        <entry key="APPLICATION_NAME"></entry>
        <entry key="SSO_PROVIDERS">facebook,google</entry>
        <entry key="FACTORY"></entry>
        <entry key="MAX_COMMANDS"></entry>
        <entry key="EXECUTION_STRATEGY"></entry> <!--QUEUE or REFUSE-->
        <entry key="SMTP_HOST"></entry>
        <entry key="SMTP_PORT"></entry>
        <entry key="MAIL_USERNAME"></entry>
        <entry key="MAIL_PASSWORD"></entry>
        <entry key="OVERVIEW"></entry>
    </properties>
  ```
For every string delimited by "," at the **SSO_PROVIDERS** parameter value, there must be present at the same location as **appConfig.xml**, another
xml file with that string as name. That xml file must have the following format:

  ```
    <properties>
        <entry key="API_KEY"></entry>
        <entry key="API_SECRET"></entry>
        <entry key="DOMAIN"></entry>
    </properties>
  ```
  For more details please read the full documentation.

Also there is one more configuration file which must be filled by the framework user. This one is named context.xml  and
can be found in src/main/webapp/META-INF (relative to WebifycatorTemplate location). This file is read by tomcat application
server and it contains details regarding database connection.

7. The last step is to build our project and launch it in the Tomcat application server. In order to do that, we must first execute the command

  ```
  mvn package
  ```

in the WebifycatorTemplate directory. It will produce in the result folder, a folder named exactly the same, WebifycatorTemplate. This one must be copied
int the Tomcat applications directory, and Tomcat should be restarted.


That's all ! We have now a web versions for our command line application.
