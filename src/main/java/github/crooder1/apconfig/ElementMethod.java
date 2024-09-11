package github.crooder1.apconfig;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementMethod<T> {

    static final List<ElementMethod<?>> methodList = new ArrayList<>();

    public static final ElementMethod<Integer> integerMethod = new ElementMethod<Integer>() {
        @Override
        public Integer convert(String s) {
            return Integer.parseInt(s);
        }

        @Override
        public String asString(Integer o) {
            return o.toString();
        }

        @Override
        public Integer getDefault() {
            return 0;
        }

    };

    public static final ElementMethod<Double> doubleMethod = new ElementMethod<Double>() {
        @Override
        public Double convert(String s) {
            return Double.parseDouble(s);
        }

        @Override
        public String asString(Double o) {
            return o.toString();
        }

        @Override
        public Double getDefault() {
            return 0.0d;
        }

    };

    public static final ElementMethod<String> stringMethod = new ElementMethod<String>() {
        @Override
        public String convert(String s) {
            return s;
        }

        @Override
        public String asString(String o) {
            return o;
        }

        @Override
        public String getDefault() {
            return "";
        }

    };

    public static final ElementMethod<Boolean> booleanMethod = new ElementMethod<Boolean>() {
        @Override
        public Boolean convert(String s) {
            return s.equals("T");
        }

        @Override
        public String asString(Boolean o) {
            return (o) ? "T" : "F";
        }

        @Override
        public Boolean getDefault() {
            return false;
        }


    };

    public static final ElementMethod<Color> colorMethod = new ElementMethod<Color>() {
        @Override
        public Color convert(String s) {
            String[] color = s.split(",");
            return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2]), Integer.parseInt(color[3]));
        }

        @Override
        public String asString(Color o) {
            return String.format("%d,%d,%d,%d", o.getRed(), o.getGreen(), o.getBlue(), o.getAlpha());
        }

        @Override
        public Color getDefault() {
            return new Color(0, 255, 0);
        }

    };

    public static final ElementMethod<JsonObject> jsonMethod = new ElementMethod<JsonObject>() {
        @Override
        public JsonObject convert(String s) {
            return new JsonParser().parse(s).getAsJsonObject();
        }

        @Override
        public String asString(JsonObject o) {
            return o.toString();
        }

        @Override
        public JsonObject getDefault() {
            return new JsonObject();
        }

    };

    public static final ElementMethod<Vector2f> vector2fMethod = new ElementMethod<Vector2f>() {
        @Override
        public Vector2f convert(String s) {
            String[] pos = s.split(",");
            return new Vector2f(Float.parseFloat(pos[0]), Float.parseFloat(pos[1]));
        }

        @Override
        public String asString(Vector2f o) {
            return String.format("%.2f,%.2f", o.x, o.y);
        }

        @Override
        public Vector2f getDefault() {
            return new Vector2f(0.0f, 0.0f);
        }

    };

    public static final ElementMethod<Vector3f> vector3fMethod = new ElementMethod<Vector3f>() {
        @Override
        public Vector3f convert(String s) {
            String[] pos = s.split(",");
            return new Vector3f(Float.parseFloat(pos[0]), Float.parseFloat(pos[1]), Float.parseFloat(pos[2]));
        }

        @Override
        public String asString(Vector3f o) {
            return String.format("%.2f,%.2f,%.2f", o.x, o.y, o.z);
        }

        @Override
        public Vector3f getDefault() {
            return new Vector3f(0.0f,0.0f,0.0f);
        }

    };

    public static final ElementMethod<BlockPos> blockPosMethod = new ElementMethod<BlockPos>() {
        @Override
        public BlockPos convert(String s) {
            String[] pos = s.split(",");
            return new BlockPos(Integer.parseInt(pos[0]),Integer.parseInt(pos[1]),Integer.parseInt(pos[2]));
        }

        @Override
        public String asString(BlockPos o) {
            return String.format("%d,%d,%d", o.getX(), o.getY(), o.getZ());
        }

        @Override
        public BlockPos getDefault() {
            return new BlockPos(0,0,0);
        }

    };

    public static final ElementMethod<KeyBinding> keybindMethod = new ElementMethod<KeyBinding>() {
        @Override
        public KeyBinding convert(String s) {
            String[] pos = s.split(",");
            return new KeyBinding(pos[0], Integer.parseInt(pos[1]), pos[2]);
        }

        @Override
        public String asString(KeyBinding o) {
            return String.format("%s,%d,%s", o.getKeyDescription(), o.getKeyCode(),o.getKeyCategory());
        }

        @Override
        public KeyBinding getDefault() {
            return null;
        }
    };

    public ElementMethod() {
        methodList.add(this);
    }

    /**Convert a string to the generic type of this method.*/
    public abstract T convert(String s);
    /**Convert the generic type of this method to a string.*/
    public abstract String asString(T o);
    /**Get the default value of this method.*/
    public abstract T getDefault();

}
