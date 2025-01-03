package org.achacha.aoc.year2023

import kotlinx.coroutines.runBlocking
import org.achacha.common.asString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class Day10Test {

    @Test
    fun parseInput() {
        val day1 = Day10(sampleData)
        assertEquals(5, day1.grid.size)
        assertEquals(5, day1.grid[0].size)
        assertEquals(1, day1.startPoint?.first)
        assertEquals(1, day1.startPoint?.second)
        assertEquals(sampleData, day1.grid.asString())

        val day2 = Day10(nonSquareGrid)
        assertEquals(5, day2.grid.size)
        assertEquals(9, day2.grid[0].size)
        assertNull(day2.startPoint)
        assertEquals(nonSquareGrid, day2.grid.asString())
    }

    @Test
    fun `find starting point`() {
        val next1 = Day10(sampleData).findFirstClockwiseStartPoint()
        assertEquals(Pair(2, 1), next1)

        val next1c = Day10(sampleDataWithExtras).findFirstClockwiseStartPoint()
        assertEquals(Pair(2, 1), next1c)

        val next2 = Day10(nonSquareGrid).findFirstClockwiseStartPoint()
        assertEquals(Pair(6, 2), next2)

    }

    @Test
    fun `find next point`() {
        val day = Day10(nonSquareGrid)

        // F
        assertEquals(Pair(2, 2), day.next(Pair(2, 1), Day10.Direction.W))
        assertEquals(Pair(3, 1), day.next(Pair(2, 1), Day10.Direction.N))

        // -
        assertEquals(Pair(3, 1), day.next(Pair(4, 1), Day10.Direction.W))
        assertEquals(Pair(5, 1), day.next(Pair(4, 1), Day10.Direction.E))

        // 7
        assertEquals(Pair(5, 1), day.next(Pair(6, 1), Day10.Direction.N))
        assertEquals(Pair(6, 2), day.next(Pair(6, 1), Day10.Direction.E))

        // |
        assertEquals(Pair(6, 1), day.next(Pair(6, 2), Day10.Direction.N))
        assertEquals(Pair(6, 3), day.next(Pair(6, 2), Day10.Direction.S))

        // J
        assertEquals(Pair(5, 3), day.next(Pair(6, 3), Day10.Direction.S))
        assertEquals(Pair(6, 2), day.next(Pair(6, 3), Day10.Direction.E))

        // L
        assertEquals(Pair(3, 3), day.next(Pair(2, 3), Day10.Direction.S))
        assertEquals(Pair(2, 2), day.next(Pair(2, 3), Day10.Direction.W))
    }

    @Test
    fun `navigate to S`() {
        val day = Day10(sampleData)
        assertEquals(8, day.navigateUntilS())
        assertEquals(4, day.part1())
        println("day\n---\n" + day.grid1.asString())
        assertEquals(
            """
.......
.......
..S-7..
..|.|..
..L-J..
.......
.......
""".trimIndent(), day.grid1.asString()
        )
        day.floodFill()
//        println("day\n---\n"+day.grid1.asString())
        assertEquals(
            """
~~~~~~~
~~~~~~~
~~S-7~~
~~|.|~~
~~L-J~~
~~~~~~~
~~~~~~~            
""".trimIndent(), day.grid1.asString()
        )
        assertEquals(1, day.countUnfilledSpaceGrid1())

        val daye = Day10(sampleDataWithExtras)
        assertEquals(8, daye.navigateUntilS())
        assertEquals(4, daye.part1())
        assertEquals(
            """
.......
.......
..S-7..
..|.|..
..L-J..
.......
.......
""".trimIndent(), daye.grid1.asString()
        )
        daye.floodFill()
//        println("daye\n---\n"+daye.grid1.asString())
        assertEquals(
            """
~~~~~~~
~~~~~~~
~~S-7~~
~~|.|~~
~~L-J~~
~~~~~~~
~~~~~~~
""".trimIndent(), daye.grid1.asString()
        )
        assertEquals(1, daye.countUnfilledSpaceGrid1())


        val dayc = Day10(sampleDataComplex)
        assertEquals(16, dayc.navigateUntilS())
        assertEquals(8, dayc.part1())
        assertEquals(
            """
.......
...F7..
..FJ|..
.SJ.L7.
.|F--J.
.LJ....
.......
""".trimIndent(), dayc.grid1.asString()
        )
        dayc.floodFill()
//        println("dayc\n---\n"+dayc.grid1.asString())
        assertEquals(
            """
~~~~~~~
~~~F7~~
~~FJ|~~
~SJ.L7~
~|F--J~
~LJ~~~~
~~~~~~~
""".trimIndent(), dayc.grid1.asString()
        )

    }

    @Test
    fun `part1 test`() {
        val dayc = Day10(fullData)
        assertEquals(6897, dayc.part1())
    }

    @Test
    fun `test flood fill`() {
        val day0 = Day10(sampleData)
        day0.grid1 = day0.grid      // This will be populated while navigating
        day0.floodFill()
        assertEquals(
            """
~~~~~
~S-7~
~|.|~
~L-J~
~~~~~""".trimIndent(), day0.grid1.asString()
        )
        assertEquals(1, day0.countUnfilledSpaceGrid1())

        val day1 = Day10(fillableGrid)
        day1.grid1 = day1.grid
        day1.floodFill()
        assertEquals(
            """
~~~~~
~-|-~
~~|~~
~~~~~
""".trimIndent(), day1.grid1.asString()
        )
        assertEquals(0, day1.countUnfilledSpaceGrid1())
    }

    @Test
    fun `fill enclosed`() {
        val day = Day10(
            """
S------7
|F7F-7.|
||||FJ.|
||||L--J
LJLJ....
""".trimIndent()
        )
        print("part2=" + day.part2())
        print(day.grid1.asString())

    }

    @Test
    fun `part2 flood fill test`() {
        val day1 = Day10(part2example1Data)
        day1.navigateUntilS()
        day1.floodFill()
//        println(day_e1.grid1.asString())
        assertEquals(
            """
~~~~~~~~~~~~~
~~~~~~~~~~~~~
~~S-------7~~
~~|F-----7|~~
~~||~~~~~||~~
~~||~~~~~||~~
~~|L-7~F-J|~~
~~|..|~|..|~~
~~L--J~L--J~~
~~~~~~~~~~~~~
~~~~~~~~~~~~~
""".trimIndent(), day1.grid1.asString()
        )
        assertEquals(4, day1.countUnfilledSpaceGrid1())

        val day2 = Day10(part2example2Data)
        day2.navigateUntilS()
        day2.floodFill()
        println(day2.grid1.asString())
        assertEquals(
            """
~~~~~~~~~~~~~~~~~~~~~~
~~F----7F7F7F7F-7~~~~~
~~|F--7||||||||FJ~~~~~
~~||~FJ||||||||L7~~~~~
~FJL7L7LJLJ||LJ.L-7~~~
~L--J~L7...LJS7F-7L7~~
~~~~~F-J..F7FJ|L7L7L7~
~~~~~L7.F7||L7|.L7L7|~
~~~~~~|FJLJ|FJ|F7|~LJ~
~~~~~FJL-7~||~||||~~~~
~~~~~L---J~LJ~LJLJ~~~~
~~~~~~~~~~~~~~~~~~~~~~""".trimIndent(), day1.grid1.asString()
        )
        assertEquals(8, day2.countUnfilledSpaceGrid1())
    }

    @Test
    fun `test aggressive fill 1`() {
        val dayA = Day10(
            """
F--SF--7
|.FJL7.|
|.|..|.|
|.L--J.|
L------J
        """.trimIndent()
        )

        dayA.navigateUntilS()
//        println("BEFORE\n"+dayA.grid1.asString())
        runBlocking { dayA.fillGrid1Aggressive(dayA.startPoint!!, Day10.Direction.E) }
//        println("AFTER\n"+dayA.grid1.asString())
        assertEquals(
            """
~~~~~~~~~~
~F--7F--7~
~|.FJL7.|~
~|.|~~|.|~
~|.L--J.|~
~L------J~
~~~~~~~~~~
""".trimIndent(), dayA.grid1.asString()
        )
    }

    @Test
    fun `test aggressive fill 2`() {
        val dayA = Day10(
            """
FS..            
||..
|L-7
|..|
L--J
""".trimIndent()
        )

        dayA.navigateUntilS()
//        println("BEFORE\n"+dayA.grid1.asString())
        dayA.expandGrid1ToGrid2()
//        println("AFTER EXPAND\n"+dayA.grid2.asString())
        runBlocking { dayA.floodFillGrid2(Pair(0, 0)) }
//        println("AFTER FILL\n"+dayA.grid2.asString())
        dayA.normalizeAfterFillGrid2()
//        println("AFTER NORMALIZE\n"+dayA.grid2.asString())
        assertEquals(2, dayA.countUnfilledSpaceGrid2())
    }

    @Test
    fun `test part2 part2example1Data`() {
        val day = Day10(part2example1Data)
        assertEquals(4, day.part2())
    }

    @Test
    fun `test part2 part2example2Data`() {
        val day = Day10(part2example2Data)
        assertEquals(8, day.part2())
    }

    @Test
    fun `test part2 fullData`() {
        val day = Day10(fullData)
        println("COUNT: ${day.part2()}")
//        assertEquals(367, day.part2())
    }


    // start at 1,1
    val sampleData = """
.....
.S-7.
.|.|.
.L-J.
.....
""".trimIndent()

    val sampleDataWithExtras = """
-L|F7
7S-7|
L|7||
-L-J|
L|-JF
""".trimIndent()

    val sampleDataComplex = """
..F7.
.FJ|.
SJ.L7
|F--J
LJ...
    """.trimIndent()

    val nonSquareGrid = """
.........
..F---7..
..|...|..
..L---J..
.........
""".trimIndent()

    val fillableGrid = """
.....
.-|-.
..|..
.....
""".trimIndent()

    val part2example1Data = """
...........
.S-------7.
.|F-----7|.
.||.....||.
.||.....||.
.|L-7.F-J|.
.|..|.|..|.
.L--J.L--J.
...........
""".trimIndent()

    val part2example2Data = """
.F----7F7F7F7F-7....
.|F--7||||||||FJ....
.||.FJ||||||||L7....
FJL7L7LJLJ||LJ.L-7..
L--J.L7...LJS7F-7L7.
....F-J..F7FJ|L7L7L7
....L7.F7||L7|.L7L7|
.....|FJLJ|FJ|F7|.LJ
....FJL-7.||.||||...
....L---J.LJ.LJLJ...
""".trimIndent()

    val fullData = """
|J.7F7-L--77F7-J-F-77FF-7FLLJ7F-J-F---7-J-L-FJ-FJFJ7-7-FF--L7.L--|.FJ-J77FFF|77-7F-7-FFL7-|7-F7-L7F7FF7.|----LL-77FF.F--F-J-LFFL-F-LF7--F-FL
L.---7FF--LJ7J7..L7LFJ.||FJL|LJ.|77.LJ|F|||7J|.|7|JJFF-L--F-|7..L.F-J7JLL--7|J|7.|L-7-7J|F-7-L|J|||-F7F-FJ|..|LFJLJ.-.|||JJ-LFL.||.L|L-JLJJ.
.|L|7|FJJ7L-7F777L|FLJ-FFL-FL-7FJ.|F-LLLJ||.L--77J7-FLJ.L-F-F-7.LFLJ-|L||..77.|F77-L7JL-7JFJ7|FF-L7-LL--|-L.|-7L-J|7..L7||.LFJ7L7JF7.7J7-7-|
LFF--7F|FJLLLJL|7J||.LF-JLL|J.|77F-J7.LJ-|--7F7|L7--|.L.7J||F7L7L7F|FF7-J|7||7F||J7-7.LJJ7J--L7JL7||.LL-||L77||-LF-7FF7L--F-LFJJJF|L7L---JJ|
.LL|L7JF|||..L7|.FF-77|||77.LL7-JJJLL.F..LF-FJLL-L7L|7|F|FF7F7JF7JF7|||7--|7FFFJ|FF7F777..F-J-|.LJ|FFF-7FF.|L-J.FJ.|7LJ.L7|FLFJJF||LL.|.|J.J
JJL7F|FL-77-L-7F7-|.|F-JJL777L|FLJ..|---FF..|.L-7FF||-LFF7||||-|JFJ|FJ|J7L|JFFL7L7||||-F7FJL7L777LJ-|JF||J7F-J77||.L|7J--J|L.-.77FJ.L7-7L.-L
FF.LJ-JLFJ|-|F-|JF77-F7JF|JF7||7J7F-7.LFJL-77.L7|F7F77.FJ||||L7JF|FJL7|F|F7F---JFJ||||-77LL-|7|FJ-|..F--7|FF7|L-F-J..|J|.LF||F-.LJ|7.|.||||.
LJ.|FJ|-|JJJ|LL--|JL-LJ7L||L-LF7LFF7F77JJ7JF7-F-7||||F7L7LJ||FJFFJL--JL7F-7L7F-7L-JLJL7.7||F77JJF-J--LJL-F-7F7.||7F7-7-L7F-7L|J-F|L7LL-F-L7.
LLF--JFF.L.F|J.|LL----F77JF7||LL-FJLJL7.F7.|L7|FJ|LJ|||-L-7LJ|F7L-7F---JL7L7LJFL7F----J-F7FJL7JF|LJJ-.F-J|FJ||F7F777.|LLFJLL-J-|FJ-|L|-|.L|7
||||-F.F7.FF--7L-J7.J.LJJ7L|-7.LFL-7F-JF-LFL7||L-JF-J||F7-L7FJ|L7-|L-7|LF|FJ7FL.||JF7.LFJLJF-J.F|-LJ|.LFFJL7|||LJ|F7-F..7J7.FJ.|J..LLJJ||-7.
FFL.FF-F7.F|-..-.7FJ7.J7LL-LFL-7-|FJL7-|J-F7||L--7L--JLJ|7FJL-JFJFJF-J-.FJ|F7JLFJ|FJL77L--7|JF7F77L|F77LL-7|||L7FJ||7F.F7L|JLFJJ|.7--7.JJ|.|
--JF|J7L--FJLLJL-|-||-.L-|.FJJLJFF|F-JF-7FJLJ|F7FJF-----JFL7F-7|FJFJF7LFL7|||FFJFJL-7L7.F-JL7||||FFF77-LF-J||||||FJL--7|L-7JJ|-FL-J.FF-JL7JJ
L7FJ.LF77||.7F|FFJ.L7.F-F|.JJ..F7|||F7|FJ|F-7LJ|L7|F--77F-7LJ7|||FJFJL77FJ|||FJFJF7|L7L7L-7FJ||||FFJ|77|L-7||L7|||F---JL7FJ..|-FF7L-|.J-7JF-
-|F-77L77FF-JLJ-|FFJF.7FLL7.FF-JL-JLJ||L7LJFJF7L-J||F7L7L7L7F-J|||-L7FJFJFJ|LJFJL|L--JFJF7|L-JLJL7L7|77-L-|||FJ|||L-7F--JL7F77FF|.-JF7|F7FFL
FJ|-.-7|FJJFFL7JL-L7|-LL-|L-FL-7F7F7FJ|FJJ|L-JL7F-JLJL7|FJFJ|F7LJL7FJL-JFJ-L7FJJFL---7L7|||F7F---J.||F7|-FJLJL-J||F7|L--7FJ||F77JJ||L|LFJ|J.
JJJJ.|FLJLFL7L|-7|LF|7LLLJ|7F.LLJLJ||FJ|JF7F7FFJL7F7F-J||FJ7LJL-7FJL---7|F7F|L-77F7F-JFJ||||LJF7F7F|LJL7FJF--7F-J||LJF7FJL-JLJ|-J|FJ7LF.L|J-
L|FFJJJJ..L7.F|.F77.|J-F7F|F|7FF-7L|||FJFJLJL7L7FJ||L7FJ||-F7F7L||.F7F7||||FJF-JFJ|L-7L-JLJ|JFJ||L7L--7|L-JF7||F-JL7FJ||F-----JL7-|J..L7||..
F-7|7J|JFF-77L-7FF|-||.|.F7F-7LL7L-JLJ|JL-7F-JFJ||||FJL-JL7||||FJL7|||LJLJLJFJF7L7|F-JF7F-7L-JFJL7L-7FJL7LFJLJLJF--JL7||L-7F7F7F77|-.77FLF-|
.L|LJ-FJ|L-7L.L-.L|.F--FL||7-F7|L---7FJF7J|L7.L7L7||L----7|||||L-7LJ|L7F7F7FJ7||FJ||F7||L7L---JF7|F-J|F7L7L--7F-JF-7FJLJF7LJLJLJL7-.FJFL|J.L
7L|J-FL--7.-...L77L-JLF7J|L7F||F----JL7||FJFJF7L7|||F--7F||||||F7L-7L-J|||LJF7||L7|LJ||L-JF7JF7|LJL-7LJL7|F7FJ|F7L7|L7F7||F7F7F--J--.F-J|JFJ
|J|7..J|F77.77-FJF--F-J|FJFJF||L--7F7FJ||L7L7|L-J|||L-7|FJLJ||LJ|F7|F--JLJF7|||L7||F-JL77FJL7||L-7F-JJF-J|||L7||L7||L||LJLJ|||L-7|FLL|F-7||J
L-|L-J.7-J|-LJLJ-|JLL-7LJFJF7|L7F7LJ||F||FJFJL--7LJL-7||L--7||F-J|LJL----7|LJ||FJ||L-7FJFJF-J||FFJL--7L-7|||||LJFJ||FJL77F7LJL-7|777L|LJLF7.
LFLJ...L7.L7.F7J7F.FLL|F7L-JLJFJ||F-JL-JLJFJF7F7L-7F-J||F7FJLJL-7L7F--7F7||F7LJL-JL7J||7L7L7FJ|FJF---JF-JLJL7L-7L-JLJF-JFJL7F7LLJL|7-F.|-J77
FF-FL-7LJ.L|77|FF|JJ7LLJL7F--7|FJ|L----7F-JF|LJL7L|L-7|LJLJF-7F-JL||F7LJLJLJL-7F---JFJ|F7|FJ|FJ|FJF-7FJF7F7FJF7L7F7F7L--JF7LJL--77|J.|LF-L7J
-F-F-JJ7L-FLJ7F|F-7||-|F7LJF-J||FJF-77FJL--7L-7FJFJF-JL----JFJL7LFJLJL7F7F7F7FJL--7-|FJ|LJL-JL7|L7L7|L-JLJ|L-J|FJ|LJL----JL-----JF777L7J--J|
JJ.LFLL------J7-JLF-FF7||F7L-7|||FJFJFJF--7|F-JL7L7L7F7F7F--JF7L7L7F--J||||||L7F--JFJL7L----7FJ|FJFJL-7F--JF--JL7L--7F7F7F7F7F7F-JL-7||.-J-7
FJ-FFF7J-JF|.|JL||LF7||||||F-JLJ|L7L7L7|F7LJL--7|||FJ||||L7F-J|FJFJL7F7|||LJL7|L7FFJF7L7F7F-JL7||F|F--JL--7|F---JF--J|LJLJLJLJLJF---J7J.LJ||
--F-|JJ.|-|7-7.FJF-J||||LJLJF--7L7|FJ-|LJL7LF77||FJ|F|LJ||LJ-FJL7L7FJ||||L--7|L7|FJFJL-J||L--7LJL7|L7F7F7FJLJF7F7L---JF--7F7F--7L7|FLJL7|LFJ
.|..L77FF-JJF-777L-7LJ||F---JF7|FJ|L-7|F--JFJL-J|L7|FJF-JF77FJF-JFJ|J||||F77||FJ||FJ7F-7|L7F7|F--J|FJ||||L-7FJLJL-----JF7LJ|L-7L-J-F-J-FJ|||
-L7.F-FFJ||LL7|F-77L-7|LJF7F-J|||FJF-J||FF7L---7L7||L7L-7|L7L7|F7L7|FJLJ|||FJLJFJ||F7L7||FJ|LJL7F7||FJ|||F7|L7JF7F7F---JL-7|F7L----7LJLJ7L-7
L7|J7|J||F-7FJLJFJF--JL7FJ|L-7||||FJJFJL-J|F7F7|FJ||-|F-JL7|FJLJ|FJ||F--J||L--7|FJ||L7|LJL7L-7FJ|LJ|L7LJ|||L7L-JLJLJF-----J||L7F---J7.FFLJ||
LF|-77-L7L7|L--7L-JF7F7||FJF-J|||||F7L7F-7||LJ|||FJ|FJL7F7||L--7LJFJ|L7F7||F7FJ|L7|L7||F7FJF7||LL-7L7L-7|||FJF7F-7F7L--7F7LLJFJL--7.77F7|F-.
F7L-JJLFLJ|L7F7L--7||||LJL7L-7|||||||FJL7LJL-7LJ||FJL7FJ||||F7FJF-JL|FJ|LJ|||L7|FJL7||LJ|L7|LJL7F7|FJF-J||||FJLJJLJL--7LJL7F7|F---J7J-JL77.L
F|-.||FFL-L7LJL---J|||L-7FJF-JLJ|||||L-7L7F7|L-7|||F-J|FJ|||||L7|F7FJL7L7FJ|L7||L-7||L7FL7||F--J|||L7L-7||||L7F------7L7F7LJ|LJF--7JF7.LJF-7
.LJF-7J..JFL--7F-7FJLJF-JL-JF--7|||||F7|FJ||F7J||||L-7|L7|||||F||||L7FJL||.|FJ||F7|||FJF7||||F7J|||FJF7|||||FJL7F---7|.LJL--JF7|F-J.|7FLFL-7
||.-7|.L|FF.|FJ|.S|LF7L----7|F-JLJ|||||||FJ|||FJ|||F7|L7||||||FJ|||FJ|F-JL7|L7|||||||L7|||||||L7|||L7|LJLJLJL--JL7F7LJF7F-7LFJLJL--7JJ7.--7|
J7FL-J77L-J.LL-JFJ|FJL----7||L-7F7LJLJ|||L7LJ|L7|||||L7||LJ||||FJ|||FJL-7FJL7||||||LJFJ|||||LJFJ|||FJL7F7F7F7F7F-J||F7|||FJFJF7F7F7|.FL7LFJJ
|L|7||7-|J.FJLF-JFJL-----7|LJF7||L---7LJL7|F7L7||||||FJ|L-7LJLJL7||||F-7|L7FJ||LJ|L-7L7|LJ|L-7|FJ||L7FJ|LJLJLJLJF7|||LJ||L-JFJLJ||LJ7-|.FJL.
F7|FF|J---L|.FL7FJF--7JF7|L7F||LJF---JF--JLJL7LJ||LJ||FJF-J|F---J|LJ||J|L7|L7|L7FJF7L7LJF-JF-J||F||FJL7L---7F7F7|||||F-J|F--JF-7LJ||J7LF7--F
|LL|.LL--7.7-F7||JL-7L7||L7L7|L7JL---7|F7F7F7L7FJL-7||L7L--7L7F7FJF-JL7|FJL7||FJL7|L7|F-JF7|F-JL7||L7FJFF-7||||||LJLJL-7||F7LL7L7F-77||||F|.
L...--LL.LF-7||LJF7LL7LJL7L7|L7L--7F7|LJ||||L7||F7FJ||7L7F-JF||||.L-7FJ|L7FJ||L7FJ|FJ|L-7|LJL7F7|LJ-LJF7L7|||LJLJF-7F7FJ|LJ|F7|FJ|FJ--FJL-77
.-F-.-7LF-L7||L--JL77L7F7L-JL-JF-7LJLJF7||||FJ|LJ||FLJF-J|F-7LJ|L7F-JL7|FJ|FJ|FJL-J|FJF-JL-7-||||F-7F7|L-JLJ|F7F-J7||LJFJF-J|LJL-JL-7|L7F-J7
FJL7.F7-LF-J|L----7|F7LJL7F----J7L-7JFJ|||||L7L7FJL7F7|F7LJFJF7L-JL-7FJ|L7|L7LJF--7|L7|F7F7L7LJLJL7LJ|L---77||LJF-7|L--JFJF-JF------JF7|L7JL
|--|FL7|JL-7|F7.F7||||F--J|F----7F7L-JFJLJ||FJFJL-7|||LJ|F7L-JL7F---J|J|FJL7|F7L-7LJFJ|||||FJF----JF7L----JFJL--JFJL----JFJF-JF7|F---JLJFJ.|
L7F|7JLF7||||||FJLJLJLJF7FJL---7LJL---JF7LLJL7L-7FJ||L--J||F-7FJ|F7F7|FJL-7|LJ|F-JF7L7LJ|||L7|F7F--JL7F---7|F-7F7L--7|F-7L7L--JL-JF-7F-7L-7J
LLFJLJFJ|F-JLJ|L---7F-7|LJF----JF7F--7FJL--7LL--JL-J|F7F-JLJFJL7LJLJ||L-7FJL7FJ|F7|L7L-7|||FJLJLJF---J|F--J|L7||L7F7L7L7|FJF-7F7F-JFJ||L7FJJ
.|..L-L7|L-7F7|-F-7LJJLJF7L-7F7FJLJF7LJF---JF--7LF-7LJ|L---7L-7L-7F-JL7FJL7L|L7||||FL7FJLJ||F7F7FJF7F-JL---JFJ|L7||L7L-JLJFJ7LJLJF7L7L-7LJJJ
FF|-FLFJL-7|||L7L7L7F7F-JL-7LJLJFF7||F7L-7|FJF7L7L7L--JF-7FJF-JF-JL-7FJL7FJFJFJ||||F-J|F-7|||LJ|L-J||F--7F7FJ|L-JLJ7|F--7FJF7F7F-JL7|F7L-7-7
FFJF|LL--7LJ|L7L7|FJ||L---7|F----JLJLJL--JFJFJL-JFL7F-7L7|L7|F7L7F--JL-7LJFL7L7|||||F7LJFJLJ|F7|F7LLJL-7LJLJ|F---7F7LJ7FJ|7|LJLJF7FJLJ|F7|7F
.FJ-F---7L--J|L7LJL7||F7F-JLJF7F7F----7F-7L7L7F7F7|LJ7L7||FJ|||FJ|F7F-7|F--7|FJLJLJ|||F7L--7LJ|LJL-----JF-7F7|F--J|L--7L7L7L7F--J|L--7||LJ-L
F|F-L--7L7JF7F-JF-7||||LJF7F7|||||F---J|FJFJFJ|||L-7FF7LJ||FJ|LJFJ||L7LJL-7LJL-7-F-J||||F--JF-JF7F7F----JFJ|||L---JF-7L7L7L7|L--7L7F-JLJ7JFL
F7-L|JJL7L-J|L--JFJLJ|L--JLJLJLJLJL-7F-JL7|FJ.|||F-JFJL-7LJL7L7J|FJL7L-7F-JF7F7L7L7FJ||||F--JF7|||||F----JFJLJF7F-7L7|FJ|L7LJF--J|LJ7L|FF.F|
77J-|F7.L--7|F7F7L--7||F7F7F--------J|F--J|L-7||||F7L7F7L7F-JFJFJL-7|F7||F7|LJ|FJ|LJFLJLJL-7FJLJ||LJL-----JF--JLJFJFJLJF7-|F7L------77|F77FF
|JJ7L-.LF--JLJLJL7F7|L7||||L--7F-----JL---JF-J|LJLJL7||L7||F7L7|F-7|||||||||F-JL7F7F----7F7LJLF7LJFF7F7F7F7L---7JL7L---JL7LJ|F7F----J-7J.7--
||.|-|-L|F7F7F-7FJ||L7|||||F7FLJF----7F-7F7L--JF7F-7LJL7LJLJ|FJ||FJ|||||||LJL7F7||LJF-7FJ||F7FJL7F-JLJLJLJL7F--JF7L7F----JFFJ|||7F7JJJ|J7LL|
F|J|.F.F||LJLJFJL-JL-JLJLJLJ|F7FJF--7|L7|||F-7FJ||FJF-7L---7||FJ|L7|||||LJFF-J|||L7FJ-|L-JLJ|L-7|L--------7||F--JL7|L7F7F7FJFJ|L-J|L||FF77L|
JL||7.FLLJ7F--JF-7F7F---7F-7|||L-JF-J|FJ||||7LJJLJ|FJ-|F---JLJ|FJLLJ||LJ.F-JF7|LJFJ|F-JF-7F7L--JL---------JLJ|F--7|L7LJLJ|L-JLL7F7L--77JLFL|
|.JJL7J..F-JF-7|FJ|||F7FJ|-||||7F7L-7LJFLJ|L-----7|L7FJL---7F7LJF---JL--7L-7|LJF7|FJ|F7L7||L7F7F7F7F7F-------JL-7LJ7|F--7|F-7F7LJ|F-7L7-F7||
F7L-J|F-FL-7|JLJL7|||||L-JFJLJL-JL--JF7F-7|F-----JL7|L7F7F-J||F-JF7F-7F7|JFJL7FJLJ|-||L-JLJFJ|LJLJLJ||F------7F7L7F7|L7FJLJFJ|L7LLJ.L-JJL|--
JJ7.F7|L|J.LJF--7LJLJ|L-7FJF7F7F-----JLJFJ|L-77F7F7LJJ||||F7||L--J||FJ|LJFJF7||F-7|FJ|F----JFJF----7LJL--7F-7||L7LJ|L-JL7F7L7|FJF-77J.|7.LJ.
|.FF|77|F----JF7L----JF7LJFJ||LJFF------J-L-7L-JLJL7F-J||LJLJL-7-LLJL7L7L|FJ|||L7LJL-JL----7|7L-7F7L--7F7LJFJLJLL7FJF7|L||L7LJL7|FJ|JF7|7F|7
|-L-L-JFL7F--7||F7F7F7|L-7L7LJF-7L-7F7F7F7F7L-7F---JL7FJ|F-7F7FJF-77FJFJJLJJLJ|FJF-7F7F-7F7|L7F7LJL--7LJL--JF7JF7||J|L7FJL7|F-7LJL-77|LJJ---
|.|L|L-7JLJ|7LJLJLJLJ||F-JFJF7L7|LFJ|LJLJLJL-7|L---7FJ|||L7|||L7L7L7L7L777J-FFLJ.L7|||L7||||FJ|L-----JF-----JL7||LJFJFJ|F7|||FJF---JL||7.L|7
J-77|J|||.|F7JF-----7LJL-7|FJL-JL7L7|F-7F7F--JL----JL7L7|FJ||L7L-JFJ-L7L-7-|FF----JLJL-JLJ|LJFJF------JF--7F7FJ||F7|FJ7||LJLJL-JF7JJ.|--77LL
L-J|J.7-|7L|||L----7L-7F7|||F--7FJ7LJL7|||L--------7FJFJLJFJ||L7F7|J-FJF7|J|JL-----7F7F--7|F7L-JF7F----JF7LJLJ7|LJLJ|F7LJ.F-7F7J||.|7J-LFL.|
|L7JF-|LLJJ|L---7F7L-7|||LJLJF7|L7F---J|||F7F---7F-J|FJF-7L-J7L||LJ||L-JLJ7J|-F-7F-J|LJF-J|||F7FJLJF----JL--77FJF7F7LJL7F7|FJ|L-JL--77.|J|.|
L..LF--7|F-L---7LJL--JLJL----JLJFJL---7||LJLJF7FLJF-JL7L7|-J7F.||FLJ77J7|.-.|7|FJL--JF7L-7LJ|||L---JF------7L-JFJ||L7F7LJLJL-JF-----J-F.F77|
.77.|FJ-F7|J|JLL7F7F7F7F7F7F-7F7|F----JLJF---JL--7L--7L-JL7||.FLJ-|F-F.7.F---FJL-7F--JL--JF7|||F7F-7L----7J|F7FJJLJFJ||F7F7F-7L----7J..L||.-
7.F||L7LL7J||J||LJLJLJ||LJLJ7LJ||L-------JF---7F7L--7|F7F7L-77.|LFFJJL-J.J7|LL-7FJL7F7F---J|LJ||LJFJF---7L7|||L---7|FJ||LJ|L7|F-7F-JJFFJL777
J-FJF.LF|||-||FF7F--7LLJ-F--7F7||F----7F--JF7F||L---JLJLJL7FJ--JJF-..L--L-7-F--JL--J|LJF7|FJF-JL-7|7L--7L-JLJL----JLJFJL-7|FJ|L7LJLL-L.77LLF
.F7JLF|.L|.FF7FJLJF7L7LF7L-7LJLJ|L---7LJF--JL7LJF--7F----7||7J-|-J|-.L-|LF--L7F7F---JF7|L7L7|JF--JL7JF7L-7F7F7F---7F7L7F-J|L7L7|JL7L7F|JL-|J
7LLJFF7FF7-L||L---JL7L-JL77L---7L----JLFJF--7L-7|F7LJF7F7|LJ-.F|--JL7.L|J.LJ-||LJFF7L||L7L-JL7L---7L-JL--J|||LJF-7LJL7LJF7L7L7|L7--7LJ.FL|.L
J-|LF7L-F7FFJL--7F7.L---7|F7F--JF7F7LF7L7|F7L--J||L--JLJLJ.LJFL|.F|FJFFLF-F|.LJF--JL-JL7|F--7L7F7.L7F-----JLJF7L7L---JF-JL7|FJ|FJFF|-LJ77L-.
FJJ7|||.|L7L--7FJ|L-----J||LJF--JLJL-JL-JLJL-7F7||F------77J-J-F.L7JFJ|J|7|LFLJL7F7F--7|LJF7L7LJ|F7LJF----7F-JL7L-7F--JF--JLJ-||JL-.F|L--JL7
L..F7F7FL7L7F-JL7L------7|L-7|FF----7F7F7F---J|LJLJF---7FJ.7..FF7.LF|-FF7-FJJ.7LLJLJF7|L7FJL7L-7||L--JF7F7|L--7|F7LJF-7|FF--7-||-..|-7.|.FLF
.L|LLJLLLL7LJF-7L7F7F---JL--J|FJF7F7|||||L----JF---JF7F|L---7-|LJ77LJ7FLJJJ--7|-|F--J|L7|L-7L-7||L-7F7|LJ|L-7FJLJ|F7L7|L-JF7L7||J-F|J|-J.7JL
F---|7J|.L|F7L7L7LJ|L-----7F7|L7||||LJLJ|F7F7F7L----JL7L----JL|J|J|LL-F-J|L--|JFFJF-7L-JL--JF7LJL--J|LJF7L7FJL-7FLJ|FJ|F7FJL-JLJJ7L7.7|.-JFL
LJ.FL7.J-FLJL-JJL-7|F-7F77LJLJ.LJ||L-7F7LJLJLJ|F7F7F-7L-7LF7JFF-77JF.|.F.|-|FF7FJFJFJF----7FJL-7F--7L7FJL-J|F-7L--7LJJLJ||F7F7||7|F|7LF-JFJ7
L..JJ.JJ.F7F7F7|F7||L7||L7F--7FF7LJF7LJL-7F--7|||||L7|F-JFJ|FL|FJJF|-F-7-F---J|L7L7L-JF---J|F--JL7FJFJL--7FJL7L-7FJF7F7FLJ|LJL-7JJ-F77|-77LJ
|-|.77JJFJLJLJ|FJLJL7|LJFJL-7|FJL--JL----J|F-JLJLJ|FJLJF7L7L7||L7|F7JL7|.L--7FJ-L-JF-7L7F7FJL7F7FJL7|F7F-JL-7L-7|L-JLJL---JF---J.|F||FJ7LJ-J
J-J7JL.-L-7F-7|L---7|L-7L-77||L-----------JL-7F7F7|L7F-JL7|FJFJFJF||F-J|F7-FJ|F-7F7L7|FJ|LJ-FJ|||F-J||LJF7F7|F7||F7F---7F-7L---7.|7JL|.J7.L7
.F7|7JF|J.LJFJL7F7FJL--JF7L-J|LF7F-----------J||||L-JL--7LJL7L7|F-J|L7FJ||FL7|L7||L-JLJFJF-7L7|||L7FJL--JLJ|LJLJLJ||F-7|L7L7F7FJ7L--||7.L7.L
.L-LJ-7JLFF7L-7|||L----7|L7F7L7||L----7F------JLJL-7F7F7L--7L7|||F-JF||||L7FJ|FJ|L-----J7L7|LLJLJ.LJF7.F7F7L--7F7|LJ|FJL-JJLJLJ---J.77F7J|FJ
-J.F|7.|FFJL--JLJL-----J|FJ|L7LJL-----J|F---7F-----J|||L-7||FJ||||LFFJ|FJFJL7|L7L7F-7-F---JL7F-77F7FJ|FJLJL7F7LJL7-FJL7.F7FFF7-FJ|..--J|.LJJ
|F-7F|JF-L7F7F----7F7F--JL7||L7F-------JL--7||F-7|F7||L-7|FJL7||||F7L7|L7|7FJL7L7|L7L7L----7|L7|FJ|L7|L--7.LJL--7L-JF-JFJL--JL7|L|-FJ|FJJJ7.
|.J-.7L-7LLJ||F---J|LJ7F7LLJF7LJF7LF7F7F---JLJ|FJFJLJ|F7|||F-J|LJ|||FJL7|L7|F-JFJ|JL7|F7|F7||FJLJFJFJL7F7L---7F7L--7L--JF-----JF7J..FLJ.LLFJ
|7LF|L7JF---J|L7F7FJF--JL-7FJL--JL-JLJLJF-----JL7|F--J||||||F7|F-J||L-7|L7LJL-7L7L-7||||FJLJ|L--7L7L-7LJL----J|L--7L7F--J.F7F7L||--77..L.7J|
7-F---7-L----JFJ|||FJF7F-7|L--7F7F------JF------J|L7F-J||||LJ|||F-J|F-JL7L-7F-J-L-7LJLJ|L--7|7F7L7L--JF7F7F--7|F--JJLJF--7|||L-JL7|J|F-|.J-J
F7L--7|F7F-7F-JFJ||L7|LJJ||F7FJ|||F--7F--JF-7F7F-JFJL-7LJLJF-J|||F-JL7F7|F7|L7F--7L7F-7L---JL-JL-JF7F-J||LJF-J|L--7F7FJF-J|LJF---J7-FJ7|..|.
L|J.FJ|||L7LJF7|FJ|FJ|F--JLJLJFJLJL-7|L--7|FJ|LJF-JF7FJF--7|F-J||||F7LJ|||||FJL7FJ-LJFJF7F7F-7F7F-JLJF7LJF-JF7|F--J|||FJF7|F7L-7F7F-7--7-|77
F7--L7LJL-JF7|||L7|L-JL-7F7F-7|F----JL--7LJL-JF7L-7||L-J7FJ|L-7|||FJL7FJLJ|||LFJL7F7JL-JLJ||FJ|||F---J|F7L7FJ||L---JLJL7|LJ||F-J|||FJ7J|.LFJ
||-LJL7F--7||||L7||7F---J|||.LJL----7F7||F7F--JL--J||F7F7L7L7||LJ|L7FJL-7FJ|L7|F-J|L7JF7F7LJL-J|||F7F7LJL-J|FJ|F----7F-J|F-JLJ|FJLJL77F|7.7J
JJ7L-LLJ-FJ|||L-JLJFJF7F7|||F-------J|L7LJLJF-7F-7FJ|||||FL7L7L7FJFJL7F7||FJFJ|L7FJFJFJLJL7F7F7LJ||LJL7F---JL-JL---7||F7||F7F7FJF---JJF--FJ|
||F-|.LF-JFJLJF----JFJ||LJLJL----7F7-|FJF7F7L7|L7|L7LJ|||F7L7L7|L7L7FJ|LJ|L7L7L7||FJ7L--7FJ|LJ|F7LJF--J|F--7F------JLJ|LJLJ|||L7|F7-|-|.|JL|
F7|F77.L-7L--7L7F---JJ||F-7F7F--7LJ|FJL-JLJL-J|FJL-JF-J|||||L7|L7|FJL7|F7L-JFJFJLJL-7F--JL-JF-J||LFJF7FJL-7LJF7F7F7F77|F7F7LJL-JLJL-77|.|.F-
LF-----7J|F--J|LJ7F7F7LJL7|||L7FJF7LJF7F7F----JL7F--J7FJLJ|F7|L7|||F-JLJL--7L7L--7F-JL-----7L7F|L7|FJLJF--JF-JLJLJ||L-J|LJL---------J7LLL7J|
LL----7|7LJJF777F7|LJ|F7FJLJ|FJ|FJL--JLJ|L7F7LF7||F7F7L7F-J|||FJ|LJL--7F7F-JFJF--JL7F--7FF7L7L7|FJLJF77L---JF7F--7LJF-7L7LF7F-7F-----77J-J-|
F7.|FL||F77FJ|F7|||F-J|LJF-7LJFJL------7L7||L7|LJ||LJ|FJL7FJLJL7|F7F--J|LJF7L7L7F--JL-7|FJ|FJFJ||F7FJ|F7F---JLJF7L--JLL7L-JLJFJ|F---7|7FF-L|
L-LF--JLJL7L7LJLJLJL7FJF7L7L-7L-----7F7L7||L7||F-JL7FJL-7|L-7F-J||LJF7LL--JL7L7|L-7F-7|||FJL7L-J|||L7LJ|L----7FJL----7LL-----JFJL--7||-.|..F
|FLL7F-7F7L-JF7F---7|L7|L-JF-JF7F7F7LJL-JLJFJLJ|F-7|L-7|||F7|L7FJ|F7||F7F---JFJ|F-JL7LJ|||F7|F--J||LL-7L7F-7FJL7F----JF7F--7F-JF7F7|LJ-LF7F-
FF-FLJL||L7F7|||F--JL-J|F--JF7|||||L-7F7F7FJF--J|FJ|F7L7||||L7LJFJ||||||L---7|FJ|F7J|F-J|||LJL-7.||F77L7LJ7|L--JL7F7F7|||F-J|F-JLJLJJL7J.L-.
|LF-.F-JL7LJ||LJ|F---7FJL7F-JLJLJLJF7|||||L7|F--JL7LJL7|||||||F-JFJ||LJL77F-J|L7||L7||F7||L-7F-JFJ|||F7L--7L7F7F7LJLJLJLJL--JL----7J--J.-L.L
--|-FL7F-J|FJ|F7LJLF-JL-7LJF7F-----JLJ|LJ|FJLJF7F-JJF7||||||FJL-7|FJL--7L7L-7|7|LJFJ||||||F-JL-7L7||LJ|F--JFJ|||L-7F7F7F7F-7F7F7F7|FL.FJJJ.|
.|.L|.||F--JFJ||7F7L-7F-JFFJLJF--7F--7|F7||F7FJLJF-7|||LJ|||L7F-J||F7F7|FJJFJL7L-7L7|||LJ|L-7F-JFJ||F-JL-7.L-J|L-7LJ||LJ|L7LJ||LJLJJ7F-7.-LJ
FLJ77.LJL-7FJFJL-JL--JL-7FJF7FJF-J|F-JLJ||LJ||F7||FJ||L-7|||J||F7||||||||F7L7FJF7|FJ|||F-JF7||F7L7|||F7F7L--7FJF-JF-JL-7L7L-7LJ7JJ|--7LL7-|.
F-LJ|J.JJFJ|LL---7F7F7F-JL-JLJFL-7||F-7FJL7FJ||L7|L7|L7FJ||L7|||||||||||||L7|L7||||FJ|||F-JLJ|||FJLJ|||||F--JL7|F7L----J7L-7L7F7F-7J..LL|.LJ
7..-7LJ7FL-JF7F-7||LJLJF7F7F---7FJ|||FJ|F-JL7||FJL7LJF||JLJFJ|||||||||LJ||7||FJ|LJ|L7|||L--7F|||L7F-J|LJ||F7F7|LJ|F-------7L-J|||FJF7-.F7-||
|.FF--LJ.|JF|||FJLJ-F--JLJLJF-7LJFJLJL7|L-7FJ|||F7L7F-JL--7L7|LJLJ|||L7FJL7||L7L7FJFJLJL7F7|FJ||FJ|F7|F-JLJLJ|L--J|F------JF--JLJ|FJ|.FFJFL7
77.LJ.|7L77FJLJL--7FJF7F7F--JF|F7L-7F-JL7FJL7|||||FJL7F7F-JFJL---7|||FJL7FJ||LL7||FJF-7FJ|||L7||L7||LJ|F7F---J-F--JL-------JF7F--J|FJ-FJLF-|
|||--7LLJ|LL--7F-7|L-JLJ||F---J||F7||F7FJL7FJ||||||F-J||L7FJF-7F-J||||F7|L7||F-J||L7L7LJFJ||FJ|L7|LJF-J||L-----JF7F7F7F7F-7FJ|L7F7|L7-7-F--J
FFLJL|-L.7JFLFJ|FJL--7F7LJL7F7FJ||LJLJ|||-||7||LJ||L-7|L7|L7|FJL-7||LJ|LJFJLJL7FJ|FJFL-7|FJ||FJF||F-JF7||F7F-7F7|LJLJ||LJFJL7L7LJLJFJ.J7L7J7
L.|7.|.|.||FLL-JL7F-7LJL---J||L7||F---JL7FJL7||7FJL-7||FJ|F||L-7FJLJF-JF7L--77LJ-||F7F-J|L7|||F7LJ|F-JLJ|||L7|||L--7JLJF7L--J-L--7FJ77|7.JF7
|7.FJ-F|FJ|F-F-7FJ|FJF7F7F7FJL7|LJL7F-7FJL7FJ|L7|F7FJ||L7L7LJF-JL--7L-7||F-7|F---JLJ||F7L7LJ|LJL-7LJF---J||FJLJ|F--JF--JL-------7||F7-F77FJ7
J-F|JL||-L7--L7LJFJL7|||LJ||.FJ|F--J|L||F-J|JL-J||||FJ|FL7L-7L-7F-7|F-J||L7|||F7F7F7|||L7L7FL7F-7|F-JF7F7||L7LFJL---JF7F7F7F-7F-J|LJL7JJ|J-7
||LL7-JLLLL7|LL-7L-7||||F-JL7L-JL--7L7||L-7L---7||LJL7L7FJF7|F-JL7||L7FJ|FJLJ||||LJ||LJ7L7L-7LJ|LJL7FJ||LJ|FJFJF7F7F-JLJLJ||FJL-7L---J7-|JF|
FJ7-|-|7|LLFL-.FJF7|LJ||L-7FJF-----JFJ|L7FJF-7FJ|L--7|FJ|FJ||L-7FJLJ-LJ7||.F-J||L7FJL--7FJF7L--7F--J|FJ|F-JL7L7|LJ|L-----7||L7F7L7LF|-7-.7JL
||F7JF-7-J.|-.F|FJ|L7J||F-JL7|F7F-7FJJL7|L7L7LJFL7F7||L7||7||F-JL-7F7F--JL7L--J|FJL7F7FJL7||F-7||F-7||FJ|F7FJFJL-7L7F-7F7|LJFJ||FJF-77JLF||7
LJJ7.LJ||F|.|.F|L7|FJ7LJL-7FJLJ||FJL-7||L7L7L7LF-J||||FJLJFJ|L-7F-J|LJF7F7L---7LJF-J||||FJ|LJFLJ||-LJLJJ||LJFJF7FJ.|L7||LJF7L7||L77LL-F-|J-F
.|LJFJF7FL7.|F-JFJLJ--F---JL-7.LJL7F-JFJFJ-L7L7L--JLJ|L--7L7|F7||F7L--J|||F---JF-JF7||L7L7L-7F7FJL7.F---JL-7|FJ|L-7L7||L7FJL7||L7L7..|LF.||L
F7F---|J-7.LLL--J|LL7.L-7F--7|F---JL-7L7L7F7L7L7F--7FL7F7L7LJ|LJ||L7F--J||L-7F7L-7|||L7L7|F7||LJF7L7L--7F7FJ||FJF7L7||L7||F-J|L7L7L-77.|.7-|
|-|-|F7|F-7.|-|JF77LL7LL||F-J|L7F7F7FJFJFJ|L7|FJL-7|F7||L7|F-JF7LJFJL7F7||F7LJL-7LJLJFJFJ||||L7FJ|FJF--J||L7LJL7||FJ||||LJL-7|FJF|F7|--L7F7|
|7|-LJ.-J.F-|.J.JL-.|LF.LJL-7|FJ||||L7L7|LL7LJL-7FJLJ|||FLJ|F-J|F7|LFJ|||LJ|F7F-JFF--JFJ|LJLJFJ|F|L7L-7FJ|FJ-F-J|||FLJFJF-7FJ|L-7LJLJJ|F-L7-
F7L..F||7.|FJ-FL--|---J7L|J.LJL-J||L7|FJ|F-JF7F7LJF7FJLJF7FJ|F-J||L7|FJ|L-7||||7F7L--7|F-----JFJFJFJF7|L7|L-7|F-J|L-7FJFJFJL7L-7||LJ.FFF7||.
|J.L7L7J7FJL7J|J-LLFJ.LL-77..|.F-JL7||L7|L7FJ|||F-JLJF7L|LJFJL7FJL-JLJ|L7FJ|||L-JL-7FJ||F-7F-7L7L7L-J|L-J|F7|||F-JF-JL7L7L--JJ|LJJJ7FL-J7|7L
L-J--7|LF7L---FF.F||J.JF-|.J77FJF7FJ||FJL7|L7||||F---J|FJF7L7FJL----7F7L|L7||L7F-7FJL7|||FJL7L7|L|F-7L--7LJLJLJ|F7L-7FJFJ.|-|-L.LLJFFJ|.F7-.
LF.-7|JFL---|FLJFF|JJF.F.|FLF-JFJ||FJ|L-7|L7||||LJF-7FJL7|L7|L-7F-7FJ||7|FJ||FJ|J|L-7LJ||L7FJ-||FJ||L7F7|F7F7F-J||F-J|FJLF-7-7-F|.FF|-7FL|J7
F|F|L.-JJL7|F77L-JFJ.LJJFL--L7FJFJ|L-JF-J|FJ|||L7FJFJ|F-JL7|L7FJL7|L-J|FJL7||L7L7|F-JF7|L7|L-7||L7L-7|||LJLJ||F7||L-7|L7-J||FJ--.FF7|FF7-L.F
F-7J-JJF7.-|.FJFL7JFL|J-|J|FL||7L7L7JFL-7||FJ|L7|||L-J|F-7||FJ|F7||F--JL7FJLJLL7||L--J||FJL7FJLJFJF7|LJ|F-7FJ|||||F7||FJJ.|7L|J.FL--F-7--LJJ
JJ|7F.F77-LJF-7L7LJ|.77.|.LL-LJF-JFJ7|.FJ|||FL7||L-7F-JL7LJ|L7|||||L--77|||LJ|.LJ|F--7|||FFJ|F7-L7|LJF7||L||FJ|LJ||||LJJF7.FJ|7-F-|.LJL|.|L7
|-77||LL7FLLL.F|FJ-|FJ.7F7.F|F-JF-J.F.FL-J|L-7LJ|F-J|F7FJ-FJFJLJLJL7F7L7||FJF7-LFJL7-LJ|L7L7||L--JL--J||L7LJL7L7|LJLJJL-JJF77|77LF77J|--7-|7
7F7-|-JF7J|.L|--JJ.FJ.7LF7-|FL--JJLJJ|.LF-JF-J7J||JL|||L-7L-J7L77LLLJ|FJ|L7JF77.L-7L-7LL7|-||L7F7F7F7FJL7L--7|FJ7|J|F||.|JL|L|-7.J.L7-FJF-J7
F-L-LL7-LJ|7.|J.L|F|FF7FLL7.JFLJ.F7|F7.L|F7|J||-|L7-LJ|F-JJ.F-JF-.LLF|L7L7L-J|FFF-JF7|JFLJ.LJL||LJ||LJJFJF7FJLJJF|7.JLF-JJLL-|L-J|LFL.LLJJ.7
|JL77FL7J.|-.|L7-JJLJJJL7.--||7.FLFFJ|7.LJLJ-LLFJFJ-L.LJJ.|F|L7|.L77LL-J7L---J77|F-JLJFFJFFL|-LJJLLJJ|||FJ||F7.||LLF--7F|JF|-F7J.L-FJ.L-7|-|
--LL-|7LFJLL-|-|L|.FL7LLF7|F7LJ-7.LL-LF.FJ||.L|L-JJJL-L7LF7J--|-77L7JJ|LF|-JF|LFJ|LJ7FLF7-|J|FLLJ-|J|--LJFJLJ|7LJ-||.FL--FJ-7|LFF.-.F|J.J7-|
L-|LFLJ7|.FLF-7|7JL|7F-F7|FL7FJ..7-LL-J-|FFF--J|J|JFF|-7.FJ|..|-F|J|LFJFF7FL|LFL-J77|7.L--|7.L777.LJL-JLLL---J-77F-J-JF7-|.JF7.FJ-J7|L--L--L
|--.FJFJ|L|-7F|..7.LF-..|7FJL|L-LL7F7|L|.|.|L7JF7J7-F---.--77.|-LJ-J||--JJ|7|F7J7|F|FJ7.J-L-LFL-J-|.|JFJJ7LL|.|L-F|J..F|7F7F777.7-7F-J77..FJ
L7L-|JFLF7|7L|..L7-FJLF-JLJ7|L.||.7L-F.LF7-7.|FJ|.||J|7J7..F-7-FJ||.J7FLJ7LLJ7J|FFJ|L----7JF7LJLJLF7|FJ|F7.L-7-J-JJ.|-LJFF-|J.---7L7.|FJ-FL7
|L7J.|.L|J|F.L-.JJFJ|F|.L|.L-FJ||-|7F--.FF-7-|7-F7F--FF-J7.JL|-J7.L-LLJL--7J|JF|7|L|LFJ|.--J-JFJ.F--|L--777.LJ|7F||F7.F7||7|.F7..|.L-LJJ.L.|
FJ-J-77..J7JF-JJJJLFJ|7--7-|.LJ|J.JJ-7-FFJ|.L||F7J|.|L|7|L77||LL-7|7F|||.F|7-----FJ|.||L7JFJ77|.-|7||FF|L7F7.-J-F-|L77FJ-|-|7L-JF77JLL7J|..|
LJ.LF-FJ-L7-7JJL--F7-LFJ-L.7.L-J-|-L--.F7LJ---JJJ-L-FLLJ-.L|J|.L.L7-L7-F-LJLJ-|-|L-|--JJ|LJ-L-JFJL-JJL7-|LL7JJJL-LLFLFLJLJJLL-J-JL-.LLF-J--J
""".trimIndent()
}