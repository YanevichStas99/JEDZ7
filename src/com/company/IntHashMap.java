package com.company;

import java.util.Arrays;

public class IntHashMap implements IntMap {
    private int size = 0;
    private Entry[] table = new Entry[8];
    private final double loadFactor = 0.75;
    private int count = 0;
    private int maxKey = 0;

    private static class Entry {
        int key;
        int value;
        int hashcode = hashCode();
        Entry next;

        private Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return  "{"+key +
                    "=" + value +
                    '}';
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + key;
            //result = prime * result + value;
            return result;
        }

        @Override
        public boolean equals(Object entry) {
            if (this == entry)
                return true;
            if (entry == null)
                return false;
            if (getClass() != entry.getClass())
                return false;
            Entry other = (Entry) entry;
            if (key == other.key)
                return true;
            /*if (value != other.value)
                return false;*/
            return false;
        }

    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean containsKey(int key) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                if (key == table[i].key) {
                    return true;
                }
                if (table[i].next != null) {
                    Entry tmp = table[i];
                    while (tmp != null) {
                        if (key == tmp.key) {
                            return true;
                        }
                        tmp = tmp.next;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(int value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                if (value == table[i].value) {
                    return true;
                }
                if (table[i].next != null) {
                    Entry tmp = table[i];
                    while (tmp != null) {
                        if (value == tmp.value) {
                            return true;
                        }
                        tmp = tmp.next;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int get(int key) {
        if (containsKey(key)) {
            for (int i = 0; i < table.length; i++) {
                if (table[i].key == key) {
                    return table[i].value;
                }
                if (table[i].next != null) {
                    Entry tmp = table[i].next;
                    while (tmp != null) {
                        if (tmp.key == key) {
                            return tmp.value;
                        }
                        tmp = tmp.next;
                    }
                }
            }
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int put(int key, int value) {
        if (maxKey < key) {
            maxKey = key;
        }
        Entry newEntry = new Entry(key, value);
        if (compare(newEntry)) {
            return -1;
        }
        if ((count + 1) / table.length < loadFactor) {
            putInArray(key, newEntry);
        } else {
            Entry[] newTable = new Entry[table.length * 2];
            Entry[] tmp = table;
            table = newTable;
            for (int i = 0; i < tmp.length; i++) {
                if (tmp[i] != null) {
                    putInArray(tmp[i].key, tmp[i]);
                    if (tmp[i].next != null) {
                        Entry entry = tmp[i].next;
                        do {
                            putInArray(entry.key, entry);
                            entry = entry.next;
                        } while (entry != null);
                    }
                }

            }
            putInArray(key, newEntry);
        }
        size++;
        return 0;
    }

    private void putInArray(int key, Entry newEntry) {
        if (key < table.length) {
            if (table[key] == null) {
                table[key] = newEntry;
                count++;
            } else {
                Entry nextEntry = table[key];
                newEntry.next = nextEntry;
                table[key] = newEntry;
            }
        } else {
            int index = key % table.length;
            if (table[index] == null) {
                table[index] = newEntry;
                count++;
            } else {
                Entry nextEntry = table[index];
                newEntry.next = nextEntry;
                table[index] = newEntry;
            }
        }
    }

    @Override
    public int remove(int key) {
        if (containsKey(key)) {
            for (int i = 0; i < table.length; i++) {
                if (table[i].key == key) {
                    if (table[i].next != null) {
                        table[i] = table[i].next;
                        size--;
                        return 0;
                    } else {
                        table[i] = null;
                        size--;
                        return 0;
                    }

                }
                if (table[i].next != null) {
                    Entry tmp = table[i].next;
                    Entry previusTmp = table[i];
                    while (tmp != null) {
                        if (tmp.key == key) {
                            previusTmp.next = tmp.next;
                            tmp = null;
                            size--;
                            return 0;
                        }
                        tmp = tmp.next;
                        previusTmp = previusTmp.next;
                    }
                }
            }
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public int size() {
        return size;
    }

    private boolean compare(Entry entry) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                if (entry.hashcode == table[i].hashcode) {
                    if (entry.equals(table[i])) {
                        return true;
                    }
                }
                if (table[i].next != null) {
                    Entry tmp = table[i].next;
                    while (tmp != null) {
                        if (entry.equals(tmp)) {
                            return true;
                        }
                        tmp = tmp.next;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        Entry[] fullTable = new Entry[maxKey + 1];
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                fullTable[table[i].key] = table[i];
                if (table[i].next != null) {
                    Entry tmp = table[i];
                    while (tmp.next != null) {
                        tmp = tmp.next;
                        fullTable[tmp.key] = tmp;
                    }
                }
            }
        }
        Entry[] newTable = new Entry[size];
        if (size == 0) {
            return Arrays.toString(newTable);
        }
        int countTable = 0;
        for (int i = 0; i < fullTable.length; i++) {
            if (fullTable[i] != null) {
                newTable[countTable] = fullTable[i];
                countTable++;
            }
        }
        table = newTable;
        return "IntHashMap{" +
                Arrays.toString(table) +
                '}';
    }
}
