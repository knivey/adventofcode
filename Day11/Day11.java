
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {


    private static class Coord {
        int x;
        int y;
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coord{" + "x=" + x + ", y=" + y + '}';
        }
    }

    public static void main(String[] args) throws IOException {
        var input = Files.lines(Path.of("input.txt"));
        ArrayList<ArrayList<tile>> map = input
                .map(line -> Stream.of(line.split("")))
                .map(x -> x.map(tile::fromString)
                .collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
        map.forEach(System.out::println);

        int maxX = map.get(0).size();
        int maxY = map.size();
        System.out.println(listAdjacent(1,0, maxX, maxY));

        int iterations = 0;
        var mapb = copyMap(map);
        var mapc = copyMap(map);
        int changes = 1;
        while(changes != 0) {
            changes = 0;
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    if (mapb.get(y).get(x) == tile.FLOOR)
                        continue;
                    int occupied = 0;
                    for (var c : listAdjacent(x, y, maxX, maxY)) {
                        if (mapb.get(c.y).get(c.x) == tile.FILLED)
                            occupied++;
                    }
                    if (occupied >= 4)
                        if (mapc.get(y).get(x) != tile.EMPTY) {
                            mapc.get(y).set(x, tile.EMPTY);
                            changes++;
                        }
                    if (occupied == 0)
                        if (mapc.get(y).get(x) != tile.FILLED) {
                            mapc.get(y).set(x, tile.FILLED);
                            changes++;
                        }
                }
            }
            mapb = copyMap(mapc);
            mapb.forEach(System.out::println);
            System.out.println("---");
            if(changes != 0)
                iterations++;
            if(iterations > 100000) {
                System.out.println("giving up");
                break;
            }
        }

        long filled = mapb.stream()
                .flatMap(Collection::stream)
                .filter(x -> {return x==tile.FILLED;})
                .count();
        System.out.println(changes);
        System.out.println(iterations);
        System.out.println(filled);
        //mapb.forEach(System.out::println);
    }



    //fuck this gay earth
    public static ArrayList<ArrayList<tile>> copyMap(ArrayList<ArrayList<tile>> map) {
        ArrayList<ArrayList<tile>> mapc = new ArrayList<>();
        for (ArrayList<tile> tiles : map) {
            ArrayList<tile> tmp = new ArrayList<>();
            for (tile ti : tiles) {
                tmp.add(tile.fromString(ti.toString()));
            }
            mapc.add(tmp);
        }
        return mapc;
    }

    public static ArrayList<Coord> listAdjacent(int x, int y, int maxX, int maxY) {
        ArrayList<Coord> out = new ArrayList<>();
        for(int i = x-1; i <= (x+1); i++) {
            if(i >= 0 && i < maxX) {
                for (int j = y-1; j <= (y+1); j++) {
                    if (j >= 0 && j < maxY)
                        if(!(i==x&&j==y))
                            out.add(new Coord(i, j));
                }
            }
        }
        return out;
    }
}