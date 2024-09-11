package github.crooder1.apconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class APConfig {

    // Default Config Path (from .minecraft folder)
    public static final String defaultConfigPath = "/config/APUtils/";

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    // Current Instance of The Config
    private static APConfig theConfig = null;

    // Directory File
    protected final File directory;
    // Config File
    protected final File configFile;
    // Data in the Config File
    protected JsonObject configData;
    // File name
    protected final String filename;
    // List of All Elements
    public ArrayList<ConfigElement<?>> elementList = new ArrayList<>();

    public ConfigElement<Boolean> autosave;

    public APConfig(String filename) {

        // Get the path to .minecraft folder
        String basePath = ((File)(FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");

        this.filename = filename;
        this.directory = new File(basePath + defaultConfigPath);
        this.configFile = new File(basePath + defaultConfigPath + filename + ".json");

        // Store the Current Config
        theConfig = this;

        // Create Directory if Necessary
        if (!this.directory.exists()) directory.mkdirs();

        // Create Config File if Necessary
        if (!this.configFile.exists()) {

            try {
                this.configFile.createNewFile();
                generateDefaultConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Load the config
        loadConfig();

        // Update values
        // Some elements can be initialized before the config
        // Removes need for certain order when initializing
        elementList.forEach(ConfigElement::updateValue);
        // Create an autosave element
        autosave = new ConfigElement<>("settings", "autosave", this, true, ElementMethod.booleanMethod);
    }

    /**Resets all values in the config.*/
    public void resetConfig() {
        elementList.forEach(ConfigElement::resetValue);
    }

    /**Get the string contents of the config file.*/
    protected String getRawFileContents() throws IOException {

        StringBuilder content = new StringBuilder();

        try (FileReader fileReader = new FileReader(this.configFile)) {

            int i;

            // Read the characters from the file
            while ((i = fileReader.read()) != -1) {
                content.append((char) i);
            }
        }

        return content.toString();
    }

    /**Get Formatted file contents*/
    protected  String getFileContents() throws IOException {
        return getRawFileContents().replace("\r", "").replace("\n", "");
    }

    /**Generates a default config. Resets the config file, and the config object.*/
    protected void generateDefaultConfig() {

        // Create the base config object
        JsonObject object = new JsonObject();

        // Loop through each element to add to the config
        for (ConfigElement<?> element : elementList) {

            // Add the object if the category does not exist
            if (object.has(element.getCategory())) {
                object.getAsJsonObject(element.getCategory()).addProperty(element.getKey(), element.getAsString());
                continue;
            }

            // Create the category object and add the element to it
            JsonObject categoryObj = new JsonObject();
            categoryObj.addProperty(element.getKey(), element.getAsString());

            // Add the category to the object
            object.add(element.getCategory(), categoryObj);
        }

        // Set the config object
        this.configData = object;
        // Save the config
        saveConfig();
    }

    /**Loads the data in the config file into the config object.*/
    public void loadConfig() {

        try {
            // Set the config object from the file
            this.configData = new JsonParser().parse(getFileContents()).getAsJsonObject();

        } catch (Exception e) {

            // If any error occurs run this code
            // Stores the current config (that had an execption)
            // in the old config files folder, and creates a new config

            // Path to the base minecraft folder
            String basePath = ((File)(FMLInjectionData.data()[6])).getAbsolutePath().replace(File.separatorChar, '/').replace("/.", "");

            // Path to the old config directory
            File oldDirectory = new File(basePath + defaultConfigPath + "/old/");

            // Make the directory if it does not exist
            if (!oldDirectory.exists()) oldDirectory.mkdirs();

            // Path to the old config file
            File oldConfigFile = new File(basePath + defaultConfigPath + "/old/" + filename + "_" + dateFormat.format(new Date()) + ".json");

            // Try to create a new file for the config with an exception
            try {
                oldConfigFile.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // Write the contents of the excepted config to the newly created file
            try (FileWriter fw = new FileWriter(oldConfigFile)) {
                fw.write(getRawFileContents());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            // Generate a new default config
            generateDefaultConfig();
        }
    }

    /**Saves the data in the config object into the config filel.*/
    public void saveConfig() {

        // Try to write the config object to the config file
        try (FileWriter fw = new FileWriter(this.configFile)) {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String prettyJsonString = gson.toJson(this.configData);

            fw.write(prettyJsonString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**Checks and creates the given category and element if they do not exist.*/
    protected void validateElement(ConfigElement<?> element) {

        // Add the category to the config object if it is not in the config object
        if (!configData.has(element.getCategory())) configData.add(element.getCategory(), new JsonObject());

        // Get the category object from the config object
        JsonObject categoryObj = configData.getAsJsonObject(element.getCategory());

        // Add the element to the category if it is not in the category
        if (!categoryObj.has(element.getKey())) categoryObj.addProperty(element.getKey(), element.getAsString());
    }

    /**Get the value from the config object given the element.*/
    public String getValue(ConfigElement<?> element) {

        validateElement(element);

        // Return the value of the element
        return configData.getAsJsonObject(element.getCategory()).get(element.getKey()).getAsString();
    }

    /**Set the value of the element in the config object from the element.*/
    public void setValue(ConfigElement<?> element) {

        validateElement(element);

        // Set the value of the element
        this.configData.getAsJsonObject(element.getCategory()).addProperty(element.getKey(), element.getAsString());

        // Save the config if auto saving
        if (this.autosave.getValue()) this.saveConfig();
    }

    /**Get the current config.*/
    public static APConfig getConfig() {
        return theConfig;
    }

}

