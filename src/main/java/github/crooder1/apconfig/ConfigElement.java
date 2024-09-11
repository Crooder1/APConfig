package github.crooder1.apconfig;


public class ConfigElement<T> {

    private final APConfig config;
    private final String category;
    private final String key;
    private final T defaultValue;
    private final ElementMethod<T> method;
    private T value;

    /**Implicitly set the config and the default value.*/
    public ConfigElement(String category, String key, ElementMethod<T> method) {
        this(category, key, APConfig.getConfig(), method.getDefault(), method);
    }

    /**Implicitly set the default value.*/
    public ConfigElement(String category, String key, APConfig config, ElementMethod<T> method) {
        this(category, key, config, method.getDefault(), method);
    }

    /**Explicitly set all attributes.*/
    public ConfigElement(String category, String key, APConfig config, T defaultValue, ElementMethod<T> method) {
        this.category = category;
        this.key = key;
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.method = method;
        this.config = config;

        config.elementList.add(this);
        updateValue();

    }

    /**Get the default value of the element.*/
    public T getDefaultValue() {
        return this.defaultValue;
    }

    /**Get the current value of the element.*/
    public T getValue() {
        return this.value;
    }

    /**Get the category of the element.*/
    public String getCategory() {
        return this.category;
    }

    /**Get the key of the element.*/
    public String getKey() {
        return this.key;
    }

    /**Convert a string to the generic type of this element.*/
    public T convert(String s) {
        try {
            return this.method.convert(s);
        } catch (Exception e) {
            // Return the default value if any error occurs
            return this.defaultValue;
        }
    }

    /**Get and set the value of this element from the config.*/
    public void updateValue() {
        if (this.config != null) this.value = convert(this.config.getValue(this));
    }

    /**Set the value of this element from a string.*/
    public void setStringValue(String s) {
        setValue(convert(s));
    }

    /**Set the value of this element.*/
    public void setValue(T o) {
        this.value = o;
        this.config.setValue(this);
    }

    /**GSet the value of this element to the default value.*/
    public void resetValue() {
        setValue(defaultValue);
    }

    /**Get the current value of this element as a string.*/
    public String getAsString() {
        return this.method.asString(this.value);
    }

}
