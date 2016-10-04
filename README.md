# Android Plugin

## References

[Plugin Deployment](https://www.jetbrains.com/help/idea/2016.2/plugin-development-guidelines.html)

[Intellij SDK](http://www.jetbrains.org/intellij/sdk/docs/index.html)

[Plugin Documentation](https://github.com/JetBrains/intellij-community)

[Sample Android Studio Plugin](https://yalantis.com/blog/android-studio-plugin-development)

## How to install (for user)

File -> Settings -> Plugin -> "Install plugin from disk" -> select oic-plugin.jar file -> restart Android Studio

Right mouse click on a `value-w??dp/dimens.xml` file -> select **"Generate Multidimens Tool"**

## How to install (for Contributors)

### Step 1:

Project Structure:

* Project -> Project Language Level -> 8 - Lambdas, type annotation etc.
* SDK -> SourcePath -> include `Plugin Documentation`

### Step 2:

File -> New -> Project... -> Intellij Platform Plugin -> add Groovy -> Next -> Finish

### Step 3:

Create folder if needed: META-INF (with plugin.xml auto generated)
Update plugin.xml

### Step 4:

Declare Actions, Java classes...

### Step 5:

Make, Build, Run, Debug project. It's will start an Intellij IDE itself with your plugin.

### Step 6:

Build -> Prepare Plugin Module 'module name' for Deployment (a .jar file auto generated)
Copy .jar file to `/Applications/<product_system_name><product_version>/Contents/plugins` and restart IDE

### Step 7:

Go to [Intellij Repository](https://plugins.jetbrains.com/space) -> Add new plugin -> Upload plugin