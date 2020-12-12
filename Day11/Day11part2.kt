
import java.io.File

enum class Tile(val c: String) {
    FLOOR("."),
    FILLED("#"),
    EMPTY("L"),
    UNKNOWN("?");

    fun from(c: Char) : Tile {
        when (c) {
            '.' -> return FLOOR
            '#' -> return FILLED
            'L' -> return EMPTY
        }
        return UNKNOWN;
    }
    override fun toString(): String {
        return c
    }
}


fun main() {
    val map: ArrayList<ArrayList<Tile>> = File("input.txt").readLines().asSequence()
            .map { line -> line.asSequence() }
            .map { it.map {t -> Tile.UNKNOWN.from(t)} }
            .map { it.toCollection(ArrayList()) }
            .toCollection(ArrayList())


    val maxY = map.size -1
    val maxX = map[0].size -1

    var changes = 1
    var iterations = 0
    while (changes != 0) {
        changes = 0
        val mapc = copyMap(map)
        for ((y, row) in mapc.withIndex()) {
            for ((x, tile) in row.withIndex()) {
                if(tile == Tile.FLOOR) {
                    //print(".")
                    continue
                }
                var occupied : Int = 0
                for(vec in genVecs(x, y, maxX, maxY)) {
                    for (v in vec) {
                        if (mapc[v.y][v.x] == Tile.FILLED) {
                            occupied++
                            break
                        }
                        if (mapc[v.y][v.x] == Tile.EMPTY) {
                            break
                        }
                    }
                }
                if(occupied >= 5)
                    if(tile != Tile.EMPTY) {
                        map[y][x] = Tile.EMPTY
                        changes++
                    }
                if(occupied == 0)
                    if(tile != Tile.FILLED) {
                        map[y][x] = Tile.FILLED
                        changes++
                    }
                //print(occupied)
            }
            //print("\n")
        }

        if(changes != 0)
            iterations++
        if(iterations > 100000) {
            println("giving up");
            break;
        }

        //println("Changed: $changes ------")
        //map.forEach{ println(it.joinToString("")) }
        //println("------------------------")
    }
    val filled = map
            .flatten()
            .filter {it == Tile.FILLED}
            .count()
    println("$filled seats filled")
}

fun copyMap(map: ArrayList<ArrayList<Tile>>): ArrayList<ArrayList<Tile>>{
    val mapc: ArrayList<ArrayList<Tile>> = arrayListOf()
    for(m: ArrayList<Tile> in map) {
        mapc.add(m.clone() as java.util.ArrayList<Tile>)
    }
    return mapc;
}

data class Coord(val x: Int, val y: Int) {
}

//Boy this seems silly
//Will update later to make something that take (stepx, stepy)
fun genVecs(x:Int, y:Int, maxX: Int, maxY:Int) : List<List<Coord>> {
    val out: MutableList<MutableList<Coord>> = mutableListOf()
    /**
     * 123
     * 0o4
     * 765
     */
    for (v in 0..7) {
        var list: MutableList<Coord> = mutableListOf()
        when (v) {
            0 -> list = (x-1 downTo 0).map { Coord(it, y) }.toMutableList()
            1 -> {
                for (i in x-1 downTo 0) {
                    val j = y - (x - i)
                    if (j >= 0)
                        list.add(Coord(i, j))
                }
            }
            2 -> list = (y-1 downTo 0).map { Coord(x, it) }.toMutableList()
            3 -> {
                for (i in x+1..maxX) {
                    val j = y - (i - x)
                    if (j in 0..maxY)
                        list.add(Coord(i, j))
                }
            }
            4 -> list = (x+1..maxX).map { Coord(it, y) }.toMutableList()
            5 -> {
                for (i in x+1..maxX) {
                    val j = y + (i - x)
                    if (j in 0..maxY)
                        list.add(Coord(i, j))
                }
            }
            6 -> list = (y+1..maxY).map { Coord(x, it) }.toMutableList()
            7 -> {
                for (i in x-1 downTo 0) {
                    val j = y - (i - x)
                    if (j in 0..maxY)
                        list.add(Coord(i, j))
                }
            }
        }
        out.add(list)
    }
    return out
}