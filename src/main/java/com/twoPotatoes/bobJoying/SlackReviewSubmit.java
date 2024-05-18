package com.twoPotatoes.bobJoying;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <슬랙에 보내는 payload 형태>
 * {
 *   "attachments":
 *   [
 *     {
 *       "fallback": "PR에 리뷰가 달렸어요! 확인해 주세요!",
 *       "mrkdwn_in": ["text"],
 *       "color": "#238636",
 *       "text": "@SLACK_ID 님의 PR에 {리뷰단사람} 님이 리뷰를 달아주셨어요!\n
 *                리뷰 확인하기 :arrow_right: : <PR_URL|PR 링크>"
 *     }
 *   ]
 * }
 */

public class SlackReviewSubmit {
    // 깃허브 ID와 슬랙 ID를 맵핑합니다.
    private Map<String, String> githubIdSlackNick =
        Map.of(
            "JisooPyo", "U06HS6A4HSL",
            "doheuncho", "U06H39U8L2F"
        );

    public static void main(String[] args) {
        SlackReviewSubmit slackReviewSubmit = new SlackReviewSubmit();
        String text = slackReviewSubmit.generateText();
        String message = slackReviewSubmit.generateJsonPayload(text);
        slackReviewSubmit.sendSlackNotification(message);
    }

    // text 부분
    private String generateText() {
        StringBuilder sb = new StringBuilder();
        String prWriter = githubIdSlackNick.get(System.getenv("PR_WRITER"));
        String reviewer = System.getenv("PR_REVIEWER");
        String prUrl = System.getenv("PR_URL");

        sb.append("\"text\": \"<@");
        sb.append(prWriter);
        sb.append("> 님의 PR에 ");
        sb.append(reviewer);
        sb.append(" 님이 리뷰를 달아주셨어요!\n리뷰 확인하기 :arrow_right: : <");
        sb.append(prUrl);
        sb.append("|PR 링크>\"");

        return sb.toString();
    }

    // 보내는 전체 json
    private String generateJsonPayload(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"attachments\":[{");
        sb.append("\"fallback\": \"PR에 리뷰가 달렸어요! 확인해 주세요!\",");
        sb.append("\"mrkdwn_in\": [\"text\"],");
        sb.append("\"color\": \"#238636\",");
        sb.append(text);
        sb.append("}]}");

        return sb.toString();
    }

    // 슬랙에 메시지를 보냅니다.
    private void sendSlackNotification(String message) {
        // yml의 환경변수를 가져옵니다.
        String webhookUrl = System.getenv("SLACK_REVIEW_WEBHOOK_URL");

        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = message;

            // 출력 스트림을 가져와 http 요청 본문에 데이터를 씁니다.
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 응답 코드에 따라 터미널에 메시지 보내기
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Message sent successfully to Slack.");
            } else {
                System.err.println("Failed to send message to Slack. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Exception occurred: " + e.getMessage());
        }
    }
}
