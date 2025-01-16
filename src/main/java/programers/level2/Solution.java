package programers.level2;

public class Solution {

    // 프로그래머스 level 2 퍼즐 게임 챌린지 문제
    public class Solution1{

        // 해당 코드로 문제를 풀면 문제가 많아 질수록 시간을 초과하였을 때
        // 다시 처음의 문제부터 탐색을 해야 하므로 복잡도가 O(N × M) 까지 증가 크면 클수록 너무 많아짐 적으면 상관없는데
        // 그래서 이분탐색으로 진행해야함.
        public int solution(int[] diffs, int[] times, long limit) {
            // diff = 현재 퍼즐 난이도
            // time_cur = 현재 퍼즐의 소요 시간
            // time_prev = 이전 퍼즐 소요 시간
            // level = 나의 숙련도
            // diff <= level 이면 tume_cur 만큼의 시간 소요
            // diff > level 이면 diff - level 번 만큼 틀림
            // 퍼즐을 들릴때마다 time_cur 시간 사용 and time_prev 시간사용
            // time_cur(diff - level + 1) 시간사용하여 문제 해결이란 뜻
            // 추가로 time_prev 시간을 사용해서 이전 퍼즐 푸는시간 추가

            int level = 1; // 초기 숙련도 1으로 설정

            boolean success = false;
            while(!success){

                long timeLimit = 0;  // 전체 소요시간 설정

                for(int i = 0; i < diffs.length; i++){
                    int diff = diffs[i];    // 해당 문제의 난이도
                    int time = times[i];    // 해당 문제의 소요시간

                    if(diff > level){
                        // 문제 난이도가 나의 숙련도 보다 높으면
                        if(i > 0){
                            // 이전 문제가 존재하면, 첫번째 퍼즐이면 이전문제가 없으니까
                            timeLimit = timeLimit + time*(diff - level + 1) + times[i-1]*(diff - level);
                        }else{
                            // 이전 문제가 없는 첫번째 퍼즐이면
                            timeLimit = timeLimit + time*(diff - level + 1);
                        }
                    }else{
                        // 문제 난이도가 나의 숙련도와 같거나 낮으면
                        // 바로 해결이니까 해당 문제 시간만 +
                        timeLimit += time;
                    }

                    if(timeLimit > limit){
                        // 시간 초과하면
                        level++;
                        break;
                    }
                }
                if(timeLimit <= limit){
                    // 시간 초과안했으면
                    success = true;
                }
            }

            return level;
        }

        public int advancedSolution(int[] diffs, int[] times, long limit) {
            int left = 1;
            int right = 1000000; // level 최대값 (적절히 설정)
            int answer = right;

            while (left <= right) {
                int mid = (left + right) / 2;

                if (canSolve(diffs, times, mid, limit)) {
                    answer = mid;
                    right = mid - 1;  // 더 낮은 숙련도에서 가능한지 확인
                } else {
                    left = mid + 1;  // 숙련도를 더 높여야 함
                }
            }

            return answer;
        }

        // 주어진 level에서 제한 시간 내 해결 가능한지 체크
        private boolean canSolve(int[] diffs, int[] times, int level, long limit) {
            long totalTime = 0;

            for (int i = 0; i < diffs.length; i++) {
                int diff = diffs[i];
                int time = times[i];

                if (diff > level) {
                    if (i > 0) {
                        totalTime += time * (diff - level + 1) + times[i - 1] * (diff - level);
                    } else {
                        totalTime += time * (diff - level + 1);
                    }
                } else {
                    totalTime += time;
                }

                if (totalTime > limit) {
                    return false;
                }
            }

            return true;
        }
    }
}
