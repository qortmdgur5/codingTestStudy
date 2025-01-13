package programers.level1;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    // 프로그래머스 Level 1 붕대감기 공격 문제
    public static class Solution4 {
        public int solution(int[] bandage, int health, int[][] attacks) {
            int maxHealth = health;
            int skillTime = bandage[0];     // 추가 체력 가능 시간
            int healPerSecond = bandage[1];
            int extraHeal = bandage[2];

            int currentTime = 0;  // 현재 시간

            for (int[] attack : attacks) {
                int attackTime = attack[0];
                int damage = attack[1];

                // 기술 사용 가능 시간 계산
                int availableTime = attackTime - currentTime - 1;
                if (availableTime > 0) {
                    // 붕대 기술 사용
                    health = useBandage(maxHealth, health, availableTime, skillTime, healPerSecond, extraHeal);
                }

                // 몬스터 공격 처리
                health -= damage;
                if (health <= 0) {
                    return -1;
                }

                // 현재 시간을 공격 시간으로 업데이트
                currentTime = attackTime;
            }

            return health;
        }

        private int useBandage(int maxHealth, int health, int availableTime, int skillTime, int healPerSecond, int extraHeal) {
            // 초당 회복량 적용
            health += healPerSecond * availableTime;

            // 기술 완전 시전 성공 시 추가 회복
            if (availableTime >= skillTime) {
                int extraHealCount = availableTime/skillTime;   // 추가체력 회복 횟수
                health += extraHeal * extraHealCount;
            }

            // 최대 체력 초과 방지
            return Math.min(health, maxHealth);
        }
    }

    // 프로그래머스 level 1 가장많이 받은 선물 문제 - 카카오톡
    public static class Solution5 {
        public int solution(String[] friends, String[] gifts) {
            // 두 사람이 선물을 주고받았으면 더 많은 선물을 준 사람이 다음달에 선물을 1개 받음
            // 선물지수 = 이번달의 준 선물 - 받은 선물  즉, 준 선물이 더 많을수록 높음
            // 서로 주고받은게 같으면 = 선물지수가 낮은 사람이 선물지수가 높은 사람에게 선물을 줌
            // 선물지수도 같고 서로 선물한적도 없으면 다음달에 선물을 주고받지 않음
            // 다음달에 선물을 가장 많이 받을 친구의 받을 선물수를 알고 싶음
            // gifts 공백으로 구분하며 A 선물을 준 친구   B 선물을 받은 친구
            // 개인 선물지수 계산 Map 에 담고
            // 주고받았으면 더 준놈이 하나 더 받고
            // 없거나 같으면 선물지수가 높은놈이 하나 더 받고
            // 해당 사용자가 다음달에 받을 선물갯수만 계산해서 리턴

            // 주고 받은 횟수 기록 Map 선언
            Map<String, Integer> giveGiftCount = new HashMap<>();
            Map<String, Integer> receiveGiftCount = new HashMap<>();

            // 선물 주고받은 횟수 Map 초기화
            for(String friend : friends){
                giveGiftCount.put(friend, 0);
                receiveGiftCount.put(friend, 0);
            }

            // 서로 선물 주고받은 관계 Map
            Map<String, Map<String, Integer>> giftRelation = new HashMap<>();
            for(String friend : friends){
                giftRelation.put(friend, new HashMap<>());
            }

            // 선물 주고받은 횟수 기록
            for(String gift : gifts){
                String[] parts = gift.split(" ");
                String giver = parts[0];
                String receiver = parts[1];

                // 선물지수 계산을 위한 전체 몇개 줬고 받았는지
                giveGiftCount.put(giver, giveGiftCount.get(giver) + 1);
                receiveGiftCount.put(receiver, receiveGiftCount.get(receiver) + 1);

                // 선물을 준 사람이 누구에게 몇번 줬는지 Map<giver, Map<receiver, count>>
                giftRelation.get(giver).put(receiver, giftRelation.get(giver).getOrDefault(receiver, 0) +1);
            }

            // 선물 지수 계산
            Map<String, Integer> giftCount = new HashMap<>();
            for(String friend : friends){
                int giftScore = giveGiftCount.get(friend) - receiveGiftCount.get(friend);
                giftCount.put(friend, giftScore);
            }

            // 다음달 받을 선물 계산
            Map<String, Integer> nextMonthGift = new HashMap<>();
            for(String giver : friends){
                // 모든 친구들에 대해 반복
                nextMonthGift.put(giver, 0);   // 다음달 받을 선물 Map 초기화
                for(String receiver : friends){
                    // 상대방과 선물지수 및 선물관계 파악
                    if(!giver.equals(receiver)){
                        // 본인이 아닌 경우만 파악
                        // 상대방과 주고받은 관계가 있으면, 더 많이 줬을때 하나를 받고
                        // 상대방과 내꺼를 - 했을때 0 이면 선물지수로 판별
                        int giveCountToReceiver = giftRelation.get(giver).getOrDefault(receiver, 0);
                        int giveCountFromReceiver = giftRelation.get(receiver).getOrDefault(giver, 0);
                        if((giveCountToReceiver - giveCountFromReceiver) != 0){
                            if((giveCountToReceiver - giveCountFromReceiver) > 0){
                                // 본인이 더 많이 줬으면 반대조건은 하지않음. 모든 친구들에 초기반복문을 돌리면 상대방꺼는 그떄 +1 될테니까 여기서 해주면 중복 더하기임
                                nextMonthGift.put(giver, nextMonthGift.get(giver) + 1);
                            }
                        }else{
                            // 서로 선물한 관계가 없거나 같은 갯수를 선물했을 떄 선물지수로 판별
                            if(giftCount.get(giver) > giftCount.get(receiver)){
                                // 본인이 선물지수가 더 높으면
                                nextMonthGift.put(giver, nextMonthGift.get(giver) + 1);
                            }
                        }
                    }
                }
            }

            // 가장 많은 선물을 받은 친구의 선물 수 리턴
            int maxReceive = 0;
            for(int receives : nextMonthGift.values()){
                maxReceive = Math.max(maxReceive, receives);
            }

            return maxReceive;
        }
    }

    // 프로그래머스 level 1 이웃한 칸 문제
    public static class Solution6 {
        public int solution(String[][] board, int h, int w) {
            // 각 칸마다 색이 칠해진 2차원 격자 보드판이 있음.
            // 칸을 골랐을 때 같은 색깔로 칠해진 칸의 이웃한 칸의 갯수를 구함
            // board[h][w] = String 으로 색깔 값이 들어가 있음
            // board 는 정사각형

            int boardLength = board.length; // 보드칸 길이

            String color = board[h][w];     // 해당 보드칸 색깔

            int colorCount = 0;             // 같은 색깔 보드칸 수

            int[] rowList = {-1, 1, 0, 0};
            int[] colList = {0, 0, -1, 1};

            // 좌 우 위 아래 를 체크하려면 [h-1][w], [h+1][w], [h][w-1], [h][w+1] 체크
            for(int i = 0; i < 4; i++){
                int newRow = h + rowList[i];
                int newCol = w + colList[i];

                if(newRow >= 0 && newRow < boardLength && newCol >= 0 && newCol < boardLength){
                    if(board[newRow][newCol].equals(color)){
                        colorCount++;
                    }
                }
            }

            return colorCount;
        }
    }

    // 프로그래머스 level 1 데이터 분석 문제
    public class Solution7{
        public int[][] solution(int[][] data, String ext, int val_ext, String sort_by) {
            // data = ["코드 번호(code)", "제조일(date)", "최대 수량(maximum)", "현재 수량(remain)"]
            // ext < val_ext 조건에서 sort_by 에 해당하는 조건값으로 오름차순 정렬
            // ext = "code", "date", "maximum", "remain" 중 하나

            int dataCondition = 0;
            switch (ext) {
                case "code":
                    dataCondition = 0;
                    break;
                case "date":
                    dataCondition = 1;
                    break;
                case "maximum":
                    dataCondition = 2;
                    break;
                default:
                    dataCondition = 3;
                    break;
            }

            int sortCondition;
            switch (sort_by) {
                case "code":
                    sortCondition = 0;
                    break;
                case "date":
                    sortCondition = 1;
                    break;
                case "maximum":
                    sortCondition = 2;
                    break;
                default:
                    sortCondition = 3;
                    break;
            }

            List<int[]> filterDataByExt = new ArrayList<>();

            for(int[] datas : data){
                if(datas[dataCondition] < val_ext){
                    filterDataByExt.add(datas);
                }
            }

            // filterDataByExt 리스트를 sortCondition 번째 요소를 기준으로 오름차순 정렬
            Collections.sort(filterDataByExt, Comparator.comparingInt(a -> a[sortCondition]));

            // List<int[]>를 int[][] 배열로 변환하여 반환
            return filterDataByExt.toArray(new int[filterDataByExt.size()][]);
        }
    }

    // 프로그래머스 level 1 달리기 경주 문제
    public static class Solution8{
        public String[] solution(String[] players, String[] callings) {
            // players 는 등수 순으로 플레이어 선수
            // callings 는 해설진이 부르는 선수 순서

            Map<String, Integer> playerMap = new HashMap<>();
            for(int i = 0; i < players.length; i++){
                playerMap.put(players[i], i);
            }

            for(String call : callings){
                // 불린 선수 index
                int callPlayerIdx = playerMap.get(call);
                // 바로 앞 선수
                String frontPlayer = players[callPlayerIdx - 1];

                // 등수 체인지
                players[callPlayerIdx] = frontPlayer;
                players[callPlayerIdx - 1] = call;

                // 인덱스도 초기화
                playerMap.put(call, callPlayerIdx - 1);
                playerMap.put(frontPlayer, callPlayerIdx);

            }
            return players;
        }
    }

    // 프로그래머스 level 1 추억 점수 문제
    public class Solution9{
        public int[] solution(String[] name, int[] yearning, String[][] photo) {
            // name 사람의 이름 배열
            // yearning 각 name index 의 사람 별 그리움 점수
            // photo 는 사진에 찍힌 인물의 이름을 담은 함수

            Map<String, Integer> yearningMap = new HashMap<>();

            // 각 사람 별 그리움 점수 Map
            for(int i = 0; i < name.length; i++){
                yearningMap.put(name[i], yearning[i]);
            }

            List<Integer> answer = new ArrayList<>();

            for(String[] row : photo){
                // 각 사진들 마다 반복문을 돌리고
                int photoScore = 0;
                for (String element : row) {
                    // 등장인물이 그리움 점수에 있는 경우 점수 합산
                    photoScore += yearningMap.getOrDefault(element, 0);
                }
                answer.add(photoScore);
            }
            return answer.stream().mapToInt(Integer::intValue).toArray();
        }
    }

    // 프로그래머스 level 1 공원 산책 문제
    public static class Solution10{
        public int[] solution(String[] park, String[] routes) {
            // park 는 직사각형 공원
            // routes = 방향 공백 거리 로 구분된다
            // 마지막 도착 좌표를 구하세요

            int width = park[0].length();   // 공원 가로 길이
            int height = park.length;       // 공원 세로 길이

            // 2차원 배열로 먼저 바꿔야겠다
            String[][] coordinate = new String[height][width];
            int[] start = new int[2];
            for(int i = 0; i < height; i++){
                // park 각 row 마다 반복
                for(int j = 0; j < width; j++){
                    // 각 row element 마다 반복
                    // 좌표 별 element 담았음.
                    coordinate[i][j] = String.valueOf(park[i].charAt(j));

                    if(String.valueOf(park[i].charAt(j)).equals("S")){
                        // 스타팅 포인트 지점이라면
                        start[0] = i;
                        start[1] = j;
                    }
                }
            }

            for(String route : routes){
                // 모든 명령어에 대해 반복
                String[] part = route.split(" ");
                String direction = part[0];
                int distance = Integer.parseInt(part[1]);

                start = move(coordinate, start, distance, direction, width, height);
            }

            return start;
        }

        private int[] move(String[][] coordinate, int[] cur, int distance, String direction, int width, int height){
            // 현재 좌표
            int x = cur[0];  // 현재 x좌표
            int y = cur[1];  // 현재 y좌표

            // 이동 가능 여부를 체크
            boolean isMove = true;

            // 이동할 방향에 따라 처리
            for (int i = 0; i < distance; i++) {
                if (direction.equals("E")) {  // 동쪽
                    if (y + 1 >= width || coordinate[x][y + 1].equals("X")) {
                        isMove = false;  // 장애물이 있거나 공원 범위를 벗어났다면
                        break;
                    }
                    y += 1;  // y좌표 이동
                } else if (direction.equals("S")) {  // 남쪽
                    if (x + 1 >= height || coordinate[x + 1][y].equals("X")) {
                        isMove = false;
                        break;
                    }
                    x += 1;  // x좌표 이동
                } else if (direction.equals("W")) {  // 서쪽
                    if (y - 1 < 0 || coordinate[x][y - 1].equals("X")) {
                        isMove = false;
                        break;
                    }
                    y -= 1;  // y좌표 이동
                } else if (direction.equals("N")) {  // 북쪽
                    if (x - 1 < 0 || coordinate[x - 1][y].equals("X")) {
                        isMove = false;
                        break;
                    }
                    x -= 1;  // x좌표 이동
                }
            }

            // 이동이 가능하면 이동된 좌표를 반환
            if (isMove) {
                cur[0] = x;
                cur[1] = y;
            }

            return cur;
        }
    }
}
