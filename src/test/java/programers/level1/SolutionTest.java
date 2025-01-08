package programers.level1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    public void testSolution() {
        Solution solution = new Solution();

        String video_len = "15:00";
        String pos = "00:02";
        String op_start = "03:00";
        String op_end = "04:00";
        String[] commands = {"prev"};

        String result = solution.solution(video_len, pos, op_start, op_end, commands);
        System.out.println("result = " + result);
    }
}