package model.data_structures;

import java.util.Iterator;

public interface ITablasHash<K,V>
{
	void put(K key, V value);
	V get(K key);
	V delete(K key);
	Iterator<K> keys();
}
