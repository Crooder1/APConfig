package github.crooder1.apconfig;

import java.util.ArrayList;
import java.util.List;

public class ListElementMethod<T> extends ElementMethod<List<T>> {

    private final ElementMethod<T> method;

    public ListElementMethod(ElementMethod<T> method) {
        super();
        this.method = method;
    }

    @Override
    public List<T> convert(String s) {

        List<T> list = new ArrayList<>();

        String[] pos = s.split("\u0000");

        for (String p : pos) {
            list.add(method.convert(p));
        }

        return list;
    }

    @Override
    public String asString(List<T> o) {
        if (o.size() == 0) return "";

        StringBuilder sb = new StringBuilder();

        sb.append(method.asString(o.get(0)));

        for (int i = 1; i < o.size(); i++) {
            sb.append("\u0000").append(method.asString(o.get(i)));
        }

        return sb.toString();
    }

    @Override
    public List<T> getDefault() {
        return new ArrayList<>();
    }

}
