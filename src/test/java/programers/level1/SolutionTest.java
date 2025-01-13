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
}