#  Webifycator - Framework used to migrate command line applications to web


There are a lot of command line applications, for which, at some point we want to demonstrate theirs functionalities. The most easy 
to used solution for the end user will be a web application. That's why we have developed a framework which can be used to migrate
any type of command line application (written in any programming language) to a web applications.

## How Webifycator can be used ?

1. Download the **WebifycatorTemplate** folder content and import it as a **maven** project in any IDE.

2. Create the class that represents the UI for the command. This class must extend **LaunchCommandPageUI**. The only methods which 
must be overriden are **doClone()** (returns a similar object with the current one) and **buildPageUI()** (which will be the interface 
for out command). Basically we must create **WebElement** objects and add those to the command UI. Also, we can specify for 
every **WebElement**, a **Verifier** which validated the input/selection of the end user. Example:
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
3. Create a class which specifies how the command is build. Here we must extend **CommandLaunch**. We have also 2 methods to implement:
**doClone()** and **buildCommand()**. You can see a dummy example for a "ls" command webifycation. ParametersMap is a map which
contains as keys the **WebElements** names, and as values the actual input inserted/selected by the end user. Example:
  
  ```
    public String buildCommand(Map<String, String> parametersMap,String workingDirectory) {
        String fileName = parametersMap.get("File name");
        return "ls " + fileName;
    }
  ```  
