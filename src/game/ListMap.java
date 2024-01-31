package game;

import collections.lists.arrayLists.ArrayOrderedList;

public class ListMap {
    /**
     * List of maps
     */
    private ArrayOrderedList<Map> maps;

    public ListMap() {
        this.maps = new ArrayOrderedList<>();
    }

    /**
     * Add a map to the list
     *
     * @param map
     */
    public void addMap(Map map) {
        this.maps.add(map);
    }

    /**
     * Remove a map from the list
     *
     * @param map
     */
    public void removeMap(Map map) {
        this.maps.remove(map);
    }

    /**
     * Get the list of maps
     *
     * @return
     */
    public ArrayOrderedList<Map> getAllMaps() {
        return maps;
    }

    /**
     * Get a map by its ID
     *
     * @param id
     * @return
     */
    public Map getMapById(int id) {
        for (Map map : maps) {
            if (map.getId() == id) {
                return map;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String str;
        str = "List of maps:\n";
        for (Map map : maps) {
            str += map + "\n";
        }
        return str;
    }
}
