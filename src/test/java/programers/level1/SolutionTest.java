package programers.level1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    Solution solution = new Solution();

    // 프로그래머스 Level 1 동영상 편집기 문제 테스트
    @Test
    public void testSolution() {


        String video_len = "15:00";
        String pos = "00:02";
        String op_start = "03:00";
        String op_end = "04:00";
        String[] commands = {"prev"};

        String result = solution.solution(video_len, pos, op_start, op_end, commands);
        System.out.println("result = " + result);
    }

    @Test
    public void testSolution4() {
        Solution.Solution4 solution4 = new Solution.Solution4();

        // 테스트 케이스 1
        assertEquals(5, solution4.solution(new int[]{5, 1, 5}, 30, new int[][]{
                {2, 10}, {9, 15}, {10, 5}, {11, 5}
        }));

        // 테스트 케이스 2
        assertEquals(-1, solution4.solution(new int[]{3, 2, 7}, 20, new int[][]{
                {1, 15}, {5, 16}, {8, 6}
        }));

        // 테스트 케이스 3
        assertEquals(-1, solution4.solution(new int[]{4, 2, 7}, 20, new int[][]{
                {1, 15}, {5, 16}, {8, 6}
        }));

        // 테스트 케이스 4
        assertEquals(3, solution4.solution(new int[]{1, 1, 1}, 5, new int[][]{
                {1, 2}, {3, 2}
        }));
    }

    @Test
    void testSolution5() {
        Solution.Solution5 solution5 = new Solution.Solution5();

        // 테스트 케이스 1
        assertEquals(2, solution5.solution(
                new String[]{"muzi", "ryan", "frodo", "neo"},
                new String[]{"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi",
                        "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"}
        ));

        // 테스트 케이스 2
        assertEquals(4, solution5.solution(
                new String[]{"joy", "brad", "alessandro", "conan", "david"},
                new String[]{"alessandro brad", "alessandro joy", "alessandro conan",
                        "david alessandro", "alessandro david"}
        ));

        // 테스트 케이스 3
        assertEquals(0, solution5.solution(
                new String[]{"a", "b", "c"},
                new String[]{"a b", "b a", "c a", "a c", "a c", "c a"}
        ));
    }

    @Test
    void testSolution8() {
        Solution.Solution8 solution8 = new Solution.Solution8();

        String[] players = {"mumu", "soe", "poe", "kai", "mine"};
        String[] callings = {"kai", "kai", "mine", "mine"};
        String[] expected = {"mumu", "kai", "mine", "soe", "poe"};

        // solution 메서드 호출
        String[] result = solution8.solution(players, callings);

        // 결과가 예상한 결과와 일치하는지 확인
        assertArrayEquals(expected, result);
    }

    @Test
    void testSolution10() {
        Solution.Solution10 solution10 = new Solution.Solution10();

        // 첫 번째 테스트 케이스
        String[] park1 = {"SOO", "OOO", "OOO"};
        String[] routes1 = {"E 2", "S 2", "W 1"};
        int[] result1 = solution10.solution(park1, routes1);
        System.out.println("Test Case 1: " + (result1[0] == 2 && result1[1] == 1 ? "Passed" : "Failed"));

        // 두 번째 테스트 케이스
        String[] park2 = {"SOO", "OXX", "OOO"};
        String[] routes2 = {"E 2", "S 2", "W 1"};
        int[] result2 = solution10.solution(park2, routes2);
        System.out.println("Test Case 2: " + (result2[0] == 0 && result2[1] == 1 ? "Passed" : "Failed"));

        // 세 번째 테스트 케이스
        String[] park3 = {"OSO", "OOO", "OXO", "OOO"};
        String[] routes3 = {"E 2", "S 3", "W 1"};
        int[] result3 = solution10.solution(park3, routes3);
        System.out.println("Test Case 3: " + (result3[0] == 0 && result3[1] == 0 ? "Passed" : "Failed"));
    }

    @Test
    void testSolution11() {
        Solution.Solution11 solution11 = new Solution.Solution11();

        // 테스트 케이스
        String[][] testCases = {
                {".#...", "..#..", "...#."},
                {"..........", ".....#....", "......##..", "...##.....", "....#....."},
                {".##...##.", "#..#.#..#", "#...#...#", ".#.....#.", "..#...#..", "...#.#...", "....#...."},
                {"..", "#."}
        };
        int[][] expectedResults = {
                {0, 1, 3, 4},
                {1, 3, 5, 8},
                {0, 0, 7, 9},
                {1, 0, 2, 1}
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] result = solution11.solution(testCases[i]);
            System.out.println("Test Case " + (i + 1) + " Result: " + java.util.Arrays.toString(result) +
                    " | Expected: " + java.util.Arrays.toString(expectedResults[i]));
        }
    }
}