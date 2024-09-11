package github.crooder1.apconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashMapElementMethod<K,V> extends ElementMethod<HashMap<K,V>> {

    private final ElementMethod<K> keyMethod;
    private final ElementMethod<V> valueMethod;

    public HashMapElementMethod(ElementMethod<K> keyMethod, ElementMethod<V> valueMethod) {
        super();
        this.keyMethod = keyMethod;
        this.valueMethod = valueMethod;
    }

    @Override
    public HashMap<K, V> convert(String s) {

        HashMap<K,V> map = new HashMap<>();

        String[] pos = s.split("\u0000");

        for (String p : pos) {

            String[] entry = p.split("\u0001");
            map.put(keyMethod.convert(entry[0]), valueMethod.convert(entry[1]));

        }

        return  map;
    }

    @Override
    public String asString(HashMap<K, V> o) {
        if (o.size() == 0) return "";

        List<K> keys = new ArrayList<>(o.keySet());
        List<V> values = new ArrayList<>(o.values());

        if (keys.size() != values.size()) return "";

        StringBuilder sb = new StringBuilder();

        sb.append(keyMethod.asString(keys.get(0))).append("\u0001").append(valueMethod.asString(values.get(0)));

        for (int i = 1; i < o.size(); i++) {
            sb.append("\u0000").append(keyMethod.asString(keys.get(i))).append("\u0001").append(valueMethod.asString(values.get(i)));
        }

        return sb.toString();
    }

    @Override
    public HashMap<K, V> getDefault() {
        return new HashMap<>();
    }

}
