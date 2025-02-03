package suchai125.amazon.project125.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("session")   //  a new instance of the bean will be created for each HTTP session
public class Cart<K, V extends CartItem> {
    private Map<K, V> map;

    public Cart() {
        this.map = new HashMap<>();
    }

    public int getNoOfItem() {
        return map.size();
    }

    public int getQuantity() {
        return map.values().stream().mapToInt(o -> o.getQuantity()).sum();
    }

    public double getCartTotal() {
        return map.values().stream()
                .mapToDouble(o -> o.getTotal()).sum();
    }

    public String getCartTotalString() {
        return String.format("%.2f", getCartTotal());
    }

    public Collection<V> getAllItem() {
        return Collections.unmodifiableCollection(map.values());
    }

    public void addItem(K key, V value) {
        V item = map.get(key);
        if (item == null) {
            map.put(key, value);
        } else {
            item.setQuantity(item.getQuantity() + value.getQuantity());
        }
    }

    public void removeItem(K key) {
        V item = map.get(key);
        if (item == null) return;
        else if (item.getQuantity() <= 1) {
            map.remove(key);
        }
        else {
            item.setQuantity(item.getQuantity() - 1);
        }
    }

    public V removeAllItem(K key) {
        return map.remove(key);
    }

    public void clear() {
        map.clear();
    }
}