import java.io.File
import kotlin.math.abs
import kotlin.math.round

fun main() {
    var x = 0
    var y = 0;

    // 0 = north
    var dir = 90;
    val lines = File("input.txt").readLines()

    for(line in lines) {
        val cmd = line[0];
        val num = line.substring(1).toInt()
        when (cmd) {
            'N' -> x += num
            'S' -> x -= num
            'E' -> y += num
            'W' -> y -= num
            'L' -> {
                //print("Left: $num start: $dir ")
                val barf = (dir - num) % 360
                dir = if(barf < 0) 360 + barf else barf
                //println("end: $dir")
            }
            'R' -> {
                //print("Right: $num start: $dir ")
                dir = (dir + num) % 360
                //println("end: $dir ")
            }
            'F' -> {
                when (dir/90) {
                    0 -> x += num //North
                    2 -> x -= num //South
                    1 -> y += num //East
                    3 -> y -= num //West
                }
            }
            else -> {println("Unknown instruction $line")}
        }
    }

    println("Ended up at $x, $y  ans: ${abs(x)+abs(y)}")
}