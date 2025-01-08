package programers.level1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Solution {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        // "HH:mm:ss" 형식 포맷터 설정
        DateTimeFormatter formatterWithHours = DateTimeFormatter.ofPattern("HH:mm:ss");

        // "mm:ss" 형식 반환용 포맷터 설정
        DateTimeFormatter formatterMinutesSeconds = DateTimeFormatter.ofPattern("mm:ss");

        // "mm:ss" -> "00:mm:ss"로 변환 후 LocalTime으로 파싱
        LocalTime t_pos = LocalTime.parse("00:" + pos, formatterWithHours);
        LocalTime t_op_start = LocalTime.parse("00:" + op_start, formatterWithHours);
        LocalTime t_op_end = LocalTime.parse("00:" + op_end, formatterWithHours);
        LocalTime t_video_end = LocalTime.parse("00:" + video_len, formatterWithHours); // 동영상 끝 시간

        // 초기 재생 위치 설정
        LocalTime t_answer = t_pos;

        // 오프닝 구간에 있을 경우 오프닝 끝으로 이동
        if (t_op_start.compareTo(t_answer) <= 0 && t_answer.compareTo(t_op_end) <= 0) {
            t_answer = t_op_end;
        }

        // commands 배열 순차적으로 처리
        for (String command : commands) {
            if (command.equals("next")) {
                LocalTime newTime = t_answer.plusSeconds(10);
                t_answer = newTime.isAfter(t_video_end) ? t_video_end : newTime;
            } else if (command.equals("prev")) {
                LocalTime newTime = t_answer.minusSeconds(10);
                // 음수 초로 돌아가는 경우를 명시적으로 처리
                t_answer = t_answer.toSecondOfDay() <= 10 ? LocalTime.of(0, 0, 0) : newTime;
            }

            // 오프닝 구간에 있을 경우 오프닝 끝으로 이동
            if (t_op_start.compareTo(t_answer) <= 0 && t_answer.compareTo(t_op_end) <= 0) {
                t_answer = t_op_end;
            }
        }

        // 결과를 "mm:ss" 형식으로 포맷하여 반환
        return t_answer.format(formatterMinutesSeconds);
    }
}
