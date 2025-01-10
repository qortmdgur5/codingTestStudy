package programers.level1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Solution {
    // 프로그래머스 Level 1 동영상 편집기 문제
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

    // 프로그래머스 Level 1 지갑에 지폐 넣기 문제
    public int solution2(int[] wallet, int[] bill) {
        int answer = 0;

        // 지갑 가로 세로 크기
        int walletWidth = wallet[0];
        int walletHeight = wallet[1];

        // 지폐 가로 세로 크기
        int billWidth = bill[0];
        int billHeight = bill[1];

        while(true){
            // 지폐가 지갑에 들어가면 바로 종료
            if((walletWidth >= billWidth && walletHeight >= billHeight) || (walletWidth >= billHeight && walletHeight >= billWidth)){
                break;
            }

            // 지폐가 지갑에 안들어가면 answer 값 증가 및 큰 쪽 반으로 접기, 소수점 버림
            answer++;
            if(billWidth > billHeight){
                billWidth /= 2;
            }else{
                billHeight /= 2;
            }
        }
        return answer;
    }

    // 프로그래머스 Level 1 돗자리 펴기 문제
    public class Solution3 {
        public int solution(int[] mats, String[][] park) {
            Arrays.sort(mats); // 돗자리 크기 정렬 (작은 순서)
            int parkHeight = park.length;
            int parkWidth = park[0].length;

            // 큰 돗자리부터 가능한지 검사
            for (int matSize = mats.length - 1; matSize >= 0; matSize--) {
                int size = mats[matSize];
                if (canPlaceMat(size, park, parkHeight, parkWidth)) {
                    return size; // 가장 큰 돗자리 크기 반환
                }
            }
            return -1; // 아무 돗자리도 놓을 수 없을 경우
        }

        private boolean canPlaceMat(int size, String[][] park, int height, int width) {
            // 공원 내 모든 시작 좌표 (i, j) 탐색
            for (int i = 0; i <= height - size; i++) {
                for (int j = 0; j <= width - size; j++) {
                    if (isEmptyArea(i, j, size, park)) {
                        return true; // 빈 공간 발견
                    }
                }
            }
            return false; // 빈 공간 없음
        }

        private boolean isEmptyArea(int row, int col, int size, String[][] park) {
            // 해당 크기 영역이 전부 빈자리인지 검사
            for (int i = row; i < row + size; i++) {
                for (int j = col; j < col + size; j++) {
                    if (!park[i][j].equals("-1")) {
                        return false; // 빈자리가 아닌 곳 발견
                    }
                }
            }
            return true;
        }
    }


}
