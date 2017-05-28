import spock.lang.Specification

/**
 *
 */
class CheapestPathSpec extends Specification {

    def "check min cost map"() {

        setup:
        List<List<Integer>> grid = new ArrayList<>();
        grid = [[0, 7, 3, 4, 5],
                [2, 3, 4, 5, 6],
                [5, 2, 4, 5, 9],
                [2, 3, 7, 8, 4],
                [8, 2, 6, 2, 3],
        ]

        expect:
        Cell cell = CheapestPath.getCellLowestCost(grid, row, column)
        cell.getRow() == lowCostRow
        cell.getColumn() == lowCostColumn

        where:
        row | column || lowCostRow | lowCostColumn
        0   | 0      || 0          | 0
        1   | 0      || 0          | 0
        0   | 1      || 0          | 0
        3   | 2      || 3          | 1
        4   | 4      || 4          | 3

    }


    def "get cheapest path with set path"() {

        setup:
        List<List<Integer>> grid = new ArrayList<>();
        grid = [[0, 7, 8, 9, 10],
                [1, 2, 3, 11, 12],
                [16, 15, 4, 5, 13],
                [17, 18, 19, 6, 7],
                [21, 22, 23, 24, 0],
        ]

        when:
        List<Cell> result = CheapestPath.minimumCostPath(grid)

        then:
        result[0].getRow() == 0
        result[0].getColumn() == 0
        result[1].getRow() == 1
        result[1].getColumn() == 0
        result[2].getRow() == 1
        result[2].getColumn() == 1
        result[3].getRow() == 1
        result[3].getColumn() == 2
        result[4].getRow() == 2
        result[4].getColumn() == 2
        result[5].getRow() == 2
        result[5].getColumn() == 3
        result[6].getRow() == 3
        result[6].getColumn() == 3
        result[7].getRow() == 3
        result[7].getColumn() == 4

    }


    def "get cheapest path "() {

        setup:
        List<List<Integer>> grid = new ArrayList<>();
        grid = [[0, 4, 2, 8, 3],
                [1, 3, 9, 6, 7],
                [8, 2, 4, 5, 2],
                [5, 6, 5, 8, 2],
                [9, 3, 2, 3, 0],
        ]

        when:
        List<Cell> result = CheapestPath.minimumCostPath(grid)

        then:
        result[0].getRow() == 0
        result[0].getColumn() == 0
        result[1].getRow() == 1
        result[1].getColumn() == 0
        result[2].getRow() == 1
        result[2].getColumn() == 1
        result[3].getRow() == 2
        result[3].getColumn() == 1
        result[4].getRow() == 2
        result[4].getColumn() == 2
        result[5].getRow() == 2
        result[5].getColumn() == 3
        result[6].getRow() == 2
        result[6].getColumn() == 4
        result[7].getRow() == 3
        result[7].getColumn() == 4

    }

}