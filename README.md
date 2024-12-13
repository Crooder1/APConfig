https://markdownlivepreview.com/
https://www.markdownguide.org/basic-syntax/

# About

APConfig is a standalone code package that gives developers access to an<br>
easy to use configuration, designed with flexibility in mind. This package is<br>
primarily designed for use with developing mods for minecraft, but can be<br>
adapted to any environment.<br>

The main idea behind this configuration is giving the developer the ability<br>
to store any data structure within a text file, as a string. There are several<br>
type conversions included already, however, if you want to see how you can add<br>
conversions for your own data types, go [here].<br>

# Installation

There are 2 ways to install this code package:

### Putting the code directly into your project.

Steps:

1. Download the package as a zip
2. Move the zipped package into the 'src/main/java directory' of your project
3. Unzip the file
4. Delete the leftover zip file
5. You should see a folder called 'github' be created, containing all the necessary files inside
6. Enjoy!

### Using Maven

This will become available in the future!

# QuickStart

To use this package, you need to know how to use the features it gives you. Here<br>
is a quick guide on how to setup and use said features. 

### Setup your config

This should be done in your main mod file, your main mod file should have<br>
subscribe to forges EventHandler. To ensure that all features work, you should<br>
initialize the APConfig object before any ConfigElement objects (this shouldn't<br>
be necessary, however it is safer this way). 

This can be done as follows:<br>
`public static APConfig config = new APConfig("[filename]");`

### Setup your elements

Each element stores has a single Object, to keep it simple, whenever your want<br>
a variable that will be stored in your config, create it as a ConfigElement instead.

This can be done as follows (example with a float):<br>
`public static ConfigElement<Float> xPosition = new ConfigElement<>("[category]", "[key]", ElementMethod.floatMethod);`

This declaration specifies a few things:
- Value type: The type of data stored in the variable
- Category: The category of the element
- Key: The key of the element
- Method: Conversion from String to Object and vice versa

### Finished

Use the features however you like, and adapt them to your circumstances.

# Further In Depth

### Adding your own conversions

Adding your own conversions can be useful for when you need to store your data in a config,<br>
and want an easy way to do this. To do this, create a new implementation of the abstract<br>
ElementMethod class, with your desired object type. Finally implement its methods.

Here is a basic template:

    public static final ElementMethod<[Type]> stringMethod = new ElementMethod<[Type]>() {
        @Override
        public [Type] convert(String s) {
            return /* convert to the object type */ ;
        }

        @Override
        public String asString([Type] o) {
            return /* convert object to a string */ ;
        }

        @Override
        public String getDefault() {
            return [Type's Default Object];
        }

    };



